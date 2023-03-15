package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public abstract class UserConverter {

    @Mapping(target = "fullName", expression = "java(urDto.getFirstName() + \" \" + urDto.getLastName())")
    public abstract User userRegistrationDtoToUser(UserRegistrationDto urDto);

    @Mapping(source = "fullName", target = "firstName", qualifiedByName = "fetchFirstName")
    @Mapping(source = "fullName", target = "lastName", qualifiedByName = "fetchLastName")
    public abstract UserRegistrationDto userToUserRegistrationDto(User user);


    @Named("fetchFirstName")
    public String fetchFirstName(String fullName) {
        if (fullName == null) {
            return null;
        }
        if (fullName.contains(" ")) {
            String[] splittedName = fullName.split(" ");
            StringBuilder firstName = new StringBuilder();
            for (int i = 0; i < (splittedName.length / 2); i++) {
                firstName.append(splittedName[i]).append(" ");
            }
            return firstName.toString().strip();
        }
        return fullName;
    }


    @Named("fetchLastName")
    public String fetchLastName(String fullName) {
        if (fullName == null) {
            return null;
        }
        if (fullName.contains(" ")) {
            String[] splittedName = fullName.split(" ");
            StringBuilder lastName = new StringBuilder();
            for (int i = (splittedName.length / 2); i < splittedName.length; i++) {
                lastName.append(splittedName[i]).append(" ");
            }
            return lastName.toString().strip();
        }
        return null;
    }
}
