package com.justice.dogs.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// a data transfer object (DTO) for encapsulating a user's login credentials (username and password).
// recieves and processes login requests securely.
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    private String username, password;
}
