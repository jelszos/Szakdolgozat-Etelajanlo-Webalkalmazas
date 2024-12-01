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
public class RatingMapperImpl implements RatingMapper {

    @Override
    public Rating toEntity(RatingDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Rating rating = new Rating();

        rating.setId( dto.getId() );
        rating.setRate( dto.getRate() );
        rating.setTitle( dto.getTitle() );
        rating.setDescription( dto.getDescription() );
        rating.setImageUrl( dto.getImageUrl() );
        rating.setCreatedDate( dto.getCreatedDate() );
        rating.user( userDTOToUser( dto.getUser() ) );
        rating.recipe( recipeDTOToRecipe( dto.getRecipe() ) );

        return rating;
    }

    @Override
    public List<Rating> toEntity(List<RatingDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Rating> list = new ArrayList<Rating>( dtoList.size() );
        for ( RatingDTO ratingDTO : dtoList ) {
            list.add( toEntity( ratingDTO ) );
        }

        return list;
    }

    @Override
    public List<RatingDTO> toDto(List<Rating> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RatingDTO> list = new ArrayList<RatingDTO>( entityList.size() );
        for ( Rating rating : entityList ) {
            list.add( toDto( rating ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Rating entity, RatingDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getRate() != null ) {
            entity.setRate( dto.getRate() );
        }
        if ( dto.getTitle() != null ) {
            entity.setTitle( dto.getTitle() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getImageUrl() != null ) {
            entity.setImageUrl( dto.getImageUrl() );
        }
        if ( dto.getCreatedDate() != null ) {
            entity.setCreatedDate( dto.getCreatedDate() );
        }
        if ( dto.getUser() != null ) {
            if ( entity.getUser() == null ) {
                entity.user( new User() );
            }
            userDTOToUser1( dto.getUser(), entity.getUser() );
        }
        if ( dto.getRecipe() != null ) {
            if ( entity.getRecipe() == null ) {
                entity.recipe( new Recipe() );
            }
            recipeDTOToRecipe1( dto.getRecipe(), entity.getRecipe() );
        }
    }

    @Override
    public RatingDTO toDto(Rating s) {
        if ( s == null ) {
            return null;
        }

        RatingDTO ratingDTO = new RatingDTO();

        ratingDTO.setUser( toDtoUserId( s.getUser() ) );
        ratingDTO.setId( s.getId() );
        ratingDTO.setRate( s.getRate() );
        ratingDTO.setTitle( s.getTitle() );
        ratingDTO.setDescription( s.getDescription() );
        ratingDTO.setImageUrl( s.getImageUrl() );
        ratingDTO.setCreatedDate( s.getCreatedDate() );

        return ratingDTO;
    }

    @Override
    public UserDTO toDtoUserId(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getId() );
        userDTO.setLogin( user.getLogin() );
        userDTO.setFirstName( user.getFirstName() );
        userDTO.setLastName( user.getLastName() );
        userDTO.setAvatar( user.getAvatar() );

        return userDTO;
    }

    @Override
    public RecipeDTO toDtoRecipeId(Recipe recipe) {
        if ( recipe == null ) {
            return null;
        }

        RecipeDTO recipeDTO = new RecipeDTO();

        recipeDTO.setId( recipe.getId() );

        return recipeDTO;
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

    protected Ingredient ingredientDTOToIngredient(IngredientDTO ingredientDTO) {
        if ( ingredientDTO == null ) {
            return null;
        }

        Ingredient ingredient = new Ingredient();

        ingredient.setId( ingredientDTO.getId() );
        ingredient.setName( ingredientDTO.getName() );
        ingredient.setAmount( ingredientDTO.getAmount() );
        ingredient.setUnit( ingredientDTO.getUnit() );
        ingredient.instruction( instructionDTOToInstruction( ingredientDTO.getInstruction() ) );

        return ingredient;
    }

    protected Set<Ingredient> ingredientDTOSetToIngredientSet(Set<IngredientDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Ingredient> set1 = new LinkedHashSet<Ingredient>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( IngredientDTO ingredientDTO : set ) {
            set1.add( ingredientDTOToIngredient( ingredientDTO ) );
        }

        return set1;
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

    protected Set<Rating> ratingDTOSetToRatingSet(Set<RatingDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Rating> set1 = new LinkedHashSet<Rating>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( RatingDTO ratingDTO : set ) {
            set1.add( toEntity( ratingDTO ) );
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
}