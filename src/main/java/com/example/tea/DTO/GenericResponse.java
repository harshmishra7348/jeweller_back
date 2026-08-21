package com.example.tea.DTO;

public class GenericResponse {
    private Object data;
    private String message;
    private Boolean status;

    public GenericResponse(Object data, String message, Boolean status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }
    public GenericResponse(){}

    /** Successful response carrying data. */
    public static GenericResponse success(Object data) {
        return new GenericResponse(data, "Successful", true);
    }

    /** Successful response with a custom message. */
    public static GenericResponse success(Object data, String message) {
        return new GenericResponse(data, message, true);
    }

    /** Failed response carrying an error message. */
    public static GenericResponse error(String message) {
        return new GenericResponse(null, message, false);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
