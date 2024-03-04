package app.carstore.service;

import app.carstore.model.dto.BrandDTO;
import app.carstore.model.dto.ModelDTO;
import app.carstore.model.entity.BrandEntity;
import app.carstore.model.entity.ModelEntity;
import app.carstore.model.mapper.ModelMapper;
import app.carstore.repository.BrandRepository;
import app.carstore.repository.ModelRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final ModelRepository modelRepository;
    private ModelMapper modelMapper;

    public BrandService(BrandRepository brandRepository,
                        ModelRepository modelRepository,
                        ModelMapper modelMapper) {
        this.brandRepository = brandRepository;
        this.modelRepository = modelRepository;
        this.modelMapper = modelMapper;
    }

    public List<ModelDTO> getAllVehicles(){
        return modelRepository.findAll().stream().map(modelMapper::modelEntityToModelDTO).collect(Collectors.toList());
    }


    public List<BrandDTO> getAllBrand() {
        return brandRepository.
                findAll().
                stream().
                map(this::mapBrand).collect(Collectors.toList());
    }


    private BrandDTO mapBrand(BrandEntity brandEntity) {
        List<ModelDTO> models = brandEntity.
                getModels().
                stream().
                map(this::mapModel).collect(Collectors.toList());


        return new BrandDTO().setModels(models).setName(brandEntity.getName());

    }

    private ModelDTO mapModel(ModelEntity modelEntity) {

        ModelDTO model = new ModelDTO();
        model.setId(modelEntity.getId());
        model.setName(modelEntity.getName());

        return model;

    }
}