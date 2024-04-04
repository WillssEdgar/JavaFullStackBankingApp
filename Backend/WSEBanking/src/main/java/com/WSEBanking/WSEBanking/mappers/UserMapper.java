package com.WSEBanking.WSEBanking.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.WSEBanking.WSEBanking.api.DTOs.SignUpDto;
import com.WSEBanking.WSEBanking.api.DTOs.UserDto;
import com.WSEBanking.WSEBanking.api.model.User;

/**
 * Mapper interface for mapping User objects and DTOs.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Maps a User entity to a UserDto.
     *
     * @param user The User entity to map.
     * @return A UserDto containing mapped fields.
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "firstname", target = "firstName")
    @Mapping(source = "lastname", target = "lastName")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "token", ignore = true)
    UserDto toUserDto(User user);

    /**
     * Maps a SignUpDto to a User entity.
     *
     * @param signUpDto The SignUpDto to map.
     * @return A User entity containing mapped fields.
     */
    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);
}
