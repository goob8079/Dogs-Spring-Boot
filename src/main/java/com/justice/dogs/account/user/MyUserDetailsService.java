package com.justice.dogs.account.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    public MyUserDetailsService() {}

    public UserDetails loadUserbyUsername(String email) throws UsernameNotFoundException {
        // account checks
        boolean enabled = true;
        boolean accNotExpired = true;
        boolean credentialsNotExpired = true;
        boolean accNotLocked = true;

        try {
            User user = repo.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException("No user with that username found" + email);
            }

            return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword().toLowerCase(), user.isEnabled(),
                accNotExpired, credentialsNotExpired, accNotLocked, getAuthorities(user.getRole()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }  
    }

    private static List<GrantedAuthority> getAuthorities (List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (String role: roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        
        return authorities;
    }
}
