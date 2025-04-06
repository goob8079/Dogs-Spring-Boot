package com.justice.dogs.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByEmail(String email);
}
