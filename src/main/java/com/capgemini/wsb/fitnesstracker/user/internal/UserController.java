package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;

import java.time.LocalDate;
import java.util.List;

/**
 * REST controller responsible for handling user-related HTTP requests.
 * Provides endpoints for creating, updating, deleting, and retrieving users.
 */

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    /**
     * Retrieves all users in the system.
     *
     * @return a list of UserDto objects representing all users.
     */

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

    /**
     * Retrieves a specific user by their ID.
     *
     * @param userId the ID of the user to retrieve.
     * @return a UserDto representing the user.
     * @throws UserNotFoundException if no user with the provided ID is found.
     */

    @GetMapping("/{userId}") // lab 2
    public UserDto getUser(@PathVariable Long userId) {
        return userService.getUser(userId).map(userMapper::toDto).orElseThrow(() -> new UserNotFoundException(userId));
    }

    /**
     * Retrieves a list of users filtered by their email.
     * If no email is provided, returns all users.
     *
     * @param email the optional email to filter users by.
     * @return a list of UserEmailDto objects with user ID and email.
     */

    @GetMapping("/email") // lab 2
    public List<UserEmailDto> getUsersByEmail(@RequestParam(required = false) String email) {
        if (email != null)
            return userService.getUserByEmail(email).stream().map(userMapper::toUserEmailDto).toList();

        return userService.findAllUsers().stream().map(userMapper::toUserEmailDto).toList();
    }

    /**
     * Updates an existing user's details.
     *
     * @param userId the ID of the user to update.
     * @param userDto the UserDto with updated user details.
     * @return the updated UserDto.
     * @throws DuplicateEmailException if the new email already exists in the system.
     */

    @PutMapping("/{userId}") // lab 2
    public UserDto updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        if (userService.getUserByEmail(userDto.email()).isPresent())
            throw new DuplicateEmailException(userDto.email());

        User updatedUser = userService.updateUser(userId, userDto);
        return userMapper.toDto(updatedUser);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userId the ID of the user to delete.
     */

    @DeleteMapping("/{userId}") // lab 2
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    /**
     * Creates a new user.
     *
     * @param userDto the UserDto containing the details of the new user.
     * @return the created User entity.
     * @throws DuplicateEmailException if a user with the provided email already exists.
     * @throws InterruptedException if there is an interruption in the process.
     */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody UserDto userDto) throws InterruptedException {
        System.out.println("User with e-mail: " + userDto.email() + "passed to the request");

        if (userService.getUserByEmail(userDto.email()).isPresent())
            throw new DuplicateEmailException(userDto.email());

        User user = userMapper.toEntity(userDto);
        return userService.createUser(user);
    }

    /**
     * Retrieves a list of users with basic information (ID, first name, last name).
     *
     * @return a list of SimpleUserDto objects.
     */

    @GetMapping("/simple")
    public List<SimpleUserDto> getSimpleUsers() {
        return userService.findAllUsers().stream().map(userMapper::toSimpleUserDto).toList();
    }

    /**
     * Retrieves a list of users who were born before a specific date.
     *
     * @param date the cutoff birthdate in the format "yyyy-MM-dd".
     * @return a list of UserDto objects representing users born before the specified date.
     */

    @GetMapping("/older-than/{date}")
    public List<UserDto> getUserByDate(@PathVariable String date) {
        LocalDate beforeDate = LocalDate.parse(date);
        return userService.findAllUsers().stream().filter(user -> user.getBirthdate().isBefore(beforeDate)).map(userMapper::toDto).toList();
    }

}