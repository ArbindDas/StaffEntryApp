package com.Arbind.StaffJournal.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("health")
@RestController
public class healthCheckController {

    public record SumResponse(int ans) {

    }

    @GetMapping
    public  String print(){
        return  "jai shree ram";
    }

    @GetMapping("/Add")
    public SumResponse add(@RequestParam int num1, @RequestParam int num2) {
        int ans = num1 + num2;
        return new SumResponse(ans);
    }


   
}
