package com.contacts.contact.service;


import com.contacts.contact.model.AppResponse;
import com.contacts.contact.model.PhoneNumber;
import com.contacts.contact.model.Stop;
import com.contacts.contact.model.UserMessage;
import com.contacts.contact.repository.PhoneNumberRepository;
import com.contacts.contact.repository.StopRepository;
import com.contacts.contact.repository.UserAccountRepository;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.data.redis.serializer.RedisSerializationContext.java;
import org.springframework.stereotype.Service;

@Service
public class UserMessageService {
    // Autowire: enables you to inject the object dependency implicitly
    // Autowire the Accounts repository
    @Autowired
    private UserAccountRepository userAccountRepository;

    // Autowire the Phone Number
    @Autowired
    private PhoneNumberRepository phoneNumberRepository;
    
    // Autowire the Phone Number
    @Autowired
    private StopRepository stopRepository;
    
    // Logging data in console/terminal.
    private static final Logger LOG = Logger.getLogger(UserMessageService.class.getName());
    
    // Validate the number existance.
    // Make sure the authenticated user has the to number.
    // Check the TO number, return appropraite response.
    public AppResponse sendInbound(UserMessage message, long userId){
        // Generate unique Hkey.
        String from = message.getFrom();
        String to = message.getTo();
        String stopId = from+"_"+to;
        
        // Find the phone number if it exist
        PhoneNumber phoneNumber = phoneNumberRepository.findByNumber(message.getTo());
        if(phoneNumber == null)
            return new AppResponse("", "To parameter not found");

        // Return error if To number not for logged in user.
        if(phoneNumber.getAccount().getId() != userId)
            return new AppResponse("", "To parameter not found");
        
        // Check if From, To pair exist
        boolean existToFrom = stopRepository.findStopByToFrom(stopId);
        if(existToFrom)
            return new AppResponse("", "Alread Saved STOP");
        
        // Check message content for STOP OR stop or STOP\r\n
        String stopRegex = "(STOP|stop)\\s*\\n*";
        boolean messageContainStop = message.getText().matches(stopRegex);
        if(!messageContainStop)
            return new AppResponse("\"STOP\" MATCH found", "");
       
        // Save in Redis
        Stop stopObj = new Stop();
        stopObj.setId(stopId);
        stopObj.setFrom(from);
        stopObj.setTo(to);
        
        // Try saving and catch errors if any and send response.
        try {
            // Save Stop with expiration after 4 hours.
            stopRepository.saveWithExpiration(stopObj);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), "Failed to save");
            return new AppResponse("", "Unknown Failure.");
        }
 
        return new AppResponse("inbound sms ok", "");
    }
    
    public AppResponse sendOutBound(UserMessage message, long userId){
        // Generate unique Hkey.
        String from = message.getFrom();
        String to = message.getTo();
        String stopId = to+"_"+from;
        
        // Check if FROM in phone_number if it exist.
        PhoneNumber phoneNumber = phoneNumberRepository.findByNumber(message.getFrom());
        if(phoneNumber == null)
            return new AppResponse("", "From parameter not found");
        
        // Return error if To number not for logged in user.
        if(phoneNumber.getAccount().getId() != userId)
            return new AppResponse("", "From parameter not found.");
        
        
        // Check if To, From pair exist, return error
        boolean existToFrom = stopRepository.findStopByToFrom(stopId);
        if(existToFrom)
            return new AppResponse("", "SMS From "+from+" to"+to+" blocked by STOP request.");
        
        // Create STOP OBJECT
        Stop stopObj = new Stop();
        stopObj.setId(stopId);
        stopObj.setFrom(from);
        stopObj.setTo(to);
        
        // Check counter if less than 50
        if(stopRepository.getCounter(stopObj) >= 50)
            return new AppResponse("", "Limit Reached for from: "+from);
        
        // Try, Check number of API Request and if counter less than 50 increment. and proceed.
        try {
            stopRepository.checkCounterAndIncrement(stopObj);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), "Failed to check and increment count.");
            return new AppResponse("", "Unknown Failure.");
        }
                
        return new AppResponse("OutBount sms ok", "");
    }
}
