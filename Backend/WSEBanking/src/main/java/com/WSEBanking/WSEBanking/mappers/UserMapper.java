package com.WSEBanking.WSEBanking.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.WSEBanking.WSEBanking.api.DTOs.SignUpDto;
import com.WSEBanking.WSEBanking.api.DTOs.UserDto;
import com.WSEBanking.WSEBanking.api.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "firstname", target = "firstName")
    @Mapping(source = "lastname", target = "lastName")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "token", ignore = true)
    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);
}
