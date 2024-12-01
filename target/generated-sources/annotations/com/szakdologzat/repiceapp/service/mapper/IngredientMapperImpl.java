package com.szakdologzat.repiceapp.service.mapper;

import com.szakdologzat.repiceapp.domain.Ingredient;
import com.szakdologzat.repiceapp.domain.Instruction;
import com.szakdologzat.repiceapp.domain.Rating;
import com.szakdologzat.repiceapp.domain.Recipe;
import com.szakdologzat.repiceapp.domain.User;
import com.szakdologzat.repiceapp.service.dto.IngredientDTO;
import com.szakdologzat.repiceapp.service.dto.InstructionDTO;
import com.szakdologzat.repiceapp.service.dto.RatingDTO;
import com.szakdologzat.repiceapp.service.dto.RecipeDTO;
import com.szakdologzat.repiceapp.service.dto.UserDTO;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-01T10:18:44+0100",
    comments = "version: 1.6.2, compiler: javac, environment: Java 22.0.2 (Homebrew)"
)
@Component
public class IngredientMapperImpl implements IngredientMapper {

    @Override
    public Ingredient toEntity(IngredientDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Ingredient ingredient = new Ingredient();

        ingredient.setId( dto.getId() );
        ingredient.setName( dto.getName() );
        ingredient.setAmount( dto.getAmount() );
        ingredient.setUnit( dto.getUnit() );
        ingredient.instruction( instructionDTOToInstruction( dto.getInstruction() ) );

        return ingredient;
    }

    @Override
    public List<Ingredient> toEntity(List<IngredientDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Ingredient> list = new ArrayList<Ingredient>( dtoList.size() );
        for ( IngredientDTO ingredientDTO : dtoList ) {
            list.add( toEntity( ingredientDTO ) );
        }

        return list;
    }

    @Override
    public List<IngredientDTO> toDto(List<Ingredient> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<IngredientDTO> list = new ArrayList<IngredientDTO>( entityList.size() );
        for ( Ingredient ingredient : entityList ) {
            list.add( toDto( ingredient ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Ingredient entity, IngredientDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getAmount() != null ) {
            entity.setAmount( dto.getAmount() );
        }
        if ( dto.getUnit() != null ) {
            entity.setUnit( dto.getUnit() );
        }
        if ( dto.getInstruction() != null ) {
            if ( entity.getInstruction() == null ) {
                entity.instruction( new Instruction() );
            }
            instructionDTOToInstruction1( dto.getInstruction(), entity.getInstruction() );
        }
    }

    @Override
    public IngredientDTO toDto(Ingredient s) {
        if ( s == null ) {
            return null;
        }

        IngredientDTO ingredientDTO = new IngredientDTO();

        ingredientDTO.setInstruction( toDtoInstructionId( s.getInstruction() ) );
        ingredientDTO.setId( s.getId() );
        ingredientDTO.setName( s.getName() );
        ingredientDTO.setAmount( s.getAmount() );
        ingredientDTO.setUnit( s.getUnit() );

        return ingredientDTO;
    }

    @Override
    public InstructionDTO toDtoInstructionId(Instruction instruction) {
        if ( instruction == null ) {
            return null;
        }

        InstructionDTO instructionDTO = new InstructionDTO();

        instructionDTO.setId( instruction.getId() );

        return instructionDTO;
    }

    protected Set<Ingredient> ingredientDTOSetToIngredientSet(Set<IngredientDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Ingredient> set1 = new LinkedHashSet<Ingredient>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( IngredientDTO ingredientDTO : set ) {
            set1.add( toEntity( ingredientDTO ) );
        }

        return set1;
    }

    protected Set<Instruction> instructionDTOSetToInstructionSet(Set<InstructionDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Instruction> set1 = new LinkedHashSet<Instruction>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( InstructionDTO instructionDTO : set ) {
            set1.add( instructionDTOToInstruction( instructionDTO ) );
        }

        return set1;
    }

    protected User userDTOToUser(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDTO.getId() );
        user.setLogin( userDTO.getLogin() );
        user.setFirstName( userDTO.getFirstName() );
        user.setLastName( userDTO.getLastName() );
        user.setAvatar( userDTO.getAvatar() );

        return user;
    }

    protected Rating ratingDTOToRating(RatingDTO ratingDTO) {
        if ( ratingDTO == null ) {
            return null;
        }

        Rating rating = new Rating();

        rating.setId( ratingDTO.getId() );
        rating.setRate( ratingDTO.getRate() );
        rating.setTitle( ratingDTO.getTitle() );
        rating.setDescription( ratingDTO.getDescription() );
        rating.setImageUrl( ratingDTO.getImageUrl() );
        rating.setCreatedDate( ratingDTO.getCreatedDate() );
        rating.user( userDTOToUser( ratingDTO.getUser() ) );
        rating.recipe( recipeDTOToRecipe( ratingDTO.getRecipe() ) );

        return rating;
    }

    protected Set<Rating> ratingDTOSetToRatingSet(Set<RatingDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Rating> set1 = new LinkedHashSet<Rating>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( RatingDTO ratingDTO : set ) {
            set1.add( ratingDTOToRating( ratingDTO ) );
        }

        return set1;
    }

    protected Recipe recipeDTOToRecipe(RecipeDTO recipeDTO) {
        if ( recipeDTO == null ) {
            return null;
        }

        Recipe recipe = new Recipe();

        recipe.setId( recipeDTO.getId() );
        recipe.setTitle( recipeDTO.getTitle() );
        recipe.setDescription( recipeDTO.getDescription() );
        recipe.setIngredientNames( recipeDTO.getIngredientNames() );
        recipe.setFoodCategory( recipeDTO.getFoodCategory() );
        recipe.setFoodType( recipeDTO.getFoodType() );
        recipe.setImageUrl( recipeDTO.getImageUrl() );
        recipe.setCreatedDate( recipeDTO.getCreatedDate() );
        recipe.instructions( instructionDTOSetToInstructionSet( recipeDTO.getInstructions() ) );
        recipe.ratings( ratingDTOSetToRatingSet( recipeDTO.getRatings() ) );
        recipe.user( userDTOToUser( recipeDTO.getUser() ) );

        return recipe;
    }

    protected Instruction instructionDTOToInstruction(InstructionDTO instructionDTO) {
        if ( instructionDTO == null ) {
            return null;
        }

        Instruction instruction = new Instruction();

        instruction.setId( instructionDTO.getId() );
        instruction.setTitle( instructionDTO.getTitle() );
        instruction.setRequiredTime( instructionDTO.getRequiredTime() );
        instruction.setDescription( instructionDTO.getDescription() );
        instruction.ingredients( ingredientDTOSetToIngredientSet( instructionDTO.getIngredients() ) );
        instruction.recipe( recipeDTOToRecipe( instructionDTO.getRecipe() ) );

        return instruction;
    }

    protected void userDTOToUser1(UserDTO userDTO, User mappingTarget) {
        if ( userDTO == null ) {
            return;
        }

        if ( userDTO.getId() != null ) {
            mappingTarget.setId( userDTO.getId() );
        }
        if ( userDTO.getLogin() != null ) {
            mappingTarget.setLogin( userDTO.getLogin() );
        }
        if ( userDTO.getFirstName() != null ) {
            mappingTarget.setFirstName( userDTO.getFirstName() );
        }
        if ( userDTO.getLastName() != null ) {
            mappingTarget.setLastName( userDTO.getLastName() );
        }
        if ( userDTO.getAvatar() != null ) {
            mappingTarget.setAvatar( userDTO.getAvatar() );
        }
    }

    protected void recipeDTOToRecipe1(RecipeDTO recipeDTO, Recipe mappingTarget) {
        if ( recipeDTO == null ) {
            return;
        }

        if ( recipeDTO.getId() != null ) {
            mappingTarget.setId( recipeDTO.getId() );
        }
        if ( recipeDTO.getTitle() != null ) {
            mappingTarget.setTitle( recipeDTO.getTitle() );
        }
        if ( recipeDTO.getDescription() != null ) {
            mappingTarget.setDescription( recipeDTO.getDescription() );
        }
        if ( recipeDTO.getIngredientNames() != null ) {
            mappingTarget.setIngredientNames( recipeDTO.getIngredientNames() );
        }
        if ( recipeDTO.getFoodCategory() != null ) {
            mappingTarget.setFoodCategory( recipeDTO.getFoodCategory() );
        }
        if ( recipeDTO.getFoodType() != null ) {
            mappingTarget.setFoodType( recipeDTO.getFoodType() );
        }
        if ( recipeDTO.getImageUrl() != null ) {
            mappingTarget.setImageUrl( recipeDTO.getImageUrl() );
        }
        if ( recipeDTO.getCreatedDate() != null ) {
            mappingTarget.setCreatedDate( recipeDTO.getCreatedDate() );
        }
        if ( mappingTarget.getInstructions() != null ) {
            Set<Instruction> set = instructionDTOSetToInstructionSet( recipeDTO.getInstructions() );
            if ( set != null ) {
                mappingTarget.getInstructions().clear();
                mappingTarget.getInstructions().addAll( set );
            }
        }
        else {
            Set<Instruction> set = instructionDTOSetToInstructionSet( recipeDTO.getInstructions() );
            if ( set != null ) {
                mappingTarget.instructions( set );
            }
        }
        if ( mappingTarget.getRatings() != null ) {
            Set<Rating> set1 = ratingDTOSetToRatingSet( recipeDTO.getRatings() );
            if ( set1 != null ) {
                mappingTarget.getRatings().clear();
                mappingTarget.getRatings().addAll( set1 );
            }
        }
        else {
            Set<Rating> set1 = ratingDTOSetToRatingSet( recipeDTO.getRatings() );
            if ( set1 != null ) {
                mappingTarget.ratings( set1 );
            }
        }
        if ( recipeDTO.getUser() != null ) {
            if ( mappingTarget.getUser() == null ) {
                mappingTarget.user( new User() );
            }
            userDTOToUser1( recipeDTO.getUser(), mappingTarget.getUser() );
        }
    }

    protected void instructionDTOToInstruction1(InstructionDTO instructionDTO, Instruction mappingTarget) {
        if ( instructionDTO == null ) {
            return;
        }

        if ( instructionDTO.getId() != null ) {
            mappingTarget.setId( instructionDTO.getId() );
        }
        if ( instructionDTO.getTitle() != null ) {
            mappingTarget.setTitle( instructionDTO.getTitle() );
        }
        if ( instructionDTO.getRequiredTime() != null ) {
            mappingTarget.setRequiredTime( instructionDTO.getRequiredTime() );
        }
        if ( instructionDTO.getDescription() != null ) {
            mappingTarget.setDescription( instructionDTO.getDescription() );
        }
        if ( mappingTarget.getIngredients() != null ) {
            Set<Ingredient> set = ingredientDTOSetToIngredientSet( instructionDTO.getIngredients() );
            if ( set != null ) {
                mappingTarget.getIngredients().clear();
                mappingTarget.getIngredients().addAll( set );
            }
        }
        else {
            Set<Ingredient> set = ingredientDTOSetToIngredientSet( instructionDTO.getIngredients() );
            if ( set != null ) {
                mappingTarget.ingredients( set );
            }
        }
        if ( instructionDTO.getRecipe() != null ) {
            if ( mappingTarget.getRecipe() == null ) {
                mappingTarget.recipe( new Recipe() );
            }
            recipeDTOToRecipe1( instructionDTO.getRecipe(), mappingTarget.getRecipe() );
        }
    }
}
