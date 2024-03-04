package app.carstore.util;

import app.carstore.model.entity.*;
import app.carstore.model.enums.CategoryEnum;
import app.carstore.model.enums.EngineEnum;
import app.carstore.model.enums.TransmissionEnum;
import app.carstore.model.enums.UserRoleEnum;
import app.carstore.repository.*;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;

@Component
public class DataTestUtils {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private OfferRepository offerRepository;
    private ModelRepository modelRepository;
    private BrandRepository brandRepository;

    public DataTestUtils(UserRepository userRepository,
                         UserRoleRepository userRoleRepository,
                         OfferRepository offerRepository,
                         ModelRepository modelRepository,
                         BrandRepository brandRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.offerRepository = offerRepository;
        this.modelRepository = modelRepository;
        this.brandRepository = brandRepository;
    }


    private void initRoles() {
        if (userRoleRepository.count() == 0) {
            UserRoleEntity admin = new UserRoleEntity().setUserRole(UserRoleEnum.ADMIN);
            UserRoleEntity user = new UserRoleEntity().setUserRole(UserRoleEnum.USER);

            userRoleRepository.save(admin);
            userRoleRepository.save(user);

        }
    }

    public UserEntity createTestAdmin(String email) {

        initRoles();

        var admin = new UserEntity().
                setEmail(email).
                setFirstName("Admin").
                setLastName("Kikirikov").
                setActive(true).
                setUserRoles(userRoleRepository.findAll());

        return userRepository.save(admin);
    }
    public UserEntity createTestUser(String email) {

        initRoles();

        var user = new UserEntity().
                setEmail(email).
                setFirstName("User").
                setLastName("Kikirikov").
                setActive(true).
                setUserRoles(userRoleRepository.
                        findAll().stream().
                        filter(r -> r.getUserRole() != UserRoleEnum.ADMIN).
                        toList());

        return userRepository.save(user);
    }

    public OfferEntity createTestOffer(UserEntity seller,
                                       ModelEntity model) {
        var offerEntity = new OfferEntity().
                setDescription("Test description").
                setEngine(EngineEnum.HYBRID).
                setImageUrl("http://img.com/img.png").
                setMileage(100000).
                setModel(model).
                setPrice(BigDecimal.TEN).
                setSeller(seller).
                setTransmission(TransmissionEnum.AUTOMATIC).
                setYear(2000);

        return offerRepository.save(offerEntity);
    }

    public BrandEntity createTestBrand() {
        var brandEntity = new BrandEntity().
                setName("Renault");

        return brandRepository.save(brandEntity);
    }

    public ModelEntity createTestModel(BrandEntity brandEntity) {
        ModelEntity model = new ModelEntity().
                setName("Talisman").
                setBrand(brandEntity).
                setCategory(CategoryEnum.CAR).
                setImageUrl("http://img.com/img.png").
                setStartYear(2020);

        return modelRepository.save(model);
    }
    public void cleanUpDatabase() {
        offerRepository.deleteAll();
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
        modelRepository.deleteAll();
        brandRepository.deleteAll();
    }
}