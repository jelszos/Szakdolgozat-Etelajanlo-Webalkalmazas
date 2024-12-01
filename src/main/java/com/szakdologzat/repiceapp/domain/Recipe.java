package com.szakdologzat.repiceapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.szakdologzat.repiceapp.domain.enumeration.FoodCategory;
import com.szakdologzat.repiceapp.domain.enumeration.FoodType;
import com.szakdologzat.repiceapp.service.UserService;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A Recipe.
 */
@Entity
@Table(name = "recipe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Recipe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "ingredient_names")
    private String ingredientNames;

    @Enumerated(EnumType.STRING)
    @Column(name = "food_category")
    private FoodCategory foodCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "food_type")
    private FoodType foodType;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created_date")
    @CreationTimestamp
    private ZonedDateTime createdDate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "recipe", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = { "ingredients", "recipe" }, allowSetters = true)
    private Set<Instruction> instructions = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "recipe", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = { "user", "recipe" }, allowSetters = true)
    private Set<Rating> ratings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "recipe", "recipeBook" }, allowSetters = true)
    private Set<RecipeBookRelation> recipeBookRelations = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "recipe" }, allowSetters = true)
    private Set<FavoriteRelation> favoriteRelations = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "recipes" })
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Recipe() {}

    public Recipe(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Recipe(Long id, String title, FoodCategory foodCategory) {
        this.id = id;
        this.title = title;
        this.foodCategory = foodCategory;
    }

    public Long getId() {
        return this.id;
    }

    public Recipe id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Recipe title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Recipe description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredientNames() {
        return this.ingredientNames;
    }

    public Recipe ingredientNames(String ingredientNames) {
        this.setIngredientNames(ingredientNames);
        return this;
    }

    public void setIngredientNames(String ingredientNames) {
        this.ingredientNames = ingredientNames;
    }

    public FoodCategory getFoodCategory() {
        return this.foodCategory;
    }

    public Recipe foodCategory(FoodCategory foodCategory) {
        this.setFoodCategory(foodCategory);
        return this;
    }

    public void setFoodCategory(FoodCategory foodCategory) {
        this.foodCategory = foodCategory;
    }

    public FoodType getFoodType() {
        return this.foodType;
    }

    public Recipe foodType(FoodType foodType) {
        this.setFoodType(foodType);
        return this;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public Recipe imageUrl(String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public Recipe createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Instruction> getInstructions() {
        return this.instructions;
    }

    public void setInstructions(Set<Instruction> instructions) {
        if (this.instructions != null) {
            this.instructions.forEach(i -> i.setRecipe(null));
        }
        if (instructions != null) {
            instructions.forEach(i -> i.setRecipe(this));
        }
        this.instructions = instructions;
    }

    public Recipe instructions(Set<Instruction> instructions) {
        this.setInstructions(instructions);
        return this;
    }

    public Recipe addInstruction(Instruction instruction) {
        this.instructions.add(instruction);
        instruction.setRecipe(this);
        return this;
    }

    public Recipe removeInstruction(Instruction instruction) {
        this.instructions.remove(instruction);
        instruction.setRecipe(null);
        return this;
    }

    public Set<Rating> getRatings() {
        return this.ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        if (this.ratings != null) {
            this.ratings.forEach(i -> i.setRecipe(null));
        }
        if (ratings != null) {
            ratings.forEach(i -> i.setRecipe(this));
        }
        this.ratings = ratings;
    }

    public Recipe ratings(Set<Rating> ratings) {
        this.setRatings(ratings);
        return this;
    }

    public Recipe addRating(Rating rating) {
        this.ratings.add(rating);
        rating.setRecipe(this);
        return this;
    }

    public Recipe removeRating(Rating rating) {
        this.ratings.remove(rating);
        rating.setRecipe(null);
        return this;
    }

    public Set<RecipeBookRelation> getRecipeBookRelations() {
        return this.recipeBookRelations;
    }

    public void setRecipeBookRelations(Set<RecipeBookRelation> recipeBookRelations) {
        if (this.recipeBookRelations != null) {
            this.recipeBookRelations.forEach(i -> i.setRecipe(null));
        }
        if (recipeBookRelations != null) {
            recipeBookRelations.forEach(i -> i.setRecipe(this));
        }
        this.recipeBookRelations = recipeBookRelations;
    }

    public Recipe recipeBookRelations(Set<RecipeBookRelation> recipeBookRelations) {
        this.setRecipeBookRelations(recipeBookRelations);
        return this;
    }

    public Recipe addRecipeBookRelation(RecipeBookRelation recipeBookRelation) {
        this.recipeBookRelations.add(recipeBookRelation);
        recipeBookRelation.setRecipe(this);
        return this;
    }

    public Recipe removeRecipeBookRelation(RecipeBookRelation recipeBookRelation) {
        this.recipeBookRelations.remove(recipeBookRelation);
        recipeBookRelation.setRecipe(null);
        return this;
    }

    public Set<FavoriteRelation> getFavoriteRelations() {
        return this.favoriteRelations;
    }

    public void setFavoriteRelations(Set<FavoriteRelation> favoriteRelations) {
        if (this.favoriteRelations != null) {
            this.favoriteRelations.forEach(i -> i.setRecipe(null));
        }
        if (favoriteRelations != null) {
            favoriteRelations.forEach(i -> i.setRecipe(this));
        }
        this.favoriteRelations = favoriteRelations;
    }

    public Recipe favoriteRelations(Set<FavoriteRelation> favoriteRelations) {
        this.setFavoriteRelations(favoriteRelations);
        return this;
    }

    public Recipe addFavoriteRelation(FavoriteRelation favoriteRelation) {
        this.favoriteRelations.add(favoriteRelation);
        favoriteRelation.setRecipe(this);
        return this;
    }

    public Recipe removeFavoriteRelation(FavoriteRelation favoriteRelation) {
        this.favoriteRelations.remove(favoriteRelation);
        favoriteRelation.setRecipe(null);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Recipe user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recipe)) {
            return false;
        }
        return getId() != null && getId().equals(((Recipe) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Recipe{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", ingredientNames='" + getIngredientNames() + "'" +
            ", foodCategory='" + getFoodCategory() + "'" +
            ", foodType='" + getFoodType() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
