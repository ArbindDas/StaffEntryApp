package com.Arbind.StaffJournal.Controller;


import com.Arbind.StaffJournal.Entity.User;
import com.Arbind.StaffJournal.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController
{

    @Autowired
    private UserService userService;

    public record PrintResponse(String print){}

    @GetMapping("/print")
    public PrintResponse printResponse(){
       return new PrintResponse("jai shree ram prabhu : ");
    }


    @PostMapping("/create_user")
    public ResponseEntity<User> createUser( @Valid @RequestBody  User user){
        try {
            userService.createNewUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (RuntimeException e) {
            System.out.println(e);
            throw new RuntimeException("An Error occured during creating the user : with "+ e.getMessage());
        }
    }
}
