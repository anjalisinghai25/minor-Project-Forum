package com.discussion.forum.dtos.request;

import lombok.Data;

@Data
public class LoginDTO {
    private String email;
    private String password;
}
