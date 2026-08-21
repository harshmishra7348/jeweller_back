package com.example.tea.Controller;

import com.example.tea.Model.TransportMST;
import com.example.tea.Services.TransportMSTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transport")
public class TransportMSTController {

    @Autowired
    private TransportMSTService transportMSTService;

    @PostMapping("/create")
    public ResponseEntity<TransportMST> create(@RequestBody TransportMST transportMST) {
        try {
            TransportMST created = transportMSTService.create(transportMST);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransportMST> getById(@PathVariable Long id) {
        try {
            TransportMST transport = transportMSTService.getById(id);
            return new ResponseEntity<>(transport, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<TransportMST>> getAll() {
        try {
            List<TransportMST> transports = transportMSTService.getAll();
            return new ResponseEntity<>(transports, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TransportMST> update(@PathVariable Long id, @RequestBody TransportMST transportMST) {
        try {
            TransportMST updated = transportMSTService.update(id, transportMST);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        try {
            Boolean deleted = transportMSTService.delete(id);
            return new ResponseEntity<>(deleted, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }
}
