package com.szakdologzat.repiceapp.service.mapper;

import com.szakdologzat.repiceapp.domain.Rating;
import com.szakdologzat.repiceapp.domain.Recipe;
import com.szakdologzat.repiceapp.domain.User;
import com.szakdologzat.repiceapp.service.dto.RatingDTO;
import com.szakdologzat.repiceapp.service.dto.RecipeDTO;
import com.szakdologzat.repiceapp.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rating} and its DTO {@link RatingDTO}.
 */
@Mapper(componentModel = "spring")
public interface RatingMapper extends EntityMapper<RatingDTO, Rating> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "recipe", ignore = true, qualifiedByName = "recipeId")
    RatingDTO toDto(Rating s);

    @Named("userId")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "avatar", source = "avatar")
    @BeanMapping(ignoreByDefault = true)
    UserDTO toDtoUserId(User user);

    @Named("recipeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RecipeDTO toDtoRecipeId(Recipe recipe);
}
