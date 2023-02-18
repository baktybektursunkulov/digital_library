package kz.satbayev.university.dto;

import lombok.Data;



@Data
public class AuthorizationRequestDto {
    private String username;
    private String password;
}
