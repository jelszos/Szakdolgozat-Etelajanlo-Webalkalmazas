package com.szakdologzat.repiceapp.service.mapper;

import com.szakdologzat.repiceapp.domain.FavoriteRelation;
import com.szakdologzat.repiceapp.domain.Recipe;
import com.szakdologzat.repiceapp.domain.User;
import com.szakdologzat.repiceapp.service.dto.FavoriteRelationDTO;
import com.szakdologzat.repiceapp.service.dto.RecipeDTO;
import com.szakdologzat.repiceapp.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FavoriteRelation} and its DTO {@link FavoriteRelationDTO}.
 */
@Mapper(componentModel = "spring")
public interface FavoriteRelationMapper extends EntityMapper<FavoriteRelationDTO, FavoriteRelation> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId", ignore = true)
    @Mapping(target = "recipe", source = "recipe", qualifiedByName = "recipeId", ignore = true)
    FavoriteRelationDTO toDto(FavoriteRelation s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("recipeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RecipeDTO toDtoRecipeId(Recipe recipe);
}
