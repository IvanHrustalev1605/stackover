package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserRegistrationMapper {
    User toEntity(UserRegistrationDto userRegistrationDto);

    UserRegistrationDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserRegistrationDto userRegistrationDto, @MappingTarget User user);
}