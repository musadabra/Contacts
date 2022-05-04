/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contacts.contact.config;

import com.contacts.contact.model.UserAccount;
import com.contacts.contact.repository.UserAccountRepository;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Musa Dabra
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserAccountRepository userRepository;
   
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = userRepository.findByUsername(username);
        if(user == null)
            throw new UsernameNotFoundException("Username "+username+" Not found.");
        return new User(user.getUsername(), user.getPassword(),Collections.emptyList());
    }
}