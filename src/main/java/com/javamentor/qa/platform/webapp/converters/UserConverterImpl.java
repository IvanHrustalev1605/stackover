package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-19T23:57:06+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 19.0.2 (Oracle Corporation)"
)
@Component
public class UserConverterImpl implements UserConverter {

    @Override
    public User userRegistrationDtoToUser(UserRegistrationDto userRegistrationDto) {
        if ( userRegistrationDto == null ) {
            return null;
        }

        User user = new User();

        user.setFullName( userRegistrationDto.getFirstName() );
        user.setEmail( userRegistrationDto.getEmail() );
        user.setPassword( userRegistrationDto.getPassword() );

        return user;
    }

    @Override
    public UserRegistrationDto userToUserRegistrationDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();

        userRegistrationDto.setFirstName( user.getFullName() );
        userRegistrationDto.setEmail( user.getEmail() );
        userRegistrationDto.setPassword( user.getPassword() );

        return userRegistrationDto;
    }
}
