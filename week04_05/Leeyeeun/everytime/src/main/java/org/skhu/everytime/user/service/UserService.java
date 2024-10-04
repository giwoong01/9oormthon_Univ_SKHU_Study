package org.skhu.everytime.user.service;

import lombok.RequiredArgsConstructor;
import org.skhu.everytime.community.board.api.dto.request.PostRequestDto;
import org.skhu.everytime.user.Role;
import org.skhu.everytime.user.User;
import org.skhu.everytime.user.dto.UserProfileDto;
import org.skhu.everytime.user.dto.UserSignUpDto;
import org.skhu.everytime.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(UserSignUpDto userSignUpDto) throws Exception {
        // 입력값 공백 체크
        validateEmptyFields(userSignUpDto);

        // 이메일 형식 검증
        if (!isValidEmail(userSignUpDto.email())) {
            throw new Exception("이메일을 다시 확인해주세요.");
        }

        // 비밀번호와 비밀번호 확인 일치 여부 검증
        if (!userSignUpDto.password().equals(userSignUpDto.checkPassword())) {
            throw new Exception("비밀번호를 다시 확인해주세요.");
        }

        // 비밀번호 조건 검증
        if (!isValidPassword(userSignUpDto.password())) {
            throw new Exception("비밀번호는 대소문자, 특수문자, 숫자가 포함되어야 합니다.");
        }

        // 학교 이름 유효성 검사
        if (!isValidUniversityName(userSignUpDto.universityName())) {
            throw new Exception("학교 이름은 '대'로 끝나야 합니다.");
        }

        // 이메일 중복 체크
        if (userRepository.existsByEmail(userSignUpDto.email())) { // 수정된 부분
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        // 닉네임 중복 체크
        if (userRepository.findByNickname(userSignUpDto.nickName()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }

        // 사용자 객체 생성
        User user = User.builder()
                .email(userSignUpDto.email())
                .password(userSignUpDto.password())
                .nickname(userSignUpDto.nickName())
                .name(userSignUpDto.name())
                .universityName(userSignUpDto.universityName())
                .year(userSignUpDto.year())
                .role(Role.USER)
                .build();

        // 비밀번호 암호화
        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
    }

    public UserProfileDto getUserProfile(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User u = user.get();
            return new UserProfileDto(
                    u.getId(),
                    u.getEmail(),
                    u.getName(),
                    u.getNickname(),
                    u.getYear(),
                    u.getUniversityName()
            );
        } else {
            throw new RuntimeException("사용자를 찾을 수 없습니다."); // 예외 처리
        }
    }

    // 이메일 존재 여부를 확인하는 메서드
    public boolean isExistByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private void validateEmptyFields(UserSignUpDto userSignUpDto) throws Exception {
        if (userSignUpDto.email() == null || userSignUpDto.email().trim().isEmpty()) {
            throw new Exception("이메일이 공백일 수 없습니다.");
        }
        if (userSignUpDto.password() == null || userSignUpDto.password().trim().isEmpty()) {
            throw new Exception("비밀번호가 공백일 수 없습니다.");
        }
        if (userSignUpDto.checkPassword() == null || userSignUpDto.checkPassword().trim().isEmpty()) {
            throw new Exception("비밀번호 확인이 공백일 수 없습니다.");
        }
        if (userSignUpDto.nickName() == null || userSignUpDto.nickName().trim().isEmpty()) {
            throw new Exception("닉네임이 공백일 수 없습니다.");
        }
        if (userSignUpDto.name() == null || userSignUpDto.name().trim().isEmpty()) {
            throw new Exception("이름이 공백일 수 없습니다.");
        }
        if (userSignUpDto.universityName() == null || userSignUpDto.universityName().trim().isEmpty()) {
            throw new Exception("대학명이 공백일 수 없습니다.");
        }
        if (userSignUpDto.year() <= 0) {
            throw new Exception("연도는 양수여야 합니다.");
        }
    }

    // 이메일 형식 검증 메소드
    private boolean isValidEmail(String email) {
        String emailPattern = "^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,4}$";
        return Pattern.matches(emailPattern, email);
    }

    // 비밀번호 유효성 검사
    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return Pattern.matches(passwordPattern, password);
    }

    // 대학명 유효성 검사
    private boolean isValidUniversityName(String universityName) {
        return universityName.endsWith("대");
    }

}
