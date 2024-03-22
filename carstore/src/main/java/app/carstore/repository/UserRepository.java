package app.carstore.repository;

import app.carstore.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
      Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByPassword(String givenPassword);

    UserEntity findByVerificationCode(String code);
}

