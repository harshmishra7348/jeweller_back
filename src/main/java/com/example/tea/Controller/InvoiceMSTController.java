package com.example.tea.Controller;

import com.example.tea.DTO.GenericResponse;
import com.example.tea.DTO.InvoiceMSTRequest;
import com.example.tea.Model.InvoiceMST;
import com.example.tea.Model.ItemInvoiceMapping;
import com.example.tea.Services.ExcelExportUtil;
import com.example.tea.Services.InvoiceMSTService;
import com.example.tea.Utility.Constant.Constant;
import com.example.tea.Utility.Constant.InvoiceEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = Constant.INVOICEMST)
public class InvoiceMSTController {
    @Autowired
    private InvoiceMSTService invoiceMSTService;
    @Autowired
    private ExcelExportUtil excelExportUtil;

    @PostMapping(value = Constant.CREATE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse create(@RequestBody InvoiceMSTRequest invoiceMSTRequest){
        GenericResponse genericResponse = new GenericResponse();
        try{
            genericResponse.setStatus(true);
            genericResponse.setMessage("Successful");
            InvoiceMST invoice = invoiceMSTService.create(invoiceMSTRequest);
            genericResponse.setData(invoiceMSTService.convertToInvoiceResponse(invoice));
        }catch (Exception e){
            e.printStackTrace();
            genericResponse.setData(null);
            genericResponse.setMessage(e.getMessage());
            genericResponse.setStatus(false);
        }
        return genericResponse;
    }
    @PutMapping(value = Constant.UPDATE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse update(@RequestBody List<ItemInvoiceMapping> itemInvoiceMappingList){
        GenericResponse genericResponse = new GenericResponse();
        try{
            genericResponse.setStatus(true);
            genericResponse.setMessage("Successful");
            genericResponse.setData(invoiceMSTService.update(itemInvoiceMappingList));
        }catch (Exception e){
            e.printStackTrace();
            genericResponse.setData(null);
            genericResponse.setMessage(e.getMessage());
            genericResponse.setStatus(false);
        }
        return genericResponse;
    }
    @GetMapping(value = Constant.BY_ID+"{invoiceMSTId}")
    public GenericResponse getById(@PathVariable(name = "invoiceMSTId") Long invoiceMSTId){
        GenericResponse genericResponse = new GenericResponse();
        try{
            genericResponse.setStatus(true);
            genericResponse.setMessage("Successful");
            InvoiceMST invoice = invoiceMSTService.getById(invoiceMSTId);
            genericResponse.setData(invoiceMSTService.convertToInvoiceResponse(invoice));
        }catch (Exception e){
            e.printStackTrace();
            genericResponse.setData(null);
            genericResponse.setMessage(e.getMessage());
            genericResponse.setStatus(false);
        }
        return genericResponse;
    }
    @GetMapping(value = Constant.GET_ALL)
    public GenericResponse getAll(){
        GenericResponse genericResponse = new GenericResponse();
        try{
            genericResponse.setStatus(true);
            genericResponse.setMessage("Successful");
            List<InvoiceMST> invoices = invoiceMSTService.getAll();
            genericResponse.setData(invoiceMSTService.convertToInvoiceResponseList(invoices));
        }catch (Exception e){
            e.printStackTrace();
            genericResponse.setData(null);
            genericResponse.setMessage(e.getMessage());
            genericResponse.setStatus(false);
        }
        return genericResponse;
    }
    @DeleteMapping(value = Constant.DELETE+"{invoiceMSTId}")
    public GenericResponse delete(@PathVariable(name = "invoiceMSTId")Long invoiceMSTId){
        GenericResponse genericResponse = new GenericResponse();
        try{
            genericResponse.setStatus(true);
            genericResponse.setMessage("Successful");
            genericResponse.setData(invoiceMSTService.delete(invoiceMSTId));
        }catch (Exception e){
            e.printStackTrace();
            genericResponse.setData(null);
            genericResponse.setMessage(e.getMessage());
            genericResponse.setStatus(false);
        }
        return genericResponse;
    }

    @PutMapping(value = Constant.UPDATE_STATUS)
    public GenericResponse updateStatus(@RequestParam("invoiceMSTId")Long invoiceMSTId, @RequestParam("invoiceStatus")InvoiceEnum invoiceEnum){
        GenericResponse genericResponse = new GenericResponse();
        try{
            genericResponse.setStatus(true);
            genericResponse.setMessage("Successful");
            InvoiceMST invoice = invoiceMSTService.updateStatus(invoiceMSTId,invoiceEnum);
            genericResponse.setData(invoiceMSTService.convertToInvoiceResponse(invoice));
        }catch (Exception e){
            e.printStackTrace();
            genericResponse.setData(null);
            genericResponse.setMessage(e.getMessage());
            genericResponse.setStatus(false);
        }
        return genericResponse;
    }

    /** (Re)send the invoice PDF to the customer's email. */
    @PostMapping(value = Constant.SEND_MAIL + "/{invoiceMSTId}")
    public GenericResponse sendMail(@PathVariable("invoiceMSTId") Long invoiceMSTId) {
        try {
            return GenericResponse.success(invoiceMSTService.sendInvoiceMail(invoiceMSTId, null));
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }
    @GetMapping(value = Constant.SEND_MAILTO_CUSTOM)
    public GenericResponse sendMailToTransport(@RequestParam("invoiceMSTId") Long invoiceMSTId,@RequestParam("email") String email) {
        try {
            return GenericResponse.success(invoiceMSTService.sendInvoiceMail(invoiceMSTId, email));
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    /** Download the invoice as a PDF. */
    @GetMapping(value = Constant.DOWNLOAD + "/{invoiceMSTId}")
    public ResponseEntity<byte[]> download(@PathVariable("invoiceMSTId") Long invoiceMSTId) {
        try {
            byte[] pdf = invoiceMSTService.generatePdf(invoiceMSTId);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=invoice-" + invoiceMSTId + ".pdf")
                    .body(pdf);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    /** Download all sales (invoice) records as an Excel (.xlsx) file. */
    @GetMapping(value = Constant.EXPORT_EXCEL)
    public ResponseEntity<byte[]> exportExcel() {
        try {
            byte[] excel = excelExportUtil.salesToExcel(invoiceMSTService.getAll());
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=sales-records.xlsx")
                    .body(excel);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = Constant.SEARCH)
    public GenericResponse searchInvoice(@RequestParam("key") String key) {
        try {
            return GenericResponse.success(invoiceMSTService.searchInvoice(key));
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }
}

