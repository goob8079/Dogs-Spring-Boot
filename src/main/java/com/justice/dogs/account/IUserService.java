package com.justice.dogs.account;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

public interface IUserService {
    User reguisterNewUserAccount(UserDTO userDto);
}
