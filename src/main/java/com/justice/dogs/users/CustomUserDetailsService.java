package com.justice.dogs.users;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserObj> user = repo.findByUsername(username);
        if (user.isPresent()) {
            UserObj userObj = user.get();
            return User.builder()
                .username(userObj.getUsername())
                .password(userObj.getPassword())
                .authorities(Collections.emptyList())
                .build(); 
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
