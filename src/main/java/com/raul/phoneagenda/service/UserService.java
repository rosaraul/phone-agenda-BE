package com.raul.phoneagenda.service;


import com.raul.phoneagenda.builder.ContactBuilder;
import com.raul.phoneagenda.builder.UserBuilder;
import com.raul.phoneagenda.dto.ContactDTO;
import com.raul.phoneagenda.dto.UserDTO;
import com.raul.phoneagenda.exception.CustomException;
import com.raul.phoneagenda.model.Contact;
import com.raul.phoneagenda.model.User;
import com.raul.phoneagenda.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public List<UserDTO> findAllUsers(){
        List<User> users = userRepository.findAllUsers();
        return users.stream().map(UserBuilder::modelToDTO).collect(Collectors.toList());
    }
    public UserDTO findUserById(Long userId) throws CustomException{
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new CustomException("User not found");
        }
        return UserBuilder.modelToDTO(userOptional.get());
    }

    public List<UserDTO> findUserByName(String name){
        List<User> users = userRepository.findAllUsers();
        List<User> filteredUsers = users.stream()
                .filter(user -> user.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        return filteredUsers.stream().map(UserBuilder::modelToDTO).collect(Collectors.toList());
    }

    public boolean deleteUserById(Long userId){
        Optional<User> userOptional =userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new EntityNotFoundException(String.format("User with the ID %s not found", userId)));
        try{
            userRepository.delete(user);
        }catch (Exception e){
            return  false;
        }
        return true;
    }
    public UserDTO createUser(UserDTO userDto){
        return UserBuilder.modelToDTO(userRepository.save(UserBuilder.dtoToModel(userDto)));

    }
    public UserDTO findUserByPhoneNumber(String phoneNumber) throws  CustomException{
        Optional<User> userOptional = Optional.ofNullable(userRepository.findFirstByPhoneNumber(phoneNumber));
        if(userOptional.isEmpty()){
            throw new CustomException("User not found");
        }
        return UserBuilder.modelToDTO(userOptional.get());
    }
}
