package com.example.tea.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "transport_mst")
public class TransportMST extends BaseModel {

    @Column(name = "transport_name", nullable = false)
    private String transportName;

    @Column(name = "transport_gst")
    private String transportGst;

    @Column(name = "transport_address")
    private String transportAddress;

    @Column(name = "transport_contact")
    private String transportContact;

    @Email(message = "Please enter valid Mail.")
    @Column(name = "transport_email")
    private String email;

    public TransportMST() {}

    public TransportMST(String transportName, String transportGst, String transportAddress, String transportContact) {
        this.transportName = transportName;
        this.transportGst = transportGst;
        this.transportAddress = transportAddress;
        this.transportContact = transportContact;
    }

    public String getTransportName() {
        return transportName;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName;
    }

    public String getTransportGst() {
        return transportGst;
    }

    public void setTransportGst(String transportGst) {
        this.transportGst = transportGst;
    }

    public String getTransportAddress() {
        return transportAddress;
    }

    public void setTransportAddress(String transportAddress) {
        this.transportAddress = transportAddress;
    }

    public String getTransportContact() {
        return transportContact;
    }

    public void setTransportContact(String transportContact) {
        this.transportContact = transportContact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
