package app.carstore.model.dto;

import app.carstore.model.enums.CategoryEnum;


public class ModelDTO {

    private long id;
    private String name;

    private CategoryEnum category;

    private String imageUrl;

    private int startYear;

    private Long endYear;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ModelDTO setName(String name) {
        this.name = name;
        return this;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public ModelDTO setCategory(CategoryEnum category) {
        this.category = category;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ModelDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public int getStartYear() {
        return startYear;
    }

    public ModelDTO setStartYear(int startYear) {
        this.startYear = startYear;
        return this;
    }

    public Long getEndYear() {
        return endYear;
    }

    public ModelDTO setEndYear(Long endYear) {
        this.endYear = endYear;
        return this;
    }
}
