package app.carstore.model.mapper;


import app.carstore.model.dto.offer.CreateOrUpdateOfferDTO;
import app.carstore.model.dto.offer.OfferDetailDTO;
import app.carstore.model.entity.OfferEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface OfferMapper {

    @Mapping(source ="model" , target = "model.name")
    OfferEntity addOfferDtoToOfferEntity(CreateOrUpdateOfferDTO add);


    @Mapping(source = "model.id", target = "modelId")
    @Mapping(source = "model.name", target = "model")
    CreateOrUpdateOfferDTO offerEntityToCreateOrUpdateOfferDtoTo(OfferEntity offerEntity);




    @Mapping(source = "model.name", target = "model")
    @Mapping(source = "model.brand.name", target = "brand")
    @Mapping(source = "seller.firstName", target = "sellerFirstName")
    @Mapping(source = "seller.lastName", target = "sellerLastName")
    OfferDetailDTO offerEntityToOfferDetailDTO(OfferEntity offerEntity);
}
