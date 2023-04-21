package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.entity.registration.VerificationToken;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;

public interface VerifTokenService extends ReadWriteService<VerificationToken, Long> {
    void createVerificationToken(User user, String token);
}
