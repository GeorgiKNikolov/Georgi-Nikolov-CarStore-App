package app.carstore.repository;

import app.carstore.model.dto.offer.SearchOfferDTO;
import app.carstore.model.entity.OfferEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class OfferSpecification implements Specification<OfferEntity> {


    private final SearchOfferDTO searchOfferDTO;


    public OfferSpecification(SearchOfferDTO searchOfferDTO) {
        this.searchOfferDTO = searchOfferDTO;
    }

    @Override
    public Predicate toPredicate(Root<OfferEntity> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder cb) {

        Predicate p = cb.conjunction();

        if (searchOfferDTO.getModel() != null && !searchOfferDTO.getModel().isEmpty()) {

            p = cb.and(cb.equal(root.join("model").get("name"), searchOfferDTO.getModel()));

        }

        if (searchOfferDTO.getMinPrice() != null) {

            p = cb.and(cb.greaterThanOrEqualTo(root.get("price"), searchOfferDTO.getMinPrice()));

        }

        if (searchOfferDTO.getMaxPrice() != null) {

           p = cb.and(cb.lessThanOrEqualTo(root.get("price"), searchOfferDTO.getMaxPrice()));

        }


        return p;

    }
}
