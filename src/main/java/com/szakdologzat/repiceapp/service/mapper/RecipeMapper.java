package com.szakdologzat.repiceapp.service.mapper;

import com.szakdologzat.repiceapp.domain.*;
import com.szakdologzat.repiceapp.service.FavoriteRelationService;
import com.szakdologzat.repiceapp.service.RecipeService;
import com.szakdologzat.repiceapp.service.dto.*;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

/**
 * Mapper for the entity {@link Recipe} and its DTO {@link RecipeDTO}.
 */
@Mapper(componentModel = "spring")
public abstract class RecipeMapper implements EntityMapper<RecipeDTO, Recipe> {

    @Autowired
    @Lazy
    RecipeService recipeService;

    @Autowired
    @Lazy
    FavoriteRelationService favoriteRelationService;

    @Mapping(target = "user", source = "user")
    @Mapping(target = "ratings", source = "ratings", qualifiedByName = "mapRatings") // Add the annotation here
    @Mapping(target = "instructions", source = "instructions", qualifiedByName = "mapInstructions")
    @Mapping(target = "isFavorite", expression = "java(recipeService.isFavoriteByUser(s.getId()))")
    @Mapping(target = "favoriteCount", expression = "java(favoriteRelationService.getFavoriteCount(s.getId()))")
    @Mapping(target = "requiredTimeSum", expression = "java(getRequiredTimeSum(s.getInstructions()))")
    @Mapping(target = "totalRating", expression = "java(getTotalRating(s.getRatings()))")
    public abstract RecipeDTO toDto(Recipe s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "avatar", source = "avatar")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "login", source = "login")
    @Mapping(target = "lastName", source = "lastName")
    public abstract UserDTO toDtoUserId(User user);

    @Named("mapRatings")
    public Set<RatingDTO> mapRatings(Set<Rating> ratings) {
        if (ratings == null) {
            return null;
        }
        return ratings
            .stream()
            .map(rating -> {
                RatingDTO dto = new RatingDTO();
                dto.setId(rating.getId());
                dto.setRate(rating.getRate());
                dto.setCreatedDate(rating.getCreatedDate());
                dto.setTitle(rating.getTitle());
                dto.setImageUrl(rating.getImageUrl());
                dto.setDescription(rating.getDescription());
                dto.setUser(toDtoUserId(rating.getUser()));
                return dto;
            })
            .collect(Collectors.toSet());
    }

    @Named("mapInstructions")
    @BeanMapping(ignoreByDefault = true)
    public Set<InstructionDTO> mapInstructions(Set<Instruction> instructions) {
        if (instructions == null) {
            return null;
        }
        return instructions
            .stream()
            .map(instruction -> {
                InstructionDTO dto = new InstructionDTO();
                dto.setDescription(instruction.getDescription());
                dto.setId(instruction.getId());
                dto.setRequiredTime(instruction.getRequiredTime());
                dto.setTitle(instruction.getTitle());
                dto.setIngredients(mapIngredientsListToDTOList(instruction.getIngredients()));
                return dto;
            })
            .collect(Collectors.toSet());
    }

    public Set<IngredientDTO> mapIngredientsListToDTOList(Set<Ingredient> ingredients) {
        if (ingredients == null) {
            return null;
        }
        return ingredients
            .stream()
            .map(ingredient -> {
                IngredientDTO dto = new IngredientDTO();
                dto.setId(ingredient.getId());
                dto.setName(ingredient.getName());
                dto.setAmount(ingredient.getAmount());
                dto.setUnit(ingredient.getUnit());
                return dto;
            })
            .collect(Collectors.toSet());
    }

    @Named("getRequiredTimeSum")
    @BeanMapping(ignoreByDefault = true)
    public Long getRequiredTimeSum(Set<Instruction> instructions) {
        if (instructions == null || instructions.isEmpty()) {
            return null;
        }

        long requiredTimeSum = 0L;
        for (Instruction instruction : instructions) {
            requiredTimeSum += instruction.getRequiredTime();
        }

        return requiredTimeSum;
    }

    @Named("getTotalRating")
    @BeanMapping(ignoreByDefault = true)
    public Double getTotalRating(Set<Rating> ratings) {
        if (ratings == null || ratings.isEmpty()) {
            return 0.0;
        }

        double totalRating = 0.0;
        for (Rating rating : ratings) {
            totalRating += rating.getRate();
        }
        double finalRating = totalRating / ratings.size();

        return Math.round(finalRating * 10.0) / 10.0;
    }
}
