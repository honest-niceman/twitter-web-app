package com.example.twitterwebapp.domain.mappers;

import com.example.twitterwebapp.domain.dtos.UserCredentials;
import com.example.twitterwebapp.domain.dtos.UserDto;
import com.example.twitterwebapp.domain.dtos.UserRegistrationDto;
import com.example.twitterwebapp.domain.entities.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
    User userCredentialsToUser(UserCredentials userCredentials);

    UserCredentials userToUserCredentials(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUserFromUserCredentials(UserCredentials userCredentials,
                                       @MappingTarget User user);

    User userDtoToUser(UserDto userDto);

    UserDto userToUserDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUserFromUserDto(UserDto userDto,
                               @MappingTarget User user);

    User userRegistrationDtoToUser(UserRegistrationDto userRegistrationDto);

    UserRegistrationDto userToUserRegistrationDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUserFromUserRegistrationDto(UserRegistrationDto userRegistrationDto,
                                           @MappingTarget User user);
}
