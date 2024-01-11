package leeseunghee.study.jwttutorial.service;

import leeseunghee.study.jwttutorial.dto.UserDto;
import leeseunghee.study.jwttutorial.entity.Authority;
import leeseunghee.study.jwttutorial.entity.User;
import leeseunghee.study.jwttutorial.repository.UserRepository;
import leeseunghee.study.jwttutorial.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 회원가입
     */
    @Transactional
    public UserDto signup(final UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername())
                .orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        final Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        final User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        userRepository.save(user);

        return new UserDto(user);
    }

    // username 으로 유저 정보 가져오기
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(final String username) {
        return userRepository.findOneWithAuthoritiesByUsername(username);
    }

    // Security Context 에 저장된 username 의 유저 정보 가져오기
    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername()
                .flatMap(userRepository::findOneWithAuthoritiesByUsername);
    }
}
