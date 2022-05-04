/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contacts.contact.repository;

import com.contacts.contact.model.Stop;
import java.util.ArrayList;
import java.util.List;
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
        try {
            template.opsForHash().put(stop.getId(), stop.getFrom(), stop.getTo());
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, "Failed to save to redis.");
        }
        return stop;
    }
    
    public List<String> findAll(){
        List<String> allValues = new ArrayList<>();
        try {
            allValues = template.opsForHash().values(HASH_KEY);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, null, "Failed to fetch all from redis.");
        }
        return allValues;
    }
    
    public String findStopByFrom(Stop stop){
        return (String) template.opsForHash().get(stop.getId(), stop.getFrom());
    }
    
    public long deleteStop(Stop stop){
        return template.opsForHash().delete(stop.getId(), stop.getFrom());
    }
}
