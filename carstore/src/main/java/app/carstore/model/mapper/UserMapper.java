package app.carstore.model.mapper;

import app.carstore.model.dto.user.UserRegisterDTO;
import app.carstore.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "active", constant = "true")
    UserEntity userDtoUserEntity(UserRegisterDTO user);
}
