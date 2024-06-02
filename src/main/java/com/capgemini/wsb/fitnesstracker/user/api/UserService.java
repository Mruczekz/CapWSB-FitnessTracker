package com.capgemini.wsb.fitnesstracker.user.api;

import java.util.List;
import java.util.Optional;

/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface UserService {

    User createUser(User user);

    List<User> findAllUsers();

    Optional<User> deleteUser(long id);

    List<User> findByEmailContaining(String query);

    List<User> findByAgeGreaterThan(long maxAge);

    User updateUser(Long userId, User user);

}
