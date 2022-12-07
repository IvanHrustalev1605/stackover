package com.javamentor.qa.platform.webapp.configs.converters;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper(componentModel="spring")
public abstract class UserConverter {

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    protected UserConverter() {
    }


    public User userRegistrationDtoToUser(UserRegistrationDto u){


        User user = new User();
        user.setFullName(u.getFirstName()+" "+u.getLastName());
        user.setEmail(u.getEmail());
        user.setPassword(passwordEncoder.encode(u.getPassword()));
        user.setRole(new Role("ROLE_USER"));

        return user;
    }

    public UserRegistrationDto userToUserRegistrationDto(User u){
        String [] fullName = u.getFullName().split("\\s");
        String firstName = fullName[0];
        String lastName = fullName[1];

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setFirstName(firstName);
        userRegistrationDto.setLastName(lastName);
        userRegistrationDto.setEmail(u.getEmail());
        userRegistrationDto.setPassword(passwordEncoder.encode(u.getPassword()));
        return userRegistrationDto;
    }


}
