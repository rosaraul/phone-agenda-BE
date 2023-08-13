package com.raul.phoneagenda.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDTO {
    private String phoneNumber;
    private String password;
}
