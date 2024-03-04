package app.carstore.web;


import app.carstore.model.entity.ModelEntity;
import app.carstore.model.entity.OfferEntity;
import app.carstore.model.entity.UserEntity;
import app.carstore.util.DataTestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest
@AutoConfigureMockMvc
class OfferControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private DataTestUtils dataTestUtils;

    private UserEntity testUser, testAdmin;

    private OfferEntity testOffer, testOfferAdmin;

    @BeforeEach
    void setUp(){
        testUser = dataTestUtils.createTestUser("user@kiki.com");
        testAdmin = dataTestUtils.createTestAdmin("admin@kiki.com");

        ModelEntity testModel = dataTestUtils.createTestModel(dataTestUtils.createTestBrand());

        testOffer = dataTestUtils.createTestOffer(testUser, testModel);
        testOfferAdmin = dataTestUtils.createTestOffer(testAdmin, testModel);
    }

    @AfterEach
    void tearDown(){
        dataTestUtils.cleanUpDatabase();
    }

         @Test
         @WithMockUser(username = "admin@kiki.com", roles = {"ADMIN", "USER"})
         void testDeleteByAdmin() throws Exception {

        mvc.perform(delete("/offers/{id}", testOfferAdmin.getId())
                .with(csrf()))
                .andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/offers/all"));

        }

        void testDeleteByOwner(){

        }

        void testDeleteByNotOwned(){
         }
}
