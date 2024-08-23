package com.example.loginstudy.user.service;

import com.example.loginstudy.user.domain.User;
import com.example.loginstudy.user.dto.request.LoginRequestDTO;
import com.example.loginstudy.user.dto.request.UserCreateRequestDTO;
import com.example.loginstudy.user.dto.response.LoginResponseDTO;
import com.example.loginstudy.user.dto.response.UserResponseDTO;
import com.example.loginstudy.user.exception.InvalidPasswordException;
import com.example.loginstudy.user.exception.UserNotFoundException;
import com.example.loginstudy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO signUp(UserCreateRequestDTO userCreateRequestDTO) {
        User user = userCreateRequestDTO.toEntity(passwordEncoder);
        userRepository.save(user);

        return UserResponseDTO.of(user);
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByEmail(loginRequestDTO.email())
                .orElseThrow(UserNotFoundException::new);

        validationPassword(loginRequestDTO.password(), user.getPassword());

        return LoginResponseDTO.from(user);
    }

    private void validationPassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new InvalidPasswordException();
        }
    }
}
