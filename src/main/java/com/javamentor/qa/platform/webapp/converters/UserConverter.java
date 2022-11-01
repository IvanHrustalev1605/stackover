package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.UserDto.UserDtoBuilder;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto.UserRegistrationDtoBuilder;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

@Mapper
public abstract class UserConverter {

    public UserDto userRegistrationDtoToUser(UserRegistrationDto registrationDto) {
        UserDtoBuilder userDto = UserDto.builder();
        userDto.fullName(registrationDto.getFirstName() +" "+ registrationDto.getLastName());
        userDto.email(registrationDto.getEmail());
        userDto.registrationDate(LocalDateTime.now());
        return userDto.build();
    }

    public UserRegistrationDto userToUserRegistrationDto(UserDto userDto) {
        UserRegistrationDtoBuilder userRegistrationDto = UserRegistrationDto.builder();
        userRegistrationDto.firstName(userDto.getFullName().split(" ")[0]);
        userRegistrationDto.lastName(userDto.getFullName().split(" ")[1]);
        userRegistrationDto.email(userDto.getEmail());
        return userRegistrationDto.build();
    }

}