package com.justice.dogs.account.email;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justice.dogs.account.user.User;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}