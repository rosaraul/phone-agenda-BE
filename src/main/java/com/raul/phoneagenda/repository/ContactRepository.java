package com.raul.phoneagenda.repository;

import com.raul.phoneagenda.model.Contact;
import com.raul.phoneagenda.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    Contact findFirstById(Long id);
    @Query(value = "SELECT * FROM contact ",nativeQuery = true)
    List<Contact> findAllContacts();
    List<Contact> findAllByName(String name);
    Contact findFirstByPhoneNumber(String phoneNumber);

}
