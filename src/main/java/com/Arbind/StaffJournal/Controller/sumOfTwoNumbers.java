package com.Arbind.StaffJournal.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sum")
public class sumOfTwoNumbers
{
    public record SumResponse(int ans){}

    @GetMapping("/add")
    public SumResponse addNumber(@RequestParam int num1, @RequestParam int num2) {
        int ans = num1 + num2;
        return new SumResponse(ans);
    }

    public record PrintHello(String ans){}

    @GetMapping("/print")
    public PrintHello print(){
        return new PrintHello("my name is Arbindd das") ;
    }


//    @Data
//    public static class SumResponse{
//        private int number;
//        SumResponse(int number){
//            this.number = number;
//        }
//    }
//    @GetMapping("/add")
//    public SumResponse addNumber(@RequestParam int num1 , @RequestParam int num2){
//        int ans = num1 + num2;
//        SumResponse response = new SumResponse(ans);
//        response.setNumber(ans);
//        return response;
//    }
}


