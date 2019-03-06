package exodia.web;

import exodia.domain.models.binding.UserLoginBindingModel;
import exodia.domain.models.binding.UserRegisterBindingModel;
import exodia.domain.models.service.UserServiceModel;
import exodia.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Validator;

@Controller
public class GuestController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final Validator validator;

    @Autowired
    public GuestController(UserService userService, ModelMapper modelMapper, Validator validator) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @GetMapping("")
    public ModelAndView index(ModelAndView modelAndView){
        modelAndView.setViewName("index");

        return modelAndView;
    }

    @GetMapping("login")
    public ModelAndView login(ModelAndView modelAndView){
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping("login")
    public ModelAndView loginConfirm(@ModelAttribute UserLoginBindingModel model, ModelAndView modelAndView, HttpSession session){
        UserServiceModel serviceModel = this.userService.logInUser(this.modelMapper.map(model, UserServiceModel.class));

        if (this.loginModelIsValid(model) && serviceModel != null){

            session.setAttribute("userId", serviceModel.getId());
            modelAndView.setViewName("redirect:/home");

        }else {
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }

    private boolean loginModelIsValid(UserLoginBindingModel model) {
        return this.validator.validate(model).size() == 0;
    }

    @GetMapping("register")
    public ModelAndView register(ModelAndView modelAndView){

        modelAndView.setViewName("register");

        return modelAndView;
    }

    @PostMapping("register")
    public ModelAndView registerConfirm(@ModelAttribute UserRegisterBindingModel model, ModelAndView modelAndView){

        if (registerModelIsValid(model) && this.userService.registerUser(this.modelMapper.map(model, UserServiceModel.class))){
            modelAndView.setViewName("/login");
        }else {
            modelAndView.setViewName("redirect:/register");
        }

        return modelAndView;
    }

    private boolean registerModelIsValid(UserRegisterBindingModel model) {
        return this.validator.validate(model).size() == 0 &&
                model.getPassword().equals(model.getConfirmPassword());
    }
}
