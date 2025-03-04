package com.justice.dogs.account;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    public String findByEmail(String email);
}
