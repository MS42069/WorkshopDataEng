package com.fhwedel.softwareprojekt.v1.service;

import com.fhwedel.softwareprojekt.v1.model.User;
import com.fhwedel.softwareprojekt.v1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/** Service class for managing users. */
@Service
public class UserService implements UserDetailsService {

    /** The repository for accessing user data. */
    @Autowired private UserRepository userRepository;

    /**
     * Load a user by their username.
     *
     * @param username The username of the user to load.
     * @return The user object.
     * @throws UsernameNotFoundException If no user with the given username is found.
     */
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(
                        () ->
                                new UsernameNotFoundException(
                                        "User Not Found with username: " + username));
    }
}
