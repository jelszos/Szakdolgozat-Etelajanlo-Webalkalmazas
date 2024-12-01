package com.szakdologzat.repiceapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.szakdologzat.repiceapp.domain.FavoriteRelation} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FavoriteRelationDTO implements Serializable {

    private Long id;

    private UserDTO user;

    private RecipeDTO recipe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
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
        if (!(o instanceof FavoriteRelationDTO favoriteRelationDTO)) {
            return false;
        }

        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, favoriteRelationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FavoriteRelationDTO{" +
            "id=" + getId() +
            ", user=" + getUser() +
            ", recipe=" + getRecipe() +
            "}";
    }
}
