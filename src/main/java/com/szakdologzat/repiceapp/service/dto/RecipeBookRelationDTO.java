package com.szakdologzat.repiceapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.szakdologzat.repiceapp.domain.RecipeBookRelation} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RecipeBookRelationDTO implements Serializable {

    private Long id;

    private RecipeDTO recipe;

    private RecipeBookDTO recipeBook;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RecipeDTO getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeDTO recipe) {
        this.recipe = recipe;
    }

    public RecipeBookDTO getRecipeBook() {
        return recipeBook;
    }

    public void setRecipeBook(RecipeBookDTO recipeBook) {
        this.recipeBook = recipeBook;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecipeBookRelationDTO recipeBookRelationDTO)) {
            return false;
        }

        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, recipeBookRelationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecipeBookRelationDTO{" +
            "id=" + getId() +
            ", recipe=" + getRecipe() +
            ", recipeBook=" + getRecipeBook() +
            "}";
    }
}
