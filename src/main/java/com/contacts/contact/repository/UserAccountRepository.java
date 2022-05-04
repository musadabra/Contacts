package com.contacts.contact.repository;

import com.contacts.contact.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    public UserAccount findByUsername(String username);
}
