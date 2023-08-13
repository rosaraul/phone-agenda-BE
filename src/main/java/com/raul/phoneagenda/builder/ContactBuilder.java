package com.raul.phoneagenda.builder;

import com.raul.phoneagenda.dto.ContactDTO;
import com.raul.phoneagenda.model.Contact;

public class ContactBuilder {
    public static ContactDTO modelToDTO(Contact contact) {
        return ContactDTO.builder()
                .name(contact.getName())
                .id(contact.getId())
                .phoneNumber(contact.getPhoneNumber())
                .build();
    }
    public static Contact dtoToModel(ContactDTO contactDTO){
        return Contact.builder()
                .name(contactDTO.getName())
                .id(contactDTO.getId())
                .phoneNumber(contactDTO.getPhoneNumber())
                .user(UserBuilder.dtoToModel(contactDTO.getUserDTO()))
                .build();
    }
    public static Contact dtoToModelUserNull(ContactDTO contactDTO){
        return Contact.builder()
                .name(contactDTO.getName())
                .id(contactDTO.getId())
                .phoneNumber(contactDTO.getPhoneNumber())
                .build();
    }
}
