package com.raul.phoneagenda.builder;

import com.raul.phoneagenda.dto.ContactDTO;
import com.raul.phoneagenda.dto.UserDTO;
import com.raul.phoneagenda.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserBuilder {
    public static UserDTO modelToDTO(User user){
        if(user == null){
            return  null;
        }
       List<ContactDTO> contactDTOList;
        if (user.getContactList() !=null){
            contactDTOList = user.getContactList().stream().map(ContactBuilder::modelToDTO)
                    .collect(Collectors.toList());
        }else {
            contactDTOList = new ArrayList<>();
        }
        return UserDTO
                .builder()
                .id(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .name(user.getName())
                .password(user.getPassword())
                .contactDTOList(contactDTOList)
                .build();
    }
    public static User dtoToModel(UserDTO userDTO){
        return User.builder()
                .id(userDTO.getId())
                .password(userDTO.getPassword())
                .phoneNumber(userDTO.getPhoneNumber())
                .name(userDTO.getName())
                .build();
    }
}
