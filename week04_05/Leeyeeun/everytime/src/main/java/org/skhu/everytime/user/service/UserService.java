package org.skhu.everytime.user.service;

import lombok.RequiredArgsConstructor;
import org.skhu.everytime.user.Role;
import org.skhu.everytime.user.User;
import org.skhu.everytime.user.dto.UserSignUpDto;
import org.skhu.everytime.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(UserSignUpDto userSignUpDto) throws Exception {
        // 입력값 공백
        validateEmptyFields(userSignUpDto);

        // 이메일 형식
        if (!isValidEmail(userSignUpDto.getEmail())) {
            throw new Exception("이메일을 다시 확인해주세요.");
        }

        // 비밀번호 확인 일치 여부
        if (!userSignUpDto.getPassword().equals(userSignUpDto.getCheckPassword())) {
            throw new Exception("비밀번호를 다시 확인해주세요.");
        }

        // 비밀번호 조건
        if (!isValidPassword(userSignUpDto.getPassword())) {
            throw new Exception("비밀번호는 대소문자, 특수문자, 숫자가 포함되어야 합니다.");
        }

        // 이메일 중복
        if (userRepository.findByEmail(userSignUpDto.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        // 닉네임 중복
        if (userRepository.findByNickname(userSignUpDto.getNickname()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }

        User user = User.builder()
                .email(userSignUpDto.getEmail())
                .password(userSignUpDto.getPassword())
                .nickname(userSignUpDto.getNickname())
                .name(userSignUpDto.getName())
                .universityName(userSignUpDto.getUniversityName())
                .year(userSignUpDto.getYear())
                .role(Role.USER)
                .build();

        user.passwordEncode(passwordEncoder); // 비밀번호 암호화
        userRepository.save(user);
    }

    private void validateEmptyFields(UserSignUpDto userSignUpDto) throws Exception {
        if (userSignUpDto.getEmail() == null || userSignUpDto.getEmail().trim().isEmpty()) {
            throw new Exception("이메일이 공백일 수 없습니다.");
        }
        if (userSignUpDto.getPassword() == null || userSignUpDto.getPassword().trim().isEmpty()) {
            throw new Exception("비밀번호가 공백일 수 없습니다.");
        }
        if (userSignUpDto.getNickname() == null || userSignUpDto.getNickname().trim().isEmpty()) {
            throw new Exception("닉네임이 공백일 수 없습니다.");
        }
        if (userSignUpDto.getName() == null || userSignUpDto.getName().trim().isEmpty()) {
            throw new Exception("이름이 공백일 수 없습니다.");
        }
        if (userSignUpDto.getUniversityName() == null || userSignUpDto.getUniversityName().trim().isEmpty()) {
            throw new Exception("대학명이 공백일 수 없습니다.");
        }
        if (userSignUpDto.getYear() <= 0) {
            throw new Exception("연도는 양수여야 합니다.");
        }
    }

    // 이메일 형식 검증 메소드
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailRegex, email);
    }

    // 비밀번호 조건 검증 메소드
    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return Pattern.matches(passwordRegex, password);
    }
}
