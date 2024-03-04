package app.carstore.service;

import app.carstore.model.dto.offer.CreateOrUpdateOfferDTO;
import app.carstore.model.dto.offer.OfferDetailDTO;
import app.carstore.model.dto.offer.SearchOfferDTO;
import app.carstore.model.entity.ModelEntity;
import app.carstore.model.entity.OfferEntity;
import app.carstore.model.entity.UserEntity;
import app.carstore.model.enums.UserRoleEnum;
import app.carstore.model.mapper.OfferMapper;
import app.carstore.repository.ModelRepository;
import app.carstore.repository.OfferRepository;
import app.carstore.repository.OfferSpecification;
import app.carstore.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService {


    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;
    private final UserRepository userRepository;
    private final ModelRepository modelRepository;

    public OfferService(OfferRepository offerRepository,
                        OfferMapper offerMapper,
                        UserRepository userRepository,
                        ModelRepository modelRepository) {

        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
        this.userRepository = userRepository;
        this.modelRepository = modelRepository;
    }


    public Page<OfferDetailDTO> getAllOffers(Pageable pageable) {
        return offerRepository.findAll(pageable).
                map(offerMapper::offerEntityToOfferDetailDTO);
    }

    public void deleteOfferById(Long offerId) {
        offerRepository.deleteById(offerId);
    }

    public boolean isOwner(String username, Long id) {

        boolean isOwner = offerRepository
                .findById(id)
                .filter(o -> o.getSeller().getEmail().equals(username)).isPresent();

        if (isOwner) {
            return true;
        }

        return userRepository.findByEmail(username).filter(this::isAdmin).isPresent();

    }

    private boolean isAdmin(UserEntity user) {
        return user.getUserRoles().stream().anyMatch(r -> r.getUserRole() == UserRoleEnum.ADMIN);
    }


    public void addOffer(CreateOrUpdateOfferDTO addOfferDTO, UserDetails userDetails) {

        UserEntity seller = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();

        OfferEntity newOffer = offerMapper.addOfferDtoToOfferEntity(addOfferDTO);

        ModelEntity model = modelRepository.findById(addOfferDTO.getModelId()).orElseThrow();

        newOffer.setModel(model);
        newOffer.setSeller(seller);

        offerRepository.save(newOffer);
    }

    public Optional<OfferDetailDTO> findOfferById(Long offerId) {
        return offerRepository
                .findById(offerId).
                map(offerMapper::offerEntityToOfferDetailDTO);
    }

    public List<OfferDetailDTO> searchOffer(SearchOfferDTO searchOfferDTO) {
        return this.offerRepository.findAll(new OfferSpecification(searchOfferDTO))
                .stream().map(offerMapper::offerEntityToOfferDetailDTO).toList();
    }

    public Optional<CreateOrUpdateOfferDTO> getEditOffer(Long offerId) {
        return offerRepository
                .findById(offerId)
                .map(offerMapper::offerEntityToCreateOrUpdateOfferDtoTo);
    }


    public void updateOffer(CreateOrUpdateOfferDTO updateOffer, Long id) {

        OfferEntity offer = offerRepository.findById(id).orElseThrow();


        offer.setPrice(updateOffer.getPrice());
        offer.setEngine(updateOffer.getEngine());
        offer.setTransmission(updateOffer.getTransmission());
        offer.setYear(updateOffer.getYear());
        offer.setMileage(updateOffer.getMileage());
        offer.setDescription(updateOffer.getDescription());
        offer.setImageUrl(updateOffer.getImageUrl());


        offerRepository.save(offer);

    }

}
