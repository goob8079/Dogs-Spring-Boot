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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetails = repo.findByEmail(username);

        return userDetails.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public String addUser(UserInfo userInfo) {
        // automatically set the role of any new account to USER
        userInfo.setRoles("ROLE_USER");
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repo.save(userInfo);
        return "User has been successfully added";
    }
}
