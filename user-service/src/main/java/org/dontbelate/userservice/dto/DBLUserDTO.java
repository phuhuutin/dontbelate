package org.dontbelate.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DBLUserDTO {
    private String username;
    private String email;
    private String street;
    private String city;
    private String state;
    private String zipCode;

}
