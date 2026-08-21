package com.example.tea.Services;

import com.example.tea.Model.InvoiceMST;
import com.example.tea.Model.PurchaseMST;
import com.example.tea.Model.UserMST;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

/** Builds .xlsx workbooks for the admin's sales and purchase record downloads. */
@Component
public class ExcelExportUtil {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public byte[] salesToExcel(List<InvoiceMST> invoices) throws IOException {
        String[] headers = {"Invoice Number", "Date", "Customer", "Customer Email", "Address",
                "GST Number", "Status", "Subtotal", "Tax", "Total"};
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Sales");
            writeHeader(workbook, sheet, headers);

            int rowIdx = 1;
            for (InvoiceMST inv : invoices) {
                Row row = sheet.createRow(rowIdx++);
                UserMST customer = inv.getUserMST();
                double total = inv.getAmount() != null ? inv.getAmount() : 0d;
                double tax = inv.getTax() != null ? inv.getTax() : 0d;
                row.createCell(0).setCellValue(safe(inv.getInvoiceNumber()));
                row.createCell(1).setCellValue(inv.getInvoiceDate() != null ? inv.getInvoiceDate().format(DATE_FMT) : "");
                row.createCell(2).setCellValue(customer != null ? safe(customer.getName()) : "");
                row.createCell(3).setCellValue(customer != null ? safe(customer.getEmail()) : "");
                row.createCell(4).setCellValue(safe(inv.getAddress()));
                row.createCell(5).setCellValue(safe(inv.getGSTNumber()));
                row.createCell(6).setCellValue(inv.getInvoiceStatus() != null ? inv.getInvoiceStatus().name() : "");
                row.createCell(7).setCellValue(total - tax);
                row.createCell(8).setCellValue(tax);
                row.createCell(9).setCellValue(total);
            }
            autoSize(sheet, headers.length);
            workbook.write(out);
            return out.toByteArray();
        }
    }

    public byte[] purchasesToExcel(List<PurchaseMST> purchases) throws IOException {
        String[] headers = {"Purchase Number", "Date", "Supplier", "Supplier GST", "Address",
                "Status", "Subtotal", "Tax", "Total"};
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Purchases");
            writeHeader(workbook, sheet, headers);

            int rowIdx = 1;
            for (PurchaseMST pur : purchases) {
                Row row = sheet.createRow(rowIdx++);
                double total = pur.getAmount() != null ? pur.getAmount() : 0d;
                double tax = pur.getTax() != null ? pur.getTax() : 0d;
                row.createCell(0).setCellValue(safe(pur.getPurchaseNumber()));
                row.createCell(1).setCellValue(pur.getPurchaseDate() != null ? pur.getPurchaseDate().format(DATE_FMT) : "");
                row.createCell(2).setCellValue(safe(pur.getSupplierName()));
                row.createCell(3).setCellValue(safe(pur.getSupplierGst()));
                row.createCell(4).setCellValue(safe(pur.getAddress()));
                row.createCell(5).setCellValue(pur.getPurchaseStatus() != null ? pur.getPurchaseStatus().name() : "");
                row.createCell(6).setCellValue(total - tax);
                row.createCell(7).setCellValue(tax);
                row.createCell(8).setCellValue(total);
            }
            autoSize(sheet, headers.length);
            workbook.write(out);
            return out.toByteArray();
        }
    }

    private void writeHeader(Workbook workbook, Sheet sheet, String[] headers) {
        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);
        Row header = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    private void autoSize(Sheet sheet, int columns) {
        for (int i = 0; i < columns; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private String safe(String value) {
        return value != null ? value : "";
    }
}
