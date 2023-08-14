package com.raul.phoneagenda.service;

import com.raul.phoneagenda.builder.ContactBuilder;
import com.raul.phoneagenda.builder.UserBuilder;
import com.raul.phoneagenda.dto.ContactDTO;
import com.raul.phoneagenda.exception.CustomException;
import com.raul.phoneagenda.model.Contact;
import com.raul.phoneagenda.model.User;
import com.raul.phoneagenda.repository.ContactRepository;
import com.raul.phoneagenda.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final UserRepository userRepository;
    @Autowired
    public ContactService(ContactRepository contactRepository,  UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }
    public List<ContactDTO> findAllContacts(){
        List<Contact> contacts = contactRepository.findAllContacts();
        return contacts.stream().map(ContactBuilder::modelToDTO).collect(Collectors.toList());

    }
    public List<ContactDTO> findContactsByName(String name){
        List<Contact> contacts = contactRepository.findAllContacts();
        List<Contact> filteredContacts = contacts.stream()
                .filter(contact -> contact.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        return filteredContacts.stream().map(ContactBuilder::modelToDTO).collect(Collectors.toList());

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
    public ContactDTO createContactForUser(Long userId, ContactDTO contactDTO) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new EntityNotFoundException(String.format("User with ID %s not found", userId)));

        Contact newContact = ContactBuilder.dtoToModelUserNull(contactDTO);
        newContact.setUser(user);
        Contact savedContact = contactRepository.save(newContact);
        return ContactBuilder.modelToDTO(savedContact);
    }

    public ContactDTO findContactsByPhoneNumber(String phoneNumber) throws  CustomException{
        Optional<Contact> contactOptional = Optional.ofNullable(contactRepository.findFirstByPhoneNumber(phoneNumber));
        if(contactOptional.isEmpty()){
            throw new CustomException("Contact not found");
        }
        return ContactBuilder.modelToDTO(contactOptional.get());
    }
    public ContactDTO findContactsById(Long id) throws  CustomException{
        Optional<Contact> contactOptional = contactRepository.findById(id);
        if(contactOptional.isEmpty()){
            throw new CustomException("Contact not found");
        }
        return ContactBuilder.modelToDTO(contactOptional.get());
    }

    public List<ContactDTO> getUserAgenda(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new EntityNotFoundException(String.format("User with ID %s not found", userId)));

        List<Contact> userContacts = user.getContactList();
        return userContacts.stream()
                .map(ContactBuilder::modelToDTO)
                .collect(Collectors.toList());
    }

}
