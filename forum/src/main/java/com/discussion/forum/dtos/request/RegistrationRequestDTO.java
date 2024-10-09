package com.discussion.forum.dtos.request;

import lombok.Data;

@Data
public class RegistrationRequestDTO {

    private String name;
    private String collageName;
    private String email;
    private String password;
    private String cousreId;
    private Integer semester;

}