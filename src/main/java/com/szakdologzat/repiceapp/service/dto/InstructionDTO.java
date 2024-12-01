package com.szakdologzat.repiceapp.service.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.szakdologzat.repiceapp.domain.Instruction} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InstructionDTO implements Serializable {

    private Long id;

    private String title;

    private Integer requiredTime;

    private String description;

    private RecipeDTO recipe;

    private Set<IngredientDTO> ingredients;

    public Set<IngredientDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
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

    public Integer getRequiredTime() {
        return requiredTime;
    }

    public void setRequiredTime(Integer requiredTime) {
        this.requiredTime = requiredTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RecipeDTO getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeDTO recipe) {
        this.recipe = recipe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InstructionDTO instructionDTO)) {
            return false;
        }

        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, instructionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InstructionDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", requiredTime=" + getRequiredTime() +
            ", description='" + getDescription() + "'" +
            ", recipe=" + getRecipe() +
            "}";
    }
}
