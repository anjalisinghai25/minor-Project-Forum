package com.discussion.forum.controller;


import com.discussion.forum.dtos.BaseResponseDTO;
import com.discussion.forum.dtos.request.LoginDTO;
import com.discussion.forum.dtos.request.RegistrationRequestDTO;
import com.discussion.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/onboard")
public class OnBoardingController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponseDTO> registerUser(@RequestBody RegistrationRequestDTO requestDTO) {
        return userService.registerUser(requestDTO).createResponseEntity();
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponseDTO> loginUser(@RequestBody LoginDTO loginDTO) {
        return userService.loginUserAndGenrateToken(loginDTO).createResponseEntity();
    }

    @GetMapping("/validate")
    public ResponseEntity<BaseResponseDTO> validateUser() {
        return userService.validateUser().createResponseEntity();
    }

}
