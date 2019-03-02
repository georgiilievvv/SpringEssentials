package exodia.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoggedInController {

    @GetMapping("/home")
    public ModelAndView home(ModelAndView modelAndView){
        modelAndView.setViewName("home");

        return modelAndView;
    }

    @GetMapping("/schedule")
    public ModelAndView schedule(ModelAndView modelAndView){
        modelAndView.setViewName("schedule");

        return modelAndView;
    }

    @GetMapping("/print")
    public ModelAndView print(ModelAndView modelAndView){
        modelAndView.setViewName("print");

        return modelAndView;
    }

    @GetMapping("/details")
    public ModelAndView details(ModelAndView modelAndView){
        modelAndView.setViewName("details");

        return modelAndView;
    }
}
