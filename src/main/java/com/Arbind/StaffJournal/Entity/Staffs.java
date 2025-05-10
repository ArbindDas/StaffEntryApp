package com.Arbind.StaffJournal.Entity;


import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
//TODO => CONTROLLER --> SERVICE --> REPOSITORY

@Data
@Document(collection = "staffEntries")
public class Staffs {
    @Id
    private String id;

    @NotBlank(message = "Username cannot be blank")
//    @Pattern(regexp = "^[a-zA-Z0-9_]{3,20}$",
//            message = "Username must be 3-20 characters long and can only contain letters, numbers, and underscores")
    private String username;

    @NotBlank(message = "Address cannot be blank")
    @Pattern(regexp = "^[a-zA-Z0-9\\s,.-]{5,100}$",
            message = "Address must be 5-100 characters long and can only contain letters, numbers, spaces, commas, dots, and hyphens")
    private String address;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^\\d{10}$",
            message = "Phone number must be exactly 10 digits")
    private String phoneNumber;

    @NotNull(message = "Salary cannot be null")
    private Double salary;

    private LocalDateTime date;


}
