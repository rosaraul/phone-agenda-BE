package com.raul.phoneagenda.service;

import com.raul.phoneagenda.builder.ContactBuilder;
import com.raul.phoneagenda.dto.ContactDTO;
import com.raul.phoneagenda.exception.CustomException;
import com.raul.phoneagenda.model.Contact;
import com.raul.phoneagenda.repository.ContactRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactService {
    private final ContactRepository contactRepository;
    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }
    public List<ContactDTO> findAllContacts(){
        List<Contact> contacts = contactRepository.findAllContacts();
        return contacts.stream().map(ContactBuilder::modelToDTO).collect(Collectors.toList());

    }
    public List<ContactDTO> findContactsByName(String name){
        List<Contact> contacts = contactRepository.findAllByName(name);
        return contacts.stream().map(ContactBuilder::modelToDTO).collect(Collectors.toList());
    }
    public ContactDTO createContact(ContactDTO contactDTO){
        return ContactBuilder.modelToDTO(contactRepository.save(ContactBuilder.dtoToModelUserNull(contactDTO)));
    }
    public boolean updateContact(ContactDTO contactDTO, Long contactId){
        Optional<Contact> contact = contactRepository.findById(contactId);
        Contact contactToUpdate = contact.orElseThrow(() -> new EntityNotFoundException(String.format("Contact with the ID %s not found", contact)));
        try {
            contactToUpdate.setName(contactDTO.getName());
            contactToUpdate.setPhoneNumber(contactDTO.getPhoneNumber());
            contactRepository.save(contactToUpdate);
        }catch (Exception exception){
            return false;
        }
        return true;
    }
    public boolean deleteContactById(Long contactId) {
        Optional<Contact> contactOptional = contactRepository.findById(contactId);
        Contact contact = contactOptional.orElseThrow(() -> new EntityNotFoundException(String.format("Contact with the ID %s not found", contactId)));
        try {
            contactRepository.delete(contact);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
