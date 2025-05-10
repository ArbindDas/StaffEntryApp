package com.Arbind.StaffJournal.Controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Arbind.StaffJournal.Entity.Staffs;
import com.Arbind.StaffJournal.Entity.User;
import com.Arbind.StaffJournal.Service.StaffService;
import com.Arbind.StaffJournal.Service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<?> getAllstaffByusername() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Optional<User> user = userService.findByUsername(username);
            if (user.isPresent()) {
                List<Staffs> all = user.get().getStaffsEntries();
                if (all != null && !all.isEmpty()) {
                    return new ResponseEntity<>(all, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("No staff are found ", HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>("no user are present there ", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<?> createStaff(@Valid @RequestBody Staffs staffs) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            staffService.saveStaff(staffs, username);
            return new ResponseEntity<>(staffs, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid input: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Error occurred while saving staff: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("id/{myid}")
    public ResponseEntity<?> getStaffById(@Valid @PathVariable String myid) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Optional<User> user = userService.findByUsername(username);
            List<Staffs> collect = user.get().getStaffsEntries().stream().filter(x -> x.getId().equals(myid)).collect(Collectors.toList());
            if (!collect.isEmpty()) {
                Optional<Staffs> staffs = staffService.getStaffById(myid);
                if (staffs.isPresent()) {
                    return new ResponseEntity<>(staffs, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Staff not found with ID: " + myid, HttpStatus.BAD_REQUEST);
                }
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Error occurred while fetching staff by ID", e);
        }
        return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("id/{myid}")
    public ResponseEntity<?> deleteStaffById(@PathVariable String myid) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            staffService.deleteStaffById(myid, username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>("Error occurred while deleting staff: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("id/{myid}")
    public ResponseEntity<?> updateStaffById(@PathVariable String myid, @Valid @RequestBody Staffs newStaff
    ) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Optional<User> user = userService.findByUsername(username);
            List<Staffs> collect = user.get().getStaffsEntries().stream().filter(x -> x.getId().equals(myid)).collect(Collectors.toList());
            if (!collect.isEmpty()) {
                Optional<Staffs> staffs = staffService.getStaffById(myid);
                if (staffs.isPresent()) {
                    Staffs oldStaff = staffs.get();
                    oldStaff.setUsername(newStaff.getUsername() != null && !newStaff.getUsername().isEmpty() ? newStaff.getUsername() : oldStaff.getUsername());
                    oldStaff.setAddress(newStaff.getAddress() != null && !newStaff.getAddress().isEmpty() ? newStaff.getAddress() : oldStaff.getAddress());
                    oldStaff.setPhoneNumber(newStaff.getPhoneNumber() != null && !newStaff.getPhoneNumber().isEmpty() ? newStaff.getPhoneNumber() : oldStaff.getPhoneNumber());
                    oldStaff.setSalary(newStaff.getSalary() != null ? newStaff.getSalary() : oldStaff.getSalary());
                    staffService.saveStaff(oldStaff);
                    return new ResponseEntity<>(oldStaff, HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>("Staff not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating staff: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


    }
}
