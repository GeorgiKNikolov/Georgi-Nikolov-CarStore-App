package app.carstore.model.dto.offer;


import app.carstore.model.enums.EngineEnum;
import app.carstore.model.enums.TransmissionEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


import java.math.BigDecimal;


public class CreateOrUpdateOfferDTO {

    @NotNull
    @Min(1)
    private Long modelId;

    private Long id;

    @Positive
    private BigDecimal price;

    @NotNull
    private EngineEnum engine;

    @Positive
    private int mileage;

    @Min(1900)
    private int year;

    @NotEmpty
    private String description;

    @NotNull
    private TransmissionEnum transmission;

    @NotEmpty
    private String imageUrl;

    private String model;

    public String getModel() {
        return model;
    }

    public CreateOrUpdateOfferDTO setModel(String model) {
        this.model = model;
        return this;
    }

    public Long getId() {
        return id;
    }

    public CreateOrUpdateOfferDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public TransmissionEnum getTransmission() {
        return transmission;
    }

    public void setTransmission(TransmissionEnum transmission) {
        this.transmission = transmission;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public EngineEnum getEngine() {
        return engine;
    }

    public void setEngine(EngineEnum engine) {
        this.engine = engine;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CreateOrUpdateOfferDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

}
