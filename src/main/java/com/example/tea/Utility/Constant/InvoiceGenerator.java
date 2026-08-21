package com.example.tea.Utility.Constant;
import com.example.tea.DTO.InvoiceData;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class InvoiceGenerator {

    public byte[] createInvoice(InvoiceData invoice) throws IOException {
        ClassLoaderTemplateResolver resolver =
                new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");
        resolver.setCharacterEncoding("UTF-8");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);

        Context context = new Context();
        context.setVariable("invoiceNumber", invoice.getInvoiceNumber());
        context.setVariable("invoiceDate", invoice.getInvoiceDate());
        context.setVariable("dueDate", invoice.getDueDate());

        context.setVariable("yourCompanyName", invoice.getYourCompanyName());
        context.setVariable("yourCompanyAddress", invoice.getYourCompanyAddress());
        context.setVariable("yourCompanyContact", invoice.getYourCompanyContact());
        context.setVariable("yourCompanyEmail", invoice.getYourCompanyEmail());

        context.setVariable("customerName", invoice.getCustomerName());
        context.setVariable("customerAddress", invoice.getCustomerAddress());
        context.setVariable("customerEmail", invoice.getCustomerEmail());

        context.setVariable("items", invoice.getItems());

        context.setVariable("subtotal", invoice.getSubtotal());
        context.setVariable("taxRate", invoice.getTaxRate());
        context.setVariable("taxAmount", invoice.getTaxAmount());
        context.setVariable("grandTotal", invoice.getGrandTotal());
        context.setVariable("paymentTerms", invoice.getPaymentTerms());

        context.setVariable("gst", invoice.getGst());
        context.setVariable("hsn", invoice.getHsn());
        context.setVariable("billFromCompany", invoice.getBillFromCompany());
        context.setVariable("customerGSTNumber", invoice.getCustomerGSTNumber());

        context.setVariable("transport", invoice.getTransport());
        context.setVariable("lrNumber", invoice.getLrNumber());
        context.setVariable("bankName", invoice.getBankName());
        context.setVariable("accountNumber", invoice.getAccountNumber());
        context.setVariable("ifscCode", invoice.getIfscCode());
        context.setVariable("amountInWords", invoice.getAmountInWords());
        context.setVariable("discount", invoice.getDiscount());
        context.setVariable("labour", invoice.getLabour());
        context.setVariable("sgstAmount", invoice.getSgstAmount());
        context.setVariable("cgstAmount", invoice.getCgstAmount());
        context.setVariable("sgstRate", invoice.getSgstRate());
        context.setVariable("cgstRate", invoice.getCgstRate());

        String html =
                templateEngine.process("invoice", context);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            PdfRendererBuilder builder =
                    new PdfRendererBuilder();

            builder.withHtmlContent(html, null);
            builder.toStream(byteArrayOutputStream);
            builder.run();
            return byteArrayOutputStream.toByteArray();
    }
}
