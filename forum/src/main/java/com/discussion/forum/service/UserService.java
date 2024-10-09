package com.discussion.forum.service;

import com.discussion.forum.dtos.BaseResponseDTO;
import com.discussion.forum.dtos.request.LoginDTO;
import com.discussion.forum.dtos.request.RegistrationRequestDTO;
import com.discussion.forum.entities.User;
import com.discussion.forum.exception.ResourceNotFoundException;
import com.discussion.forum.repository.CourseRepository;
import com.discussion.forum.repository.UserRepository;
import com.discussion.forum.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpServletRequest context;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private JwtUtil jwtUtil;


    public BaseResponseDTO registerUser(RegistrationRequestDTO requestDTO) {

        User user = new User();

        Optional<User> byEmail = userRepository.findByEmail(requestDTO.getEmail());

        if (byEmail.isPresent()) {
            throw new RuntimeException("Email already exist");
        } else {
            user.setEmail(requestDTO.getEmail());
            user.setName(requestDTO.getName());
            user.setPassword(requestDTO.getPassword());
            user.setCollageName(requestDTO.getCollageName());
            user.setCourse(courseRepository.findById(requestDTO.getCousreId()).orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + requestDTO.getCousreId())));
            user.setSemester(requestDTO.getSemester());

            User save = userRepository.save(user);

            return BaseResponseDTO.builder().message("User Registered").data(save).build();
        }


    }

    public BaseResponseDTO loginUserAndGenrateToken(LoginDTO loginDTO) {
        Optional<User> byEmail = userRepository.findByEmail(loginDTO.getEmail());

        if (byEmail.isPresent()) {

            if (byEmail.get().getPassword().equals(loginDTO.getPassword())) {
                String token = jwtUtil.generateToken(byEmail.get());

                return BaseResponseDTO.builder().data(Map.of("token", token)).message("Welcome.").build();
            } else {
                return BaseResponseDTO.builder().message("Invalid Credentials").status(403).build();
            }
        } else {
            throw new ResourceNotFoundException("User not found with email " + loginDTO.getEmail());
        }
    }

    public BaseResponseDTO validateUser() {
        String token = context.getHeader("Authorization");

        Optional<User> optionalUsers = userRepository.findById(jwtUtil.extractUserId(token));

        HashMap<String, Object> resp = new HashMap<>();

        if (optionalUsers.isPresent() && jwtUtil.validateToken(token, optionalUsers.get())) {
            return new BaseResponseDTO(Map.of("user", optionalUsers.get()), "Welcome!!", 200);
        } else {
            return new BaseResponseDTO(null, "Invalid Token.", 401);
        }
    }
}
