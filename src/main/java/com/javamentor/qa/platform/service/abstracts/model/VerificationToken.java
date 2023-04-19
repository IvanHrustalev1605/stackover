package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationToken extends JpaRepository
        <com.javamentor.qa.platform.models.entity.registration.VerificationToken, Long> {
    VerificationToken findByToken(String token);

    VerificationToken findByUser(UserRegistrationDto user);
}
