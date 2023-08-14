package com.raul.phoneagenda.controller;

import com.raul.phoneagenda.dto.ContactDTO;
import com.raul.phoneagenda.dto.UserDTO;
import com.raul.phoneagenda.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/contacts")
public class ContactController {
    private final ContactService contactService;
    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }
    @GetMapping("/view_contacts")
    public ResponseEntity<List<ContactDTO>> seeAllContacts(){
        return new ResponseEntity<>(contactService.findAllContacts(), HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteContact(@PathVariable Long id){
        return new ResponseEntity<>(contactService.deleteContactById(id),HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<ContactDTO> addContact(@RequestBody ContactDTO contactDTO){
        return new ResponseEntity<>(contactService.createContact(contactDTO),HttpStatus.OK);
    }
    @PostMapping("/addForUser/{userId}")
    public ResponseEntity<ContactDTO> addContactForUser(@PathVariable Long userId,@RequestBody ContactDTO contactDTO){
        return new ResponseEntity<>(contactService.createContactForUser(userId,contactDTO),HttpStatus.OK);
    }
    @GetMapping("/view_contacts/name/{name}")
    public ResponseEntity<List<ContactDTO>> findContactByNmae(@PathVariable String name) throws Exception {
        return  new ResponseEntity<>(contactService.findContactsByName(name),HttpStatus.OK);
    }
    @GetMapping("/view_contacts/id/{id}")
    public ResponseEntity<ContactDTO> findContactById(@PathVariable Long id) throws Exception {
        return  new ResponseEntity<>(contactService.findContactsById(id),HttpStatus.OK);
    }
    @GetMapping("/view_contacts/phone/{phonenumber}")
    public ResponseEntity<ContactDTO> findContactByPhoneNumber(@PathVariable String phonenumber) throws Exception {
        return  new ResponseEntity<>(contactService.findContactsByPhoneNumber(phonenumber),HttpStatus.OK);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ContactDTO>> seeUserContacts(@PathVariable Long userId){
        return new ResponseEntity<>(contactService.getUserAgenda(userId),HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> updateContact(@PathVariable Long id,@RequestBody ContactDTO contactDTO){
        return new ResponseEntity<>(contactService.updateContact(contactDTO,id),HttpStatus.OK);
    }

}
