package com.szakdologzat.repiceapp.service.dto;

import com.szakdologzat.repiceapp.domain.Recipe;
import java.util.List;

public class ProfileDTO {

    private List<RecipeDTO> recipes;
    private List<RecipeBookDTO> recipeBooks;
    private AdminUserDTO adminUser;
    private Long sumRecipes;
    private Long sumRecipeBooks;

    public List<RecipeBookDTO> getRecipeBooks() {
        return recipeBooks;
    }

    public void setRecipeBooks(List<RecipeBookDTO> recipeBooks) {
        this.recipeBooks = recipeBooks;
    }

    public Long getSumRecipes() {
        return sumRecipes;
    }

    public void setSumRecipes(Long sumRecipes) {
        this.sumRecipes = sumRecipes;
    }

    public Long getSumRecipeBooks() {
        return sumRecipeBooks;
    }

    public void setSumRecipeBooks(Long sumRecipeBooks) {
        this.sumRecipeBooks = sumRecipeBooks;
    }

    public List<RecipeDTO> getRecipes() {
        return this.recipes;
    }

    public void setRecipes(List<RecipeDTO> recipes) {
        this.recipes = recipes;
    }

    public AdminUserDTO getAdminUser() {
        return this.adminUser;
    }

    public void setAdminUser(AdminUserDTO adminUser) {
        this.adminUser = adminUser;
    }

    public ProfileDTO() {}

    public ProfileDTO(List<RecipeDTO> recipes, AdminUserDTO adminUser) {
        this.recipes = recipes;
        this.adminUser = adminUser;
    }
}
