package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.*;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for CRUD operations.
 */

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    /**
     * Creates a new user in the system.
     *
     * @param user the User entity to be created.
     * @return the created User entity.
     * @throws IllegalArgumentException if the user already has a database ID (indicating it exists in the database).
     */

    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    /**
     * Deletes a user from the system by their ID.
     *
     * @param userId the ID of the user to be deleted.
     * @throws UserNotFoundException if no user with the provided ID is found.
     */

    @Override // lab 2
    public void deleteUser(final Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        log.info("Deleting User {}", userId);
        userRepository.delete(user);
    }

    /**
     * Updates the details of an existing user based on the provided UserDto.
     *
     * @param userId the ID of the user to be updated.
     * @param userDto the data transfer object containing user information.
     * @return the updated User entity.
     * @throws UserNotFoundException if no user with the provided ID is found.
     */

    @Override // lab 2
    public User updateUser(final Long userId, final UserDto userDto) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(userId));

        if (userDto.firstName() != null)
            user.setFirstName(userDto.firstName());
        if (userDto.lastName() != null)
            user.setLastName(userDto.lastName());
        if (userDto.birthdate() != null)
            user.setBirthdate(userDto.birthdate());
        if (userDto.email() != null)
            user.setEmail(userDto.email());

        log.info("Updating User {}", userId);
        return userRepository.save(user);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the ID of the user to be retrieved.
     * @return an Optional containing the User entity, or empty if not found.
     */

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId).stream().findFirst();
    }

    /**
     * Retrieves a user by their email address.
     *
     * @param email the email address of the user to be retrieved.
     * @return an Optional containing the User entity, or empty if not found.
     */

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Retrieves a list of all users in the system.
     *
     * @return a List of all User entities.
     */

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }



}