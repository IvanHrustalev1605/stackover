package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.registration.VerificationToken;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.UserRegistrationService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import com.javamentor.qa.platform.webapp.converters.UserConverter;
import org.springframework.stereotype.Service;


@Service
public class UserRegistrationServiceImpl extends ReadWriteServiceImpl<UserRegistrationDto, Long> implements UserRegistrationService {


    private final com.javamentor.qa.platform.service.abstracts.model.VerificationToken tokenRepository;
    private UserService userService;
    private final UserConverter userConverter;

    public UserRegistrationServiceImpl(ReadWriteDao<UserRegistrationDto, Long> readWriteDao,
                                       com.javamentor.qa.platform.service.abstracts.model.VerificationToken tokenRepository,
                                       UserService userService,
                                       UserConverter userConverter) {
        super(readWriteDao);
        this.tokenRepository = tokenRepository;
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @Override
    public User registerNewUserAccount(UserRegistrationDto userRegistrationDto) {
        if (userService.getByEmail(userRegistrationDto.getEmail()).isPresent()) {
            throw new RuntimeException("Пользователь с таким email уже зарегистрирован");
        }
            User user = new User();
            user.setEmail(userRegistrationDto.getEmail());
            user.setPassword(userRegistrationDto.getPassword());
            user.setFullName(userRegistrationDto.getFirstName());
        UserRegistrationDto userRegistrationDto1 = userConverter.userToUserRegistrationDto(user);
        super.persist(userRegistrationDto1);
        return user;
    }

    @Override
    public User getUser(String verificationToken) {
        User user = ((User) tokenRepository.findByToken(verificationToken));
        return user;
    }

    @Override
    public void saveRegisteredUser(User user) {
        userService.persist(user);
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(token, user);
        tokenRepository.save(verificationToken);
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return null;
    }
}
