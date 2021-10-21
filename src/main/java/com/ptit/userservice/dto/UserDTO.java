package com.ptit.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotBlank(message = "Can't be left blank")
    private String name;
    @NotBlank(message = "Can't be left blank")
    private String address;
}
