package com.raul.phoneagenda.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder

public class UserDTO {
    private Long id;
    private String phoneNumber;
    private String name;
    private String password;
    private List<ContactDTO> contactDTOList;
}
