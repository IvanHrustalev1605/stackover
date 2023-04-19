package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserConverter {
    UserConverter MAPPER = Mappers.getMapper(UserConverter.class);
    @Mapping(target = "fullName", source = "firstName")
    User userRegistrationDtoToUser(UserRegistrationDto userRegistrationDto);
    @Mapping(target = "firstName", source = "fullName")

    UserRegistrationDto userToUserRegistrationDto(User user);

}
