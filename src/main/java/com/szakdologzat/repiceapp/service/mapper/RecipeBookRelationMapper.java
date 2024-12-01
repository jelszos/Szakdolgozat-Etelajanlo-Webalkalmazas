package com.szakdologzat.repiceapp.service.mapper;

import com.szakdologzat.repiceapp.domain.Recipe;
import com.szakdologzat.repiceapp.domain.RecipeBook;
import com.szakdologzat.repiceapp.domain.RecipeBookRelation;
import com.szakdologzat.repiceapp.service.dto.RecipeBookDTO;
import com.szakdologzat.repiceapp.service.dto.RecipeBookRelationDTO;
import com.szakdologzat.repiceapp.service.dto.RecipeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RecipeBookRelation} and its DTO {@link RecipeBookRelationDTO}.
 */
@Mapper(componentModel = "spring")
public interface RecipeBookRelationMapper extends EntityMapper<RecipeBookRelationDTO, RecipeBookRelation> {
    @Mapping(target = "recipe", source = "recipe", qualifiedByName = "recipeId", ignore = true)
    @Mapping(target = "recipeBook", source = "recipeBook", qualifiedByName = "recipeBookId", ignore = true)
    RecipeBookRelationDTO toDto(RecipeBookRelation s);

    @Named("recipeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RecipeDTO toDtoRecipeId(Recipe recipe);

    @Named("recipeBookId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RecipeBookDTO toDtoRecipeBookId(RecipeBook recipeBook);
}
