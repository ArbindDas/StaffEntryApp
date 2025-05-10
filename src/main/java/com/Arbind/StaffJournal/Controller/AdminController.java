package com.Arbind.StaffJournal.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Arbind.StaffJournal.Entity.User;
import com.Arbind.StaffJournal.Service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired

    private UserService userService;

    @GetMapping("/get-all-users")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> all = userService.getAllUser();
            if (all != null && !all.isEmpty()) {
                return new ResponseEntity<>(all, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (RuntimeException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/create-new-admin")
    public ResponseEntity<?>createAdmin(@Valid @RequestBody  User user){
        try {
            userService.createAdmin(user);
            return new ResponseEntity<>(user ,HttpStatus.CREATED);
        } catch (RuntimeException e) {
            System.out.println(e);
            throw new RuntimeException("An error occcured during creating a admin: "+e.getMessage());
        }
    }

}
