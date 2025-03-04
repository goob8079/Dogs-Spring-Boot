package com.justice.dogs.account;

import com.justice.dogs.exceptions.UserAlreadyExistsException;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// this class checks to see if a user email already exists
@Service
@Transactional
public class UserService implements IUserService {
    @Autowired
    private UserRepository repo;

    @Override
    public User registerNewUserAccount(UserDTO userDto) throws UserAlreadyExistsException {
        if (emailExists(userDto.getEmail())) {
            throw new UserAlreadyExistsException("An account with that email already exists!: "
                + userDto.getEmail());
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setRoles(Arrays.asList("ROLE_USER"));
    }

    private boolean emailExists(String email) {
        return repo.findByEmail(email) != null;
    }
}
