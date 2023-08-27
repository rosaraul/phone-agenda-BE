package com.raul.phoneagenda.service;

import com.raul.phoneagenda.builder.UserBuilder;
import com.raul.phoneagenda.dto.CredentialsDTO;
import com.raul.phoneagenda.dto.UserDTO;
import com.raul.phoneagenda.exception.CustomException;
import com.raul.phoneagenda.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final UserService userService;
    @Autowired
    public LoginService(UserService userService) {
        this.userService = userService;
    }

    public UserDTO loginUser(CredentialsDTO credentialsDto) throws CustomException {

        UserDTO userDto = userService.findUserByPhoneNumber(credentialsDto.getPhoneNumber());
        if(userDto == null){
            throw new CustomException("Bad credentials");
        }
        if(!userDto.getPassword().equals(credentialsDto.getPassword())){
            throw new CustomException("Bad credentials");

        }
        return userDto;
    }
}
