package com.javamentor.qa.platform.dao.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.dto.UserRegistrationDtoDao;
import com.javamentor.qa.platform.dao.impl.repository.ReadWriteDaoImpl;
import com.javamentor.qa.platform.dao.util.SingleResultUtil;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.service.EmailService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRegistrationDtoDaoImpl extends ReadWriteDaoImpl<UserRegistrationDto, Long> implements UserRegistrationDtoDao {

    @PersistenceContext
    private EntityManager entityManager;
    private final UserService userService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationDtoDaoImpl(UserService userService, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UserRegistrationDto> getUserRegistrationDtoByActivationCode(String activationCode) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("""
                        SELECT  substring(User.fullName,1) as first_name, substring(User.fullName,1)  as last_name, User.email as email, User.password as password, User.activationCode as activation_code
                                                FROM User
                                                WHERE User.activationCode=:activationCode
                        """)
                .setParameter("activationCode", activationCode));
    }

    public boolean addUserRegistrationDto(UserRegistrationDto userRegistrationDto) {
        if (userService.getByEmail(userRegistrationDto.getEmail()).isPresent()) {
            return false;
        }
        userRegistrationDto.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        userRegistrationDto.setActivationCode(UUID.randomUUID().toString());
        entityManager.persist(userRegistrationDto);

        if (StringUtils.hasLength(userRegistrationDto.getEmail())) {
            String message = String.format("Hello, %s \n" +
                            "Please follow the link to complete your registration: http://localhost:8080/api/user/registration/verify/%s",
                    userRegistrationDto.getFirstName(),
                    userRegistrationDto.getActivationCode());
            emailService.send(userRegistrationDto.getEmail(), "Account verify", message);
        }
        return true;
    }

    public boolean verifyUserRegistrationDto(String activationCode) {
        Optional<UserRegistrationDto> userRegistrationDto = getUserRegistrationDtoByActivationCode(activationCode);
        if (userRegistrationDto.isEmpty()) {
            return false;
        }
        userRegistrationDto.get().setActivationCode(null);
        entityManager.persist(userRegistrationDto);
        return true;
    }
}
