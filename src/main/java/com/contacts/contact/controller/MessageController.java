package com.contacts.contact.controller;

import com.contacts.contact.model.AppResponse;
import com.contacts.contact.model.UserAccount;
import com.contacts.contact.model.UserMessage;
import com.contacts.contact.repository.UserAccountRepository;
import com.contacts.contact.service.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author mdabra
 */

@RestController
@ControllerAdvice
@RequestMapping("/api")
public class MessageController {

    @Autowired
    private UserMessageService userMessageService;
    
    @Autowired
    private UserAccountRepository userAccountRepository;

    @PostMapping("/inbound/sms")
    public ResponseEntity<AppResponse> inbound(@Validated @RequestBody UserMessage message, Authentication authenticatedUser){
        // PICK LOGGED IN USER
        long userId = userAccountRepository.findByUsername(authenticatedUser.getName()).getId();
        // PERFORM BUSINESS LOGIN IN THE SERVICE
        AppResponse response = userMessageService.sendInbound(message, userId);
        // CHECK IF VALIDATION ERROR EXIST
        if(response.getError() != "")
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        // RETURN JSON RESPONSE AND RESPONSE CODE; 200
        return ResponseEntity.ok(response);
    }

    @PostMapping("/outbound/sms")
    public ResponseEntity<AppResponse> outbound(@Validated @RequestBody UserMessage message) {
        // PICK LOGGED IN USER ID
        long userId = 1;
        // PERFORM BUSINESS LOGIN IN THE SERVICE
        AppResponse response = userMessageService.sendOutBound(message, userId);
        // CHECK IF VALIDATION ERROR EXIST
        if(response.getError() != "")
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); 
        // RETURN JSON RESPONSE AND RESPONSE CODE; 200
        return ResponseEntity.ok(response);
    }
}
