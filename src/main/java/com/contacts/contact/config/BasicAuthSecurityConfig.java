/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contacts.contact.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Musa Dabra
 */
@Configuration
@EnableWebSecurity
public class BasicAuthSecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Autowired
    private UserService userDetailsService;
 
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Take user details and password to authenticate.
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Disable CSRF
        http.csrf().disable()
                .authorizeRequests()
                // Allow all users to access auth url
                .antMatchers("/api/auth")
                .permitAll()
                // Authentication needed for any other request
                .anyRequest()
                .authenticated()
                .and()
                // Using Http Basic Authentication
                .httpBasic()
                // Permit all other request without authentication
                .and()
                // Redirected Any Forbidden request.
                .exceptionHandling().accessDeniedPage("/403");
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        // Get user details from DB or any specified source.
        return new UserService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Custom encording, by default spring security if Bcrypt,
        // In our case password validation is done with plain text based on the data provice
        // In a production the default encorder would be use or any specified encording type.
        return new CustomPasswordEncoder();
    }
}