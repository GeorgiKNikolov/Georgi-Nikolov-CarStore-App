package app.carstore.configuration;

import app.carstore.model.user.CustomerOAuth2User;
import app.carstore.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {


    @Autowired
    private UserService userService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        CustomerOAuth2User customOAuth2User= (CustomerOAuth2User)authentication.getPrincipal();
        String email = customOAuth2User.getEmail();

        userService.createUserIfNotExist(email);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
