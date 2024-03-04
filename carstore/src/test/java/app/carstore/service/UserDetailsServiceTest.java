package app.carstore.service;


import app.carstore.model.entity.UserEntity;
import app.carstore.model.entity.UserRoleEntity;
import app.carstore.model.enums.UserRoleEnum;
import app.carstore.model.user.UserDetailsCarStore;
import app.carstore.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {


    @Mock
    private UserRepository mockRepository;


    private UserDetailServiceCarStore test;


    @BeforeEach
    void setup() {
        test = new UserDetailServiceCarStore(
                mockRepository
        );


    }


    @Test
    void testLoadingUserByUsernameIfExist() {
        UserEntity testUserEntity =
                new UserEntity().
                        setEmail("test@test.com").
                        setPassword("alabala").
                        setFirstName("Kiki").
                        setLastName("Riki").
                        setActive(true).
                        setImageUrl("http://img.bg/img").
                        setUserRoles(
                                List.of(
                                        new UserRoleEntity().setUserRole(UserRoleEnum.ADMIN),
                                        new UserRoleEntity().setUserRole(UserRoleEnum.USER)));


        when(mockRepository.findByEmail(testUserEntity.getEmail())).
                thenReturn(Optional.of(testUserEntity));


        UserDetailsCarStore userDetails = (UserDetailsCarStore)
                test.loadUserByUsername(testUserEntity.getEmail());


        Assertions.assertEquals(testUserEntity.getEmail(),
                userDetails.getUsername());

        Assertions.assertEquals(testUserEntity.getFirstName(), userDetails.getFirstName());
        Assertions.assertEquals(testUserEntity.getLastName(), userDetails.getLastName());
        Assertions.assertEquals(testUserEntity.getPassword(), userDetails.getPassword());
        Assertions.assertEquals(testUserEntity.getFirstName() + " " + testUserEntity.getLastName(), userDetails.getFullName());

        var authorities = userDetails.getAuthorities();

        Assertions.assertEquals(2, userDetails.getAuthorities().size());

        var iteratorAuth = authorities.iterator();

        Assertions.assertEquals("ROLE_" + UserRoleEnum.ADMIN.name(),
                iteratorAuth.next().getAuthority());
        Assertions.assertEquals("ROLE_" + UserRoleEnum.USER.name(),
                iteratorAuth.next().getAuthority());
    }


    @Test
    void testLoadingUserByUsernameIfNotExist() {


        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> test.loadUserByUsername("userNotEx@example.com"));

    }

}
