package com.szakdologzat.repiceapp.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.szakdologzat.repiceapp.domain.RecipeBook} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RecipeBookDTO implements Serializable {

    private Long id;

    private String title;

    private String theme;

    private String description;

    private String tags;

    private ZonedDateTime createdDate;

    private UserDTO user;

    private boolean isPrivate;

    private boolean isRecipeInList;

    private List<String> recipeImages;

    public List<String> getRecipeImages() {
        return recipeImages;
    }

    public void setRecipeImages(List<String> recipeImages) {
        this.recipeImages = recipeImages;
    }

    public boolean getIsRecipeInList() {
        return isRecipeInList;
    }

    public boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public void setIsRecipeInList(boolean recipeInList) {
        isRecipeInList = recipeInList;
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

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecipeBookDTO recipeBookDTO)) {
            return false;
        }

        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, recipeBookDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecipeBookDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", theme='" + getTheme() + "'" +
            ", description='" + getDescription() + "'" +
            ", tags='" + getTags() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
