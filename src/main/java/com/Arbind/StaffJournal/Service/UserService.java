package com.Arbind.StaffJournal.Service;

import com.Arbind.StaffJournal.Entity.User;
import com.Arbind.StaffJournal.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    private static final PasswordEncoder passwordencoder = new BCryptPasswordEncoder();


    public List<User> getAllUser() {
        try {
            return userRepository.findAll();
        } catch (RuntimeException e) {
            System.out.println(e);
            throw new RuntimeException("Ann error occured while getting the users ", e);

        }
    }

    public void createAdmin(User user){
       try {
           user.setPassword(passwordencoder.encode(user.getPassword()));
           user.setRoles(Arrays.asList("ADMIN" , "USER"));
           userRepository.save(user);
       } catch (RuntimeException e) {
           throw new RuntimeException(e);
       }
    }


    // Save or update a user
    public void createUser(User user) {
        try {
            userRepository.save(user);
        } catch (RuntimeException e) {
            System.out.println(e);
            throw new RuntimeException("Ann error occured while creating  the users ", e);
        }
    }

    // Save or update a user
    public void createNewUser(User user) {
        try {
            user.setPassword(passwordencoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }



    public Optional<User> getUsesrById(ObjectId id) {
        try {
            Optional<User> user = userRepository.findById(id);
            return user;
        } catch (RuntimeException e) {
            System.out.println(e);
            throw new RuntimeException("An error occured whike geting user by Id", e);
        }

    }

    public Optional<User> deleteUserById(ObjectId id) {
        try {
            Optional<User> user = userRepository.findById(id);
            user.ifPresent(U -> userRepository.delete(U));
            return user;
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to delete user with ID: " + id, e);
        }
    }


    public Optional<User> findByUsername(String username) {
        try {
            return Optional.ofNullable(userRepository.findByUsername(username));
        } catch (RuntimeException e) {
            System.out.println(e);
            throw new RuntimeException("An error occured while finding the username by username ", e);
        }
    }


    public void deleteByUserName(String username){
        try {
            userRepository.deleteByUsername(username);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


}
