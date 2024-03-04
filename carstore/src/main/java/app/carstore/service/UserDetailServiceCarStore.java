package app.carstore.service;

import app.carstore.model.entity.UserEntity;
import app.carstore.model.entity.UserRoleEntity;
import app.carstore.model.user.UserDetailsCarStore;
import app.carstore.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class UserDetailServiceCarStore implements UserDetailsService {


    private UserRepository userRepository;

    public UserDetailServiceCarStore(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.
                findByEmail(username).map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("User with email" + username + "not found"));

    }

    private UserDetails map(UserEntity entity) {

        return new UserDetailsCarStore(
                entity.getId(),
                entity.getPassword(),
                entity.getEmail(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getUserRoles()
                        .stream()
                        .map(this::mapRole).toList());

    }

    private GrantedAuthority mapRole(UserRoleEntity userRole) {
        return new SimpleGrantedAuthority("ROLE_" +
                userRole.getUserRole().name());
    }

}
