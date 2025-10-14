package com.justice.dogs.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.justice.dogs.user.UserInfo;
import com.justice.dogs.user.UserRepo;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private PasswordEncoder encoder;

    // automatically called when a user tries to log in or when validating a JWT
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // searches the database for a user by email
        Optional<UserInfo> userDetails = repo.findByEmail(username);

        // if the user if found, it wraps a UserInfo object into a UserInfoDetails object
        return userDetails.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public String addUser(UserInfo userInfo) {
        // automatically set the role of any new account to USER
        userInfo.setRoles("ROLE_USER");
        // encode the password so its not stored in plain text
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repo.save(userInfo);
        return "User has been successfully added";
    }
}
