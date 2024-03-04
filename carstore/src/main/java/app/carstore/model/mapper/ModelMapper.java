package app.carstore.model.mapper;


import app.carstore.model.dto.ModelDTO;
import app.carstore.model.entity.ModelEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModelMapper {


    ModelDTO modelEntityToModelDTO(ModelEntity modelEntity);
}
