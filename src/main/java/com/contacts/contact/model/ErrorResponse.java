/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contacts.contact.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;

/**
 *
 * @author dell
 */

    // Error Object for response.
public class ErrorResponse{
    private String error;
    private String Message = "";

    @JsonIgnore
    private List<String> fieldErrors = new ArrayList<>();

    public ErrorResponse(HttpStatus status, String message, List<String> fieldErrors ) {
            this.fieldErrors = fieldErrors;
            this.error = fieldErrors.stream().collect(Collectors.joining());
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

}