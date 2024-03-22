package app.carstore.service;


import app.carstore.model.dto.ForgottenPasswordDTO;
import app.carstore.model.dto.user.UserRegisterDTO;
import app.carstore.model.entity.UserEntity;
import app.carstore.model.entity.UserRoleEntity;
import app.carstore.model.enums.UserRoleEnum;
import app.carstore.model.mapper.UserMapper;

import app.carstore.model.view.UserDisplayView;
import app.carstore.repository.UserRepository;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;



@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserDetailsService userDetailsService;
    private final EmailService emailService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       UserMapper userMapper,
                       UserDetailsService userDetailsService,
                       EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.userDetailsService = userDetailsService;
        this.emailService = emailService;
    }

    public void createUserIfNotExist(String email) {

        var userOpt = this.userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            var newUser = new UserEntity().
                    setEmail(email).
                    setPassword(null).
                    setFirstName("FaceBook").
                    setLastName("User").
                    setUserRoles(List.of());
            userRepository.save(newUser);
        }
    }

    public void registerAndLogin(UserRegisterDTO userRegisterDto, Locale preferredLocale) {

        UserEntity newUser = userMapper.userDtoUserEntity(userRegisterDto);
        newUser.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));

        String verificationCode = RandomStringUtils.random(10);
        newUser.setVerificationCode(verificationCode);
        newUser.setActive(false);
        newUser.setUserRoles(List.of());

        userRepository.save(newUser);

        emailService.sendRegistrationEmail(newUser.getEmail(),
                newUser.getFirstName() + " " + newUser.getLastName(),preferredLocale, newUser );

    }


    public Page<UserDisplayView> findAllUsers(Pageable  pageable){
        return userRepository.findAll(pageable).
                map(userMapper::userEntityToUserView);

    }

    public boolean findEmailIsPresent(String email) {
        UserEntity user = userRepository.findByEmail(email).orElse(null);
        return user != null;
    }

    public void findUserAndSendEmail(String email, Locale resolveLocale) {

        UserEntity user = userRepository.findByEmail(email).orElseThrow();

        String randomPassword = RandomStringUtils.random(6);

        user.setPassword(randomPassword);

        this.userRepository.save(user);

        emailService.sendNewPassword(user,resolveLocale);


    }

    public void setUserNewPassword(ForgottenPasswordDTO forgottenPasswordDTO) {

       UserEntity user = userRepository.findByPassword(forgottenPasswordDTO.getGivenPassword()).orElseThrow();

        user.setPassword(passwordEncoder.encode(forgottenPasswordDTO.getPassword()));

        userRepository.save(user);
    }

    public boolean verify(String code) {
        UserEntity user = userRepository.findByVerificationCode(code);

        if (user == null || user.isActive()) {
            return false;
        }else {
            user.setVerificationCode(null);
            user.setActive(true);
            userRepository.save(user);
        }
        return true;
    }

    @Scheduled(cron = "0 0 00 * * *")
    private void deleteAllEnabledUsers() {
        userRepository.findAll()
                .stream()
                .filter(u -> !u.isActive())
                .forEach(user -> userRepository.deleteById(user.getId()));
    }

}

