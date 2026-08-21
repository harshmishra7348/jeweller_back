package com.example.tea.Utility.Constant;

public enum InvoiceEnum {
    PENDING("pending"),
    REMAINING("remaining"),
    CANCELLED("cancelled"),
    COMPLETED("completed");
    private String name;

    public String getName(){
        return name;
    }
     InvoiceEnum(String name){
        this.name=name;
    }
}
