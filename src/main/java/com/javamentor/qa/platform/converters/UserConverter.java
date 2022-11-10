package com.javamentor.qa.platform.converters;

import com.javamentor.qa.platform.models.dto.UserDto.UserDtoBuilder;
import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto.UserRegistrationDtoBuilder;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.User.UserBuilder;
import com.javamentor.qa.platform.service.abstracts.model.RoleService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Mapper(componentModel = "spring")
public abstract class UserConverter {

    @Autowired
    private RoleService roleService;

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

    public User userRegistrationDtoDtoToUser(UserRegistrationDto userRegistrationDto) {
        UserBuilder user = User.builder();
        user.fullName(userRegistrationDto.getFirstName() +" "+ userRegistrationDto.getLastName());
        user.email(userRegistrationDto.getEmail());
        user.persistDateTime(LocalDateTime.now());
        user.password(userRegistrationDto.getPassword());
        user.activationCode(userRegistrationDto.getActivationCode());
        user.role(roleService.getByName("ROLE_USER").get());
        user.isEnabled(false);
        user.isDeleted(false);
        return user.build();
    }

}