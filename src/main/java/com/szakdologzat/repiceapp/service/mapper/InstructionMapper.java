package com.szakdologzat.repiceapp.service.mapper;

import com.szakdologzat.repiceapp.domain.Ingredient;
import com.szakdologzat.repiceapp.domain.Instruction;
import com.szakdologzat.repiceapp.domain.Recipe;
import com.szakdologzat.repiceapp.service.dto.IngredientDTO;
import com.szakdologzat.repiceapp.service.dto.InstructionDTO;
import com.szakdologzat.repiceapp.service.dto.RecipeDTO;
import java.util.Set;
import java.util.stream.Collectors;
import jdk.jfr.Name;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Instruction} and its DTO {@link InstructionDTO}.
 */
@Mapper(componentModel = "spring")
public interface InstructionMapper extends EntityMapper<InstructionDTO, Instruction> {
    @Mapping(target = "recipe", source = "recipe", qualifiedByName = "recipeId", ignore = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "ingredients", source = "ingredients", qualifiedByName = "mapIngredients")
    InstructionDTO toDto(Instruction s);

    @Named("recipeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RecipeDTO toDtoRecipeId(Recipe recipe);

    @Named("mapIngredients")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "id", target = "id")
    Set<IngredientDTO> mapInstructions(Set<Ingredient> ingredients);
}
