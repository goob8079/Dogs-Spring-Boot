package com.justice.dogs.account;

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
public class MyUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository repo;

    public UserDetails loadUserbyUsername(String email) throws UsernameNotFoundException {
        User user = repo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("A user with this username not found: " + email);
        }

        boolean enabled = true;
        boolean accNotExpired = true;
        boolean credentialsNotExpired = true;
        boolean accNotLocked = true;

        return new User(user.getEmail(), user.getPassword(), 
            enabled, accNotExpired, credentialsNotExpired, accNotLocked, getAuthorities(user.getRoles()));
    }

    private static List<GrantedAuthority> getAuthorities (List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (String role: roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        
        return authorities;
    }
}
