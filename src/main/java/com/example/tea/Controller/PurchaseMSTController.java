package com.example.tea.Controller;

import com.example.tea.DTO.GenericResponse;
import com.example.tea.DTO.PurchaseRequest;
import com.example.tea.Services.ExcelExportUtil;
import com.example.tea.Services.PurchaseMSTService;
import com.example.tea.Utility.Constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Admin-only purchase (stock-in) management. Secured by JWT. */
@RestController
@RequestMapping(value = Constant.PURCHASEMST)
public class PurchaseMSTController {

    @Autowired
    private PurchaseMSTService purchaseMSTService;
    @Autowired
    private ExcelExportUtil excelExportUtil;

    @PostMapping(value = Constant.CREATE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public GenericResponse create(@RequestBody PurchaseRequest request) {
        try {
            return GenericResponse.success(purchaseMSTService.create(request));
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    @GetMapping(value = Constant.GET_ALL)
    public GenericResponse getAll() {
        try {
            return GenericResponse.success(purchaseMSTService.getAll());
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    @GetMapping(value = Constant.BY_ID + "/{purchaseMSTId}")
    public GenericResponse getById(@PathVariable("purchaseMSTId") Long purchaseMSTId) {
        try {
            return GenericResponse.success(purchaseMSTService.getById(purchaseMSTId));
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    @DeleteMapping(value = Constant.DELETE + "/{purchaseMSTId}")
    public GenericResponse delete(@PathVariable("purchaseMSTId") Long purchaseMSTId) {
        try {
            return GenericResponse.success(purchaseMSTService.delete(purchaseMSTId));
        } catch (Exception e) {
            e.printStackTrace();
            return GenericResponse.error(e.getMessage());
        }
    }

    /** Download all purchase records as an Excel (.xlsx) file. */
    @GetMapping(value = Constant.EXPORT_EXCEL)
    public ResponseEntity<byte[]> exportExcel() {
        try {
            byte[] excel = excelExportUtil.purchasesToExcel(purchaseMSTService.getAll());
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=purchase-records.xlsx")
                    .body(excel);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
