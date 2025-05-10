package com.Arbind.StaffJournal.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Arbind.StaffJournal.Entity.Staffs;
import com.Arbind.StaffJournal.Entity.User;
import com.Arbind.StaffJournal.Repository.StaffRepository;


@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private UserService userService;

//    public List<Staffs> getAllStaffs() {
//        try {
//            return staffRepository.findAll();
//        } catch (RuntimeException e) {
//            throw new RuntimeException(e);
//        }
//    }


    public void saveStaff(Staffs staffs, String username) {
        try {
            Optional<User> userOptional = userService.findByUsername(username);
            if (userOptional.isEmpty()) {
                throw new IllegalArgumentException("User with username " + username + " does not exist");
            }

            User user = userOptional.get(); // Unwrap the optional
            staffs.setDate(LocalDateTime.now());
            Staffs savedStaff = staffRepository.save(staffs);
            user.getStaffsEntries().add(savedStaff);
            userService.createUser(user); // Save the updated user entity
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save staff entry: " + e.getMessage());    
        }
    }

    //  TODO METHOD OVERLOADING
    public void saveStaff(Staffs staffs) {
        try {
            staffRepository.save(staffs);

        } catch (RuntimeException e) {
            System.out.println(e);
            throw new RuntimeException("An error occured during saving the staff : ",e);
        }
    }

    public Optional<Staffs> getStaffById(String myid) {
        try {
            return staffRepository.findById(myid);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteStaffById(String myid, String username) {
        Optional<User> userOptional = userService.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.getStaffsEntries().removeIf(x -> x.getId().equals(myid));
            userService.createUser(user);
            staffRepository.deleteById(myid);
        } else {
            throw new RuntimeException("User not found with username: " + username);
        }
    }
}
