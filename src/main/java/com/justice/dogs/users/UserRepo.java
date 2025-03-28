package com.justice.dogs.users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserObj, Long> {
    Optional<UserObj> findByUsername(String username);
}
