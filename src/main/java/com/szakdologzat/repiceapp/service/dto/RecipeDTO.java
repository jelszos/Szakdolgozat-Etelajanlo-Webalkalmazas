package com.szakdologzat.repiceapp.service.dto;

import com.szakdologzat.repiceapp.domain.enumeration.FoodCategory;
import com.szakdologzat.repiceapp.domain.enumeration.FoodType;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.szakdologzat.repiceapp.domain.Recipe} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RecipeDTO implements Serializable {

    private Long id;

    private String title;

    private String description;

    private String ingredientNames;

    private FoodCategory foodCategory;

    private FoodType foodType;

    private String imageUrl;

    private ZonedDateTime createdDate;

    private boolean isFavorite;

    private Long favoriteCount;

    private Long requiredTimeSum;

    private Double totalRating;

    public Long getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Long favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public Long getRequiredTimeSum() {
        return requiredTimeSum;
    }

    public void setRequiredTimeSum(Long requiredTimeSum) {
        this.requiredTimeSum = requiredTimeSum;
    }

    private Set<InstructionDTO> instructions;

    private Set<RatingDTO> ratings;

    private UserDTO user;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Set<InstructionDTO> getInstructions() {
        return instructions;
    }

    public void setInstructions(Set<InstructionDTO> instructions) {
        this.instructions = instructions;
    }

    public Set<RatingDTO> getRatings() {
        return ratings;
    }

    public void setRatings(Set<RatingDTO> ratings) {
        this.ratings = ratings;
    }

    public Double getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(Double totalRating) {
        this.totalRating = totalRating;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredientNames() {
        return ingredientNames;
    }

    public void setIngredientNames(String ingredientNames) {
        this.ingredientNames = ingredientNames;
    }

    public FoodCategory getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(FoodCategory foodCategory) {
        this.foodCategory = foodCategory;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public RecipeDTO() {}

    public RecipeDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecipeDTO recipeDTO)) {
            return false;
        }

        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, recipeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecipeDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", ingredientNames='" + getIngredientNames() + "'" +
            ", foodCategory='" + getFoodCategory() + "'" +
            ", foodType='" + getFoodType() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
