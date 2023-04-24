package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class UserRegistrationMapper {
    public User toEntity(UserRegistrationDto userRegistrationDto) {
        if ( userRegistrationDto == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( userRegistrationDto.getEmail() );
        user.setPassword( userRegistrationDto.getPassword() );

        return user;
    };

    public UserRegistrationDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();

        userRegistrationDto.setEmail( user.getEmail() );
        userRegistrationDto.setPassword( user.getPassword() );

        return userRegistrationDto;
    };

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public User partialUpdate(UserRegistrationDto userRegistrationDto, @MappingTarget User user) {
        if ( userRegistrationDto == null ) {
            return null;
        }

        if ( userRegistrationDto.getEmail() != null ) {
            user.setEmail( userRegistrationDto.getEmail() );
        }
        if ( userRegistrationDto.getPassword() != null ) {
            user.setPassword( userRegistrationDto.getPassword() );
        }

        return user;
    };
}