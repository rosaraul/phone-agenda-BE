package com.raul.phoneagenda.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class ContactDTO {
    private Long id;
    private String name;
    private String phoneNumber;
    private UserDTO userDTO;
}
