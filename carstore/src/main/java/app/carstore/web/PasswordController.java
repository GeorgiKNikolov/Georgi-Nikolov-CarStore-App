package app.carstore.web;

import app.carstore.model.dto.ForgottenPasswordDTO;
import app.carstore.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PasswordController {

    private final UserService userService;
    private final LocaleResolver localeResolver;

    public PasswordController(UserService userService,
                              LocaleResolver localeResolver) {
        this.userService = userService;
        this.localeResolver = localeResolver;
    }

    @GetMapping("/users/forgot")
    public String forgotPassword(){
        return "send-email";
    }

    @PostMapping("/users/forgot")
    public String forgotPasswordEmailConfirm(ForgottenPasswordDTO forgottenPasswordDTO,
                                             HttpServletRequest request){

        boolean emailIsPresent = userService.findEmailIsPresent(forgottenPasswordDTO.getEmail());
        if (!emailIsPresent){
            return "redirect:/users/forgot";
        }


        userService.findUserAndSendEmail(forgottenPasswordDTO.getEmail(),localeResolver.resolveLocale(request));


        return "redirect:/";
    }

    @GetMapping("/change")
    public String changePassword() {
        return "new-password";
    }

    @PostMapping("/change")
    public String setNewPassword(@Valid ForgottenPasswordDTO forgottenPasswordDTO,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("forgottenPasswordDTO", forgottenPasswordDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.forgottenPasswordDTO"
                    , bindingResult);

            return "redirect:/change";
        }

        userService.setUserNewPassword(forgottenPasswordDTO);

        return "redirect:/users/login";
    }



    @ModelAttribute
    public ForgottenPasswordDTO forgottenPasswordDTO() {
        return new ForgottenPasswordDTO();
    }

}
