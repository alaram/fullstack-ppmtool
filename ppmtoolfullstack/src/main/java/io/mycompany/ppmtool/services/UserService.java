package io.mycompany.ppmtool.services;

import io.mycompany.ppmtool.domain.User;
import io.mycompany.ppmtool.exceptions.UsernameAlreadyExistsException;
import io.mycompany.ppmtool.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Password encoder, in order to not store password
     * in clear text, i.e. human readable
     */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     *
     * @param newUser
     * @return
     */
    public User saveUser (User newUser) {
        try {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

            // Validate that the username is unique, so this will fire the custom Exception
            newUser.setUsername(newUser.getUsername());

            // This allows to present the confirmation password as empty in the JSON response
            // object. The JsonIgnore annotation does not work for this field in the Entity
            // class, so this is the work around of the problem.
            newUser.setConfirmPassword("");

            return userRepository.save(newUser);
        } catch (Exception e) {
            throw new UsernameAlreadyExistsException("Username '" + newUser.getUsername() + "' already exists!");
        }

    }


}