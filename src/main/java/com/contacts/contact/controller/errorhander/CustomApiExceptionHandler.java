/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contacts.contact.controller.errorhander;

import com.contacts.contact.model.ErrorResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import java.util.Arrays;
import static org.springframework.data.redis.serializer.RedisSerializationContext.java;
import org.springframework.web.HttpRequestMethodNotSupportedException;
/**
 *
 * @author Musa Dabra
 */
@ControllerAdvice
public class CustomApiExceptionHandler {
	
    // Validation Error handling
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        
        List<String> errorList = new ArrayList<>();
        result.getFieldErrors().forEach((fieldError) -> {
        	errorList.add(fieldError.getDefaultMessage()+" ");
        });
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), errorList);
    }
    
    // Request Method Error handling.
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public ErrorResponse handleMethodNotValidException(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        String result = ex.getMessage();
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), Arrays.asList(result));
    }

}