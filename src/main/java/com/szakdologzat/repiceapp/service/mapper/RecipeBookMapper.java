package com.szakdologzat.repiceapp.service.mapper;

import com.szakdologzat.repiceapp.domain.RecipeBook;
import com.szakdologzat.repiceapp.domain.RecipeBookRelation;
import com.szakdologzat.repiceapp.domain.User;
import com.szakdologzat.repiceapp.service.RecipeBookRelationService;
import com.szakdologzat.repiceapp.service.dto.RecipeBookDTO;
import com.szakdologzat.repiceapp.service.dto.UserDTO;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

/**
 * Mapper for the entity {@link RecipeBook} and its DTO {@link RecipeBookDTO}.
 */
@Mapper(componentModel = "spring")
public abstract class RecipeBookMapper implements EntityMapper<RecipeBookDTO, RecipeBook> {

    @Autowired
    @Lazy
    RecipeBookRelationService recipeBookRelationService;

    @Named("toDto")
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    //    @Mapping(target = "isRecipeInList", expression = "java(recipeBookRelationService.isRecipeInTheList(recipeId))")
    public abstract RecipeBookDTO toDto(RecipeBook s);

    @Named("toDtoWithIsRecipeInTheList")
    //    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "isRecipeInList", expression = "java(recipeBookRelationService.isRecipeInTheList(recipeId, s.getId()))")
    public abstract RecipeBookDTO toDtoWithIsRecipeInTheList(RecipeBook s, long recipeId);

    //Recipebook with recipe Images
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "recipeImages", expression = "java(recipeBookRelationService.findImagesInRecipeBook(s.getId()))")
    public abstract RecipeBookDTO toDtoWithRecipeImages(RecipeBook s);

    //    @Named("toDtoIsRecipeInTheList")
    //    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    //    @Mapping(target = "recipes", expression = "java(recipeBookRelationService.getRecipesForRecipeBook())")
    //    @Mapping(target = "isRecipeInTheList", expression = "")
    //    public abstract RecipeBookDTO toDtoIsRecipeInTheList(RecipeBook s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "avatar", source = "avatar")
    @Mapping(target = "lastName", source = "lastName")
    public abstract UserDTO toDtoUserId(User user);
}
