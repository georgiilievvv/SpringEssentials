package exodia.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoggedInController {

    @GetMapping("/home")
    public String home(){

        return "home";
    }

    @GetMapping("/schedule")
    public String schedule(){

        return "schedule";
    }

    @GetMapping("/print")
    public String print(){

        return "print";
    }

    @GetMapping("/details")
    public String details(){

        return "details";
    }
}
