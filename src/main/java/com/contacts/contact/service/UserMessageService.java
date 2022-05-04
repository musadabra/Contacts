package com.contacts.contact.service;


import com.contacts.contact.model.AppResponse;
import com.contacts.contact.model.PhoneNumber;
import com.contacts.contact.model.Stop;
import com.contacts.contact.model.UserMessage;
import com.contacts.contact.repository.PhoneNumberRepository;
import com.contacts.contact.repository.StopRepository;
import com.contacts.contact.repository.UserAccountRepository;
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
    
    @Autowired
    private StopRepository stopRepository;
    
    private static final Logger LOG = Logger.getLogger(UserMessageService.class.getName());
    
    

    // Validate the number existance.
    // Make sure the authenticated user has the to number.
    // Check the TO number, return appropraite response.
    public AppResponse sendInbound(UserMessage message, long userId){
        // Find the phone number if it exist
        PhoneNumber phoneNumber = phoneNumberRepository.findByNumber(message.getTo());
        if(phoneNumber == null)
            return new AppResponse("", "To parameter not found");

        if(phoneNumber.getAccount().getId() != userId)
            return new AppResponse("", "To parameter not found");

        // Check message content for STOP OR stop or STOP\r\n
        String stopRegex = "(STOP|stop)\\s*\\n*";
        boolean messageContainStop = message.getText().matches(stopRegex);
        if(!messageContainStop)
            return new AppResponse("MATCH found", "");
        // Count the number of time saved in Redis
        
        
        // Save in tredis
        String from = message.getFrom();
            String to = message.getTo();
            String stopId = from+"_"+to;
            Stop stopObj = new Stop();
            stopObj.setId(stopId);
            stopObj.setFrom(from);
            stopObj.setTo(to);
        try {
            
            System.out.println("Herer++++");
            stopRepository.save(stopObj);
            System.out.println("After saving+++");
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), "Failed to save");
        }
        
//        List<Stop> findAll = stopRepository.findAll();
//        findAll.stream().forEach((t) -> {
//            System.out.println(t.getFrom()+" to: "+t.getTo()+" Counter: "+t.getCounter());
//        });
        Object findStopByFrom = stopRepository.findStopByFrom(stopObj);
        System.out.println(findStopByFrom);
        return new AppResponse("inbound sms ok", "");
    }
    
    public AppResponse sendOutBound(UserMessage message, long userId){
        return new AppResponse("inbound sms ok", "");
    }
}
