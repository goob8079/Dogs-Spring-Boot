package com.justice.dogs.account.user;

import com.justice.dogs.account.email.VerificationToken;
import com.justice.dogs.account.email.VerificationTokenRepository;
import com.justice.dogs.account.user.UserRepository;
import com.justice.dogs.exceptions.UserAlreadyExistsException;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// this class checks to see if a user email already exists
@Service
@Transactional
public class UserService implements IUserService {
    
    @Autowired
    private UserRepository repo;

    @Autowired
    private VerificationTokenRepository tokenRepo;

    @Override
    public User registerNewUserAccount(UserDTO userDto) throws UserAlreadyExistsException {
        if (emailExists(userDto.getEmail())) {
            throw new UserAlreadyExistsException("An account with that email already exists!: "
                + userDto.getEmail());
        }

        User user = new User();
        user.setName(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setRoles(Arrays.asList("ROLE_USER"));

        return repo.save(user);
    }

    private boolean emailExists(String email) {
        return repo.findByEmail(email) != null;
    }

    @Override
    public User getUser(String verificationToken) {
        User user = tokenRepo.findByToken(verificationToken).getUser();
        return user;
    }
    
    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepo.findByToken(VerificationToken);
    }
    
    @Override
    public void saveRegisteredUser(User user) {
        repo.save(user);
    }
    
    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepo.save(myToken);
    }
}
