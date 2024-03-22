package app.carstore.web;

import app.carstore.model.dto.user.UserRegisterDTO;
import app.carstore.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserRegistrationController {

    private final UserService userService;
    private final LocaleResolver localeResolver;

    public UserRegistrationController(UserService userService, LocaleResolver localeResolver) {
        this.userService = userService;
        this.localeResolver = localeResolver;
    }

    @ModelAttribute("userRegisterModel")
    public UserRegisterDTO initUserModel() {
        return new UserRegisterDTO();
    }


    @GetMapping("/users/register")
    private String register() {
        return "auth-register";
    }


    @PostMapping("/users/register")
    public String register(@Valid UserRegisterDTO userRegisterModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           HttpServletRequest request) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("userRegisterModel", userRegisterModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterModel", bindingResult);

            return "redirect:/users/register";
        }

        userService.registerAndLogin(userRegisterModel,localeResolver.resolveLocale(request));


        return "redirect:/users/pending-verification";
    }

    @GetMapping("/users/pending-verification")
    public String pending(){
        return "pending-verification";
    }


    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        return userService.verify(code)? "successful-verification" : "failed-verification";
    }
}
