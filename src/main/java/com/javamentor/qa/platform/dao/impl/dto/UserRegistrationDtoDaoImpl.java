package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserRegistrationDtoDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.service.EmailService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserRegistrationDtoDaoImpl extends ReadWriteDaoImpl<UserRegistrationDto, Long> implements UserRegistrationDtoDao {

    @PersistenceContext
    private EntityManager entityManager;
    private final UserService userService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    public UserRegistrationDtoDaoImpl(UserService userService, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean addRegisterUserDto(UserRegistrationDto userRegistrationDto) {
        if (userService.getByEmail(userRegistrationDto.getEmail()).isPresent()) {
            return false;
        }
        userRegistrationDto.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        entityManager.persist(userRegistrationDto);

        if (StringUtils.hasLength(userRegistrationDto.getEmail())) {
            String message = String.format("Hello, %s \n" +
                            "Please follow the link to complete your registration: http://localhost:8080/api/user/registration/verify",
                    userRegistrationDto.getFirstName());
            emailService.send(userRegistrationDto.getEmail(), "Account verify", message);
        }
        return true;
    }

    @Override
    public boolean verifyUserRegistrationDto(UserRegistrationDto userRegistrationDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userRegistrationDto.getEmail(), userRegistrationDto.getPassword());
        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (ArithmeticException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
        return true;
    }
}
