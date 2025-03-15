package com.justice.dogs.account.user;

import com.justice.dogs.account.email.VerificationToken;
import com.justice.dogs.account.user.User ;
import com.justice.dogs.exceptions.UserAlreadyExistsException;

public interface IUserService {
    public User registerNewUserAccount(UserDTO userDto) throws UserAlreadyExistsException;
    public User getUser(String verificationToken);

    public VerificationToken getVerificationToken(String verificationToken);
    public void createVerificationToken(User user, String token);
    
    public void saveRegisteredUser(User user);
}
