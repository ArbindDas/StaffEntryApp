package com.Arbind.StaffJournal.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Arbind.StaffJournal.Entity.User;
import com.Arbind.StaffJournal.Service.UserService;

import jakarta.validation.Valid;

@RequestMapping("/user")
@RestController
@Validated
public class UserController {

    @Autowired
    private UserService userService;


    @DeleteMapping("/delete_user")
    public ResponseEntity<?> deleteUserbyId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userService.deleteByUserName(authentication.getName());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    @PutMapping()
    public ResponseEntity<?> updateUserById(@Valid @RequestBody User user) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Optional<User> userinDB = userService.findByUsername(username);
            if (userinDB.isPresent()) {
                User existingUser = userinDB.get();
                existingUser.setUsername(user.getUsername() != null && !user.getUsername().isEmpty() ? user.getUsername() : existingUser.getUsername());
                existingUser.setPassword(user.getPassword() != null && !user.getPassword().isEmpty() ? user.getPassword() : existingUser.getPassword());
                userService.createNewUser(existingUser); // Save the updated user
                return new ResponseEntity<>(existingUser, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("The username is not found", HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Error updating user: " + e.getMessage(), e);
        }
    }

}
