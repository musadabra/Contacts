/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contacts.contact.repository;

import com.contacts.contact.model.Stop;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author dell
 */
@Repository
public class StopRepository {
    
    public String HASH_KEY;
    
    @Autowired
    private RedisTemplate template;
    private static final Logger LOG = Logger.getLogger(StopRepository.class.getName());
    
    public Stop save(Stop stop){
        template.opsForHash().put(stop.getId(), stop.getFrom(), stop.getTo());
        return stop;
    }
    
    public Stop saveWithExpiration(Stop stop){
        // Save data.
        template.opsForHash().put(stop.getId(), stop.getFrom(), stop.getTo());
        // Set expiration time
        long expirationTimeHours = 60*60*4;
        template.expire(stop.getId(), expirationTimeHours, TimeUnit.SECONDS);
        return stop;
    }
    
    public String findStopByFrom(Stop stop){
        return (String) template.opsForHash().get(stop.getId(), stop.getFrom());
    }
    
    public long deleteStop(Stop stop){
        return template.opsForHash().delete(stop.getId(), stop.getFrom());
    }

    public boolean findStopByToFrom(String stopId) {
        return template.opsForHash().values(stopId).isEmpty();
    }
    
    public int getCounter(Stop stop){
//        Object get = template.opsForHash().get(stop.getId(), stop.getFrom());
//        return template.opsForHash().increment(stop.getId(), stop.getFrom(), 1);
        return 1;
    }
    
    public void checkCounterAndIncrement(Stop stop) {
        template.opsForHash().increment(stop.getId(), stop.getFrom(), 1);
    }
}
