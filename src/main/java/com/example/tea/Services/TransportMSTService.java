package com.example.tea.Services;

import com.example.tea.Model.TransportMST;
import com.example.tea.Repository.TransportMSTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportMSTService {

    @Autowired
    private TransportMSTRepository transportMSTRepository;

    public TransportMST create(TransportMST transportMST) {
        if (transportMST.getTransportName() == null || transportMST.getTransportName().isEmpty()) {
            throw new RuntimeException("Transport Name is required.");
        }
        return transportMSTRepository.save(transportMST);
    }

    public TransportMST getById(Long transportMSTId) {
        return transportMSTRepository.findById(transportMSTId)
                .orElseThrow(() -> new RuntimeException("TransportMST Id not found."));
    }

    public List<TransportMST> getAll() {
        return transportMSTRepository.findAll();
    }

    public TransportMST update(Long transportMSTId, TransportMST transportMST) {
        TransportMST existing = getById(transportMSTId);
        
        if (transportMST.getTransportName() != null) {
            existing.setTransportName(transportMST.getTransportName());
        }
        if (transportMST.getTransportGst() != null) {
            existing.setTransportGst(transportMST.getTransportGst());
        }
        if (transportMST.getTransportAddress() != null) {
            existing.setTransportAddress(transportMST.getTransportAddress());
        }
        if (transportMST.getTransportContact() != null) {
            existing.setTransportContact(transportMST.getTransportContact());
        }
        
        return transportMSTRepository.save(existing);
    }

    public Boolean delete(Long transportMSTId) {
        TransportMST transportMST = getById(transportMSTId);
        transportMST.setActive(false);
        transportMSTRepository.save(transportMST);
        return true;
    }
}
