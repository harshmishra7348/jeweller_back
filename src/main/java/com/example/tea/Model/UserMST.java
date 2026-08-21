package com.example.tea.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity(name = "user_mst")
public class UserMST extends BaseModel {

    @NotBlank(message = "Name is mandatory")
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name="company_name")
    private String companyName;
    @NotBlank(message = "Address is mandatory")
    @Column(name = "address",nullable = false)
    private String address;
    @NotBlank(message = "Phone number is mandatory")
    @Column(name = "phone_number",nullable = false)
    private String phoneNumber;
    @NotBlank(message = "Email is mandatory")
    @Column(name = "email",nullable = false)
    private String email;

    // Admin flag. Defaults to false; only an admin may create another admin user.
    @Column(name = "admin",columnDefinition = "bit(1) default 0")
    private Boolean admin = false;

    // Accepted from request bodies but never serialized back in responses (no leaking the hash).
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password",nullable = false)
    private String password;

    public UserMST(){}

    public UserMST(Long id, LocalDateTime createAt, LocalDateTime modifyAt, boolean isActive, String name, String address, String phoneNumber, String email, String password) {
        super(id, createAt, modifyAt, isActive);
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
