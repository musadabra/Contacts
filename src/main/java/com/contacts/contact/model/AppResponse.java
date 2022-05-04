package com.contacts.contact.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 *
 * @author Musa Dabra
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppResponse {

    private Object data;
    private String message;
    private String error;

    public AppResponse data(Object data) {
        this.data = data;
        return this;
    }

    public AppResponse(String message, String error) {
        this.message = message;
        this.error = error;
    }
    
    public void error(){
        this.message = "";
        this.error = "Unknown Failure.s";
    }

}
