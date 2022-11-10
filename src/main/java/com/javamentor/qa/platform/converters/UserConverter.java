package com.javamentor.qa.platform.converters;

import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import org.mapstruct.Mapper;

@Mapper
public abstract class UserConverter {

    public abstract UserDto userRegistrationDtoToUser(UserRegistrationDto registrationDto);

    public abstract UserRegistrationDto userToUserRegistrationDto(UserDto userDto);
}