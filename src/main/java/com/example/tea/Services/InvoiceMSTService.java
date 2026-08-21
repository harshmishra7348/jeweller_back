package com.example.tea.Services;

import com.example.tea.DTO.InvoiceData;
import com.example.tea.DTO.InvoiceLineItem;
import com.example.tea.DTO.InvoiceMSTRequest;
import com.example.tea.DTO.InvoiceResponse;
import com.example.tea.Model.InvoiceMST;
import com.example.tea.Model.ItemInvoiceMapping;
import com.example.tea.Model.UserMST;
import com.example.tea.Model.TransportMST;
import com.example.tea.Repository.InvoiceMSTRepository;
import com.example.tea.Repository.ItemInvoiceMappingRepository;
import com.example.tea.Utility.Constant.InvoiceEnum;
import com.example.tea.Utility.Constant.InvoiceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceMSTService {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private InvoiceMSTRepository invoiceMSTRepository;
    @Autowired
    private ItemInvoiceMappingRepository itemInvoiceMappingRepository;
    @Autowired
    private UserMSTService userMSTService;
    @Autowired
    private TransportMSTService transportMSTService;
    @Autowired
    private ItemInvoiceMappingService itemInvoiceMappingService;
    @Autowired
    private InvoiceGenerator invoiceGenerator;
    @Autowired
    private MailUtil mailUtil;

    @Value("${app.invoice.payment-terms-days:7}")
    private Integer paymentTermsDays;

    @Value("${bill.from:Jaydeep Traders}")
    private String billFromCompany;

    @Value("${gst:}")
    private String gst;

    @Value("${hsn:}")
    private String hsn;

    @Value("${invoice.bank.name:}")
    private String bankName;

    @Value("${invoice.bank.account:}")
    private String bankAccount;

    @Value("${invoice.bank.ifsc:}")
    private String ifscCode;

    @Value("${invoice.sgst.rate:2.5}")
    private String sgstRate;

    @Value("${invoice.cgst.rate:2.5}")
    private String cgstRate;

    public InvoiceMST create(InvoiceMSTRequest invoiceMSTRequest) {
        if (invoiceMSTRequest.getUserMSTId() == null) {
            throw new RuntimeException("User not found.");
        }
        if (invoiceMSTRequest.getTransportMSTId() == null) {
            throw new RuntimeException("Transport is required.");
        }
        if (invoiceMSTRequest.getItemMSTS() == null || invoiceMSTRequest.getItemMSTS().isEmpty()) {
            throw new RuntimeException("Please Add Items.");
        }
        UserMST userMST = userMSTService.getById(invoiceMSTRequest.getUserMSTId());
        TransportMST transportMST = transportMSTService.getById(invoiceMSTRequest.getTransportMSTId());
        
        InvoiceMST invoiceMST = new InvoiceMST(userMST.getId(), userMST, LocalDateTime.now(),
                InvoiceEnum.PENDING, invoiceMSTRequest.getAddress(), invoiceMSTRequest.getGSTNumber(),
                null, invoiceMSTRequest.getTax(), transportMST.getId(),invoiceMSTRequest.getLabour(),invoiceMSTRequest.getDiscount());
        invoiceMST.setTransportMST(transportMST);
        // invoice_number is NOT NULL - assign it before the first insert.
        invoiceMST.setInvoiceNumber("INV-" + System.currentTimeMillis());
        invoiceMST = invoiceMSTRepository.save(invoiceMST);

        List<ItemInvoiceMapping> all = itemInvoiceMappingService.createAll(invoiceMSTRequest.getItemMSTS(), invoiceMST);
        Double subtotal = all.stream().mapToDouble(ItemInvoiceMapping::getTotalAmount).sum();
        
        // Apply discount and labour to calculate taxable amount
        Double discount = invoiceMSTRequest.getDiscount() != null ? invoiceMSTRequest.getDiscount() : 0.0;
        Double labour = invoiceMSTRequest.getLabour() != null ? invoiceMSTRequest.getLabour() : 0.0;
        Double taxableAmount = subtotal - discount + labour;
        
        // Calculate SGST and CGST separately
        double sgstPercent = Double.parseDouble(sgstRate);
        double cgstPercent = Double.parseDouble(cgstRate);
        double sgstAmount = (taxableAmount * sgstPercent) / 100;
        double cgstAmount = (taxableAmount * cgstPercent) / 100;
        double totalTaxAmount = sgstAmount + cgstAmount;
        double grandTotal = taxableAmount + totalTaxAmount;
        
        invoiceMST.setTax(totalTaxAmount);
        invoiceMST.setAmount(grandTotal);
        invoiceMST.setInvoiceStatus(InvoiceEnum.PENDING);
        invoiceMST = invoiceMSTRepository.save(invoiceMST);

        // Best-effort: email the PDF invoice to the customer. A mail failure must not
        // roll back the created invoice - the admin can resend later via /sendMail.
        try {
            mailUtil.sendInvoicePdf(invoiceMST, buildPdf(invoiceMST), userMST.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invoiceMST;
    }

    public List<ItemInvoiceMapping> update(List<ItemInvoiceMapping> itemInvoiceMappings) {
        if (itemInvoiceMappings.isEmpty()) {
            throw new RuntimeException("Item Invoice Mapping list is Empty.");
        }
        InvoiceMST invoiceMST = getById(itemInvoiceMappings.get(0).getInvoiceMSTId());
        return itemInvoiceMappingService.updateAll(itemInvoiceMappings, invoiceMST);
    }

    public InvoiceMST getById(Long invoiceMSTId) {
        return invoiceMSTRepository.findById(invoiceMSTId)
                .orElseThrow(() -> new RuntimeException("InvoiceMST Id not found."));
    }

    public List<InvoiceMST> getAll() {
        return invoiceMSTRepository.findAll();
    }

    public Boolean delete(Long invoiceMSTId) {
        InvoiceMST invoiceMST = getById(invoiceMSTId);
        invoiceMST.setActive(false);
        invoiceMSTRepository.save(invoiceMST);
        return true;
    }

    public InvoiceMST updateStatus(Long invoiceMSTId, InvoiceEnum invoiceEnum) {
        InvoiceMST invoiceMST = getById(invoiceMSTId);
        invoiceMST.setInvoiceStatus(invoiceEnum);
        return invoiceMSTRepository.save(invoiceMST);
    }

    /** Generate the invoice PDF bytes (used for download). */
    public byte[] generatePdf(Long invoiceMSTId) throws Exception {
        return buildPdf(getById(invoiceMSTId));
    }

    /** (Re)send the invoice PDF to the customer's email. */
    public Boolean sendInvoiceMail(Long invoiceMSTId, String toTransport) throws Exception {
        InvoiceMST invoiceMST = getById(invoiceMSTId);
        if(toTransport!=null && !toTransport.isBlank()){
            mailUtil.sendInvoicePdf(invoiceMST, buildPdf(invoiceMST),toTransport);
        }else{
        mailUtil.sendInvoicePdf(invoiceMST, buildPdf(invoiceMST), invoiceMST.getUserMST().getEmail());}

        return true;
    }


    private byte[] buildPdf(InvoiceMST invoice) throws Exception {
        List<ItemInvoiceMapping> mappings = itemInvoiceMappingService.getByInvoiceMSTId(invoice.getId());
        List<InvoiceLineItem> lines = new ArrayList<>();
        for (ItemInvoiceMapping m : mappings) {
            String description = m.getItemMST() != null ? m.getItemMST().getItemName() : "Item";
            lines.add(new InvoiceLineItem(description, m.getQuantity(), m.getAmount(), m.getTotalAmount()));
        }

        // Calculate correct subtotal, discount, labour, tax, and grand total
        double itemSubtotal = lines.stream().mapToDouble(InvoiceLineItem::getTotal).sum();
        double discount = invoice.getDiscount() != null ? invoice.getDiscount() : 0.0;
        double labour = invoice.getLabour() != null ? invoice.getLabour() : 0.0;
        double taxableAmount = itemSubtotal - discount + labour;
        
        // Calculate SGST and CGST separately
        double sgstPercent = Double.parseDouble(sgstRate);
        double cgstPercent = Double.parseDouble(cgstRate);
        double sgstAmount = round((taxableAmount * sgstPercent) / 100);
        double cgstAmount = round((taxableAmount * cgstPercent) / 100);
        double totalTaxAmount = sgstAmount + cgstAmount;
        double grandTotal = taxableAmount + totalTaxAmount;

        UserMST customer = invoice.getUserMST();
        InvoiceData data = new InvoiceData();
        data.setInvoiceNumber(invoice.getInvoiceNumber());
        data.setInvoiceDate(invoice.getInvoiceDate() != null ? invoice.getInvoiceDate().format(DATE_FMT) : "");
        data.setDueDate(invoice.getInvoiceDate() != null
                ? invoice.getInvoiceDate().plusDays(paymentTermsDays).format(DATE_FMT) : "");

        data.setYourCompanyName(invoice.getUserMST().getCompanyName() != null ? invoice.getUserMST().getCompanyName() : invoice.getUserMST().getName());
        data.setYourCompanyAddress(invoice.getAddress());
        data.setYourCompanyContact(invoice.getUserMST().getPhoneNumber());
        data.setYourCompanyEmail(invoice.getUserMST().getEmail());

        data.setCustomerName(customer != null ? customer.getName() : "");
        data.setCustomerAddress(invoice.getAddress() != null ? invoice.getAddress()
                : (customer != null ? customer.getAddress() : ""));
        data.setCustomerEmail(customer != null ? customer.getEmail() : "");

        data.setItems(lines);
        data.setSubtotal(round(taxableAmount));
        data.setTaxRate((sgstPercent + cgstPercent));
        data.setTaxAmount(round(totalTaxAmount));
        data.setGrandTotal(round(grandTotal));
        data.setPaymentTerms(paymentTermsDays);
        data.setGst(gst);
        data.setHsn(hsn);
        data.setBillFromCompany(billFromCompany);
        data.setCustomerGSTNumber(invoice.getGSTNumber());
        
        data.setBankName(bankName);
        data.setAccountNumber(bankAccount);
        data.setIfscCode(ifscCode);
        data.setSgstRate("@" + sgstRate + "%");
        data.setCgstRate("@" + cgstRate + "%");
        data.setDiscount(discount);
        data.setLabour(labour);
        data.setSgstAmount(sgstAmount);
        data.setCgstAmount(cgstAmount);
        data.setAmountInWords(convertNumberToWords(round(grandTotal)));
        
        // Set transport details
        if (invoice.getTransportMST() != null) {
            data.setTransport(invoice.getTransportMST().getTransportName());
        } else {
            data.setTransport("");
        }
        data.setLrNumber("");

        return invoiceGenerator.createInvoice(data);
    }

    private double round(double value) {
        return Math.round(value * 100d) / 100d;
    }

    private String convertNumberToWords(double amount) {
        long num = (long) amount;
        if (num == 0) return "Zero";
        
        String[] ones = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
        String[] teens = {"Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", 
                         "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
        String[] tens = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
        String[] scales = {"", "Thousand", "Lakh", "Crore"};
        
        if (num < 0) return "Minus " + convertNumberToWords(-num);
        
        String result = "";
        int scaleIndex = 0;
        
        while (num > 0) {
            int group = (int) (num % 1000);
            if (group != 0) {
                result = convertGroup(group, ones, teens, tens) + 
                        (scales[scaleIndex].isEmpty() ? "" : scales[scaleIndex] + " ") + result;
            }
            num /= 1000;
            scaleIndex++;
            if (scaleIndex >= scales.length) break;
        }
        
        return result.trim();
    }

    private String convertGroup(int num, String[] ones, String[] teens, String[] tens) {
        String result = "";
        
        // Handle hundreds
        int hundreds = num / 100;
        if (hundreds > 0) {
            result += ones[hundreds] + " Hundred ";
        }
        
        // Handle tens and ones
        int remainder = num % 100;
        if (remainder >= 20) {
            result += tens[remainder / 10];
            if (remainder % 10 > 0) {
                result += " " + ones[remainder % 10];
            }
        } else if (remainder >= 10) {
            result += teens[remainder - 10];
        } else if (remainder > 0) {
            result += ones[remainder];
        }
        
        return result.trim() + " ";
    }

    public InvoiceResponse convertToInvoiceResponse(InvoiceMST invoice) {
        InvoiceResponse response = new InvoiceResponse();
        response.setId(invoice.getId());
        response.setInvoiceNumber(invoice.getInvoiceNumber());
        response.setInvoiceDate(invoice.getInvoiceDate());
        response.setInvoiceStatus(invoice.getInvoiceStatus());
        response.setAddress(invoice.getAddress());
        response.setGSTNumber(invoice.getGSTNumber());
        response.setAmount(calculateInvoiceAmount(invoice));
        response.setTax(invoice.getTax());
        response.setUserMSTId(invoice.getUserMSTId());
        response.setLabour(invoice.getLabour());
        response.setDiscount(invoice.getDiscount());
        response.setCustomerName(invoice.getUserMST()!=null?invoice.getUserMST().getName():userMSTService.getById(invoice.getUserMSTId()).getName());
        
        response.setTransportMSTId(invoice.getTransportMSTId());
        if (invoice.getTransportMST() != null) {
            response.setTransportName(invoice.getTransportMST().getTransportName());
        }
        
        response.setCreateAt(invoice.getCreateAt());
        response.setModifyAt(invoice.getModifyAt());
        
        return response;
    }

    public List<InvoiceResponse> convertToInvoiceResponseList(List<InvoiceMST> invoices) {
        List<InvoiceResponse> responses = new ArrayList<>();
        for (InvoiceMST invoice : invoices) {
            responses.add(convertToInvoiceResponse(invoice));
        }
        return responses;
    }

    private Double calculateInvoiceAmount(InvoiceMST invoice) {
        List<ItemInvoiceMapping> items = itemInvoiceMappingRepository.findByInvoiceMSTId(invoice.getId());
        Double total = 0.0;
        for (ItemInvoiceMapping item : items) {
            total += item.getTotalAmount();
        }
        return total;
    }
    public List<InvoiceResponse> searchInvoice(String key) {
        List<InvoiceMST> invoices = invoiceMSTRepository.searchInvoices(key);
        return convertToInvoiceResponseList(invoices);
    }
}

