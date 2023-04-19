package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.registration.VerificationToken;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

public interface UserRegistrationService extends ReadWriteService<UserRegistrationDto, Long> {
    User registerNewUserAccount(UserRegistrationDto userDto);

    User getUser(String verificationToken);

    void saveRegisteredUser(User user);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);
}
