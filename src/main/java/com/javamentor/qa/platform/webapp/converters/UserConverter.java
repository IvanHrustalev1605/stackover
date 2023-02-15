package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@Mapper(componentModel = "spring")
public abstract class UserConverter {

    public static UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserConverter() {
    }

    public User userRegistrationDtoToUser(UserRegistrationDto userRegistrationDto) {

        User user = new User();
        user.setEmail(userRegistrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        user.setFullName(userRegistrationDto.getFirstName() + " " +
                userRegistrationDto.getLastName());
        user.setRole(new Role("ROLE_USER"));
        return user;
    }

    public UserRegistrationDto userToUserRegistrationDto(User user) {
        return null;
    }
}
