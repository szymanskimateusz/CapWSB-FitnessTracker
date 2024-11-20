package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.SimpleUserDto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserEmailDto;
import org.springframework.stereotype.Component;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;

/**
 * Mapper class responsible for converting between User entities and various DTOs (Data Transfer Objects).
 */

@Component
public class UserMapper {

    /**
     * Converts a User entity to a UserDto.
     *
     * @param user the User entity to be converted.
     * @return a UserDto containing the user's details.
     */

    public UserDto toDto(User user) {
        return new UserDto(user.getId(),
                           user.getFirstName(),
                           user.getLastName(),
                           user.getBirthdate(),
                           user.getEmail());
    }

    /**
     * Converts a UserDto to a User entity.
     *
     * @param userDto the UserDto containing the user's details.
     * @return a User entity based on the provided UserDto.
     */

    public User toEntity(UserDto userDto) {
        return new User(
                        userDto.firstName(),
                        userDto.lastName(),
                        userDto.birthdate(),
                        userDto.email());
    }

    /**
     * Converts a User entity to a UserEmailDto, which contains only the user's ID and email.
     *
     * @param user the User entity to be converted.
     * @return a UserEmailDto with the user's ID and email.
     */

    UserEmailDto toUserEmailDto(User user) {
       return new UserEmailDto(user.getId(), user.getEmail());
    } // lab2

    /**
     * Converts a User entity to a SimpleUserDto, which contains only basic user information.
     *
     * @param user the User entity to be converted.
     * @return a SimpleUserDto containing the user's ID, first name, and last name.
     */

    SimpleUserDto toSimpleUserDto(User user) {
        return new SimpleUserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName());
    }

}
