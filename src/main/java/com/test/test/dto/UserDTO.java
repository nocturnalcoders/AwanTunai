package com.test.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

    private String username;
    private String password;
    private String name;
    private String email;
    private Date createdAt;
    private Date updatedAt;

}
