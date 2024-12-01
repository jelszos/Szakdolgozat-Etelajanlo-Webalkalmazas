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
public class RecipeMapperImpl extends RecipeMapper {

    @Override
    public Recipe toEntity(RecipeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Recipe recipe = new Recipe();

        recipe.setId( dto.getId() );
        recipe.setTitle( dto.getTitle() );
        recipe.setDescription( dto.getDescription() );
        recipe.setIngredientNames( dto.getIngredientNames() );
        recipe.setFoodCategory( dto.getFoodCategory() );
        recipe.setFoodType( dto.getFoodType() );
        recipe.setImageUrl( dto.getImageUrl() );
        recipe.setCreatedDate( dto.getCreatedDate() );
        recipe.instructions( instructionDTOSetToInstructionSet( dto.getInstructions() ) );
        recipe.ratings( ratingDTOSetToRatingSet( dto.getRatings() ) );
        recipe.user( userDTOToUser( dto.getUser() ) );

        return recipe;
    }

    @Override
    public List<Recipe> toEntity(List<RecipeDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Recipe> list = new ArrayList<Recipe>( dtoList.size() );
        for ( RecipeDTO recipeDTO : dtoList ) {
            list.add( toEntity( recipeDTO ) );
        }

        return list;
    }

    @Override
    public List<RecipeDTO> toDto(List<Recipe> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RecipeDTO> list = new ArrayList<RecipeDTO>( entityList.size() );
        for ( Recipe recipe : entityList ) {
            list.add( toDto( recipe ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Recipe entity, RecipeDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getTitle() != null ) {
            entity.setTitle( dto.getTitle() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getIngredientNames() != null ) {
            entity.setIngredientNames( dto.getIngredientNames() );
        }
        if ( dto.getFoodCategory() != null ) {
            entity.setFoodCategory( dto.getFoodCategory() );
        }
        if ( dto.getFoodType() != null ) {
            entity.setFoodType( dto.getFoodType() );
        }
        if ( dto.getImageUrl() != null ) {
            entity.setImageUrl( dto.getImageUrl() );
        }
        if ( dto.getCreatedDate() != null ) {
            entity.setCreatedDate( dto.getCreatedDate() );
        }
        if ( entity.getInstructions() != null ) {
            Set<Instruction> set = instructionDTOSetToInstructionSet( dto.getInstructions() );
            if ( set != null ) {
                entity.getInstructions().clear();
                entity.getInstructions().addAll( set );
            }
        }
        else {
            Set<Instruction> set = instructionDTOSetToInstructionSet( dto.getInstructions() );
            if ( set != null ) {
                entity.instructions( set );
            }
        }
        if ( entity.getRatings() != null ) {
            Set<Rating> set1 = ratingDTOSetToRatingSet( dto.getRatings() );
            if ( set1 != null ) {
                entity.getRatings().clear();
                entity.getRatings().addAll( set1 );
            }
        }
        else {
            Set<Rating> set1 = ratingDTOSetToRatingSet( dto.getRatings() );
            if ( set1 != null ) {
                entity.ratings( set1 );
            }
        }
        if ( dto.getUser() != null ) {
            if ( entity.getUser() == null ) {
                entity.user( new User() );
            }
            userDTOToUser1( dto.getUser(), entity.getUser() );
        }
    }

    @Override
    public RecipeDTO toDto(Recipe s) {
        if ( s == null ) {
            return null;
        }

        RecipeDTO recipeDTO = new RecipeDTO();

        recipeDTO.setUser( userToUserDTO( s.getUser() ) );
        recipeDTO.setRatings( mapRatings( s.getRatings() ) );
        recipeDTO.setInstructions( mapInstructions( s.getInstructions() ) );
        recipeDTO.setId( s.getId() );
        recipeDTO.setTitle( s.getTitle() );
        recipeDTO.setDescription( s.getDescription() );
        recipeDTO.setIngredientNames( s.getIngredientNames() );
        recipeDTO.setFoodCategory( s.getFoodCategory() );
        recipeDTO.setFoodType( s.getFoodType() );
        recipeDTO.setImageUrl( s.getImageUrl() );
        recipeDTO.setCreatedDate( s.getCreatedDate() );

        recipeDTO.setIsFavorite( recipeService.isFavoriteByUser(s.getId()) );
        recipeDTO.setFavoriteCount( favoriteRelationService.getFavoriteCount(s.getId()) );
        recipeDTO.setRequiredTimeSum( getRequiredTimeSum(s.getInstructions()) );
        recipeDTO.setTotalRating( getTotalRating(s.getRatings()) );

        return recipeDTO;
    }

    @Override
    public UserDTO toDtoUserId(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getId() );
        userDTO.setAvatar( user.getAvatar() );
        userDTO.setFirstName( user.getFirstName() );
        userDTO.setLogin( user.getLogin() );
        userDTO.setLastName( user.getLastName() );

        return userDTO;
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
        instruction.recipe( toEntity( instructionDTO.getRecipe() ) );

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
        rating.recipe( toEntity( ratingDTO.getRecipe() ) );

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

    protected UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setAvatar( user.getAvatar() );
        userDTO.setFirstName( user.getFirstName() );
        userDTO.setLastName( user.getLastName() );
        userDTO.setId( user.getId() );
        userDTO.setLogin( user.getLogin() );

        return userDTO;
    }
}
