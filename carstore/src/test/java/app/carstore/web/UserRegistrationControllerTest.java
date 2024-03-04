package app.carstore.web;

import app.carstore.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@AutoConfigureMockMvc
@SpringBootTest
public class UserRegistrationControllerTest {


    @Autowired
    public MockMvc mvc;

    @MockBean
    public EmailService mockEmailService;



    @Test
    void testUserRegistrationControllerPageView() throws Exception {
        mvc.
                perform(get("/users/register")).
                andExpect(status().isOk()).
                andExpect(view().name("auth-register"));

    }

    @Test
    void testUserRegistration() throws Exception {
        mvc.perform(post("/users/register").
                param("email", "kiki@riki.com").
                param("firstName","Kiki").
                param("lastName","Riki").
                param("password","123456").
                param("confirmPassword","123456").
                        with(csrf())).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/"));

    }
}
