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
public class InstructionMapperImpl implements InstructionMapper {

    @Override
    public Instruction toEntity(InstructionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Instruction instruction = new Instruction();

        instruction.setId( dto.getId() );
        instruction.setTitle( dto.getTitle() );
        instruction.setRequiredTime( dto.getRequiredTime() );
        instruction.setDescription( dto.getDescription() );
        instruction.ingredients( ingredientDTOSetToIngredientSet( dto.getIngredients() ) );
        instruction.recipe( recipeDTOToRecipe( dto.getRecipe() ) );

        return instruction;
    }

    @Override
    public List<Instruction> toEntity(List<InstructionDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Instruction> list = new ArrayList<Instruction>( dtoList.size() );
        for ( InstructionDTO instructionDTO : dtoList ) {
            list.add( toEntity( instructionDTO ) );
        }

        return list;
    }

    @Override
    public List<InstructionDTO> toDto(List<Instruction> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<InstructionDTO> list = new ArrayList<InstructionDTO>( entityList.size() );
        for ( Instruction instruction : entityList ) {
            list.add( toDto( instruction ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Instruction entity, InstructionDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getTitle() != null ) {
            entity.setTitle( dto.getTitle() );
        }
        if ( dto.getRequiredTime() != null ) {
            entity.setRequiredTime( dto.getRequiredTime() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( entity.getIngredients() != null ) {
            Set<Ingredient> set = ingredientDTOSetToIngredientSet( dto.getIngredients() );
            if ( set != null ) {
                entity.getIngredients().clear();
                entity.getIngredients().addAll( set );
            }
        }
        else {
            Set<Ingredient> set = ingredientDTOSetToIngredientSet( dto.getIngredients() );
            if ( set != null ) {
                entity.ingredients( set );
            }
        }
        if ( dto.getRecipe() != null ) {
            if ( entity.getRecipe() == null ) {
                entity.recipe( new Recipe() );
            }
            recipeDTOToRecipe1( dto.getRecipe(), entity.getRecipe() );
        }
    }

    @Override
    public InstructionDTO toDto(Instruction s) {
        if ( s == null ) {
            return null;
        }

        InstructionDTO instructionDTO = new InstructionDTO();

        instructionDTO.setId( s.getId() );
        instructionDTO.setIngredients( mapInstructions( s.getIngredients() ) );
        instructionDTO.setTitle( s.getTitle() );
        instructionDTO.setRequiredTime( s.getRequiredTime() );
        instructionDTO.setDescription( s.getDescription() );

        return instructionDTO;
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

    @Override
    public Set<IngredientDTO> mapInstructions(Set<Ingredient> ingredients) {
        if ( ingredients == null ) {
            return null;
        }

        Set<IngredientDTO> set = new LinkedHashSet<IngredientDTO>( Math.max( (int) ( ingredients.size() / .75f ) + 1, 16 ) );
        for ( Ingredient ingredient : ingredients ) {
            set.add( ingredientToIngredientDTO( ingredient ) );
        }

        return set;
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
        ingredient.instruction( toEntity( ingredientDTO.getInstruction() ) );

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

    protected Set<Instruction> instructionDTOSetToInstructionSet(Set<InstructionDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Instruction> set1 = new LinkedHashSet<Instruction>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( InstructionDTO instructionDTO : set ) {
            set1.add( toEntity( instructionDTO ) );
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

    protected IngredientDTO ingredientToIngredientDTO(Ingredient ingredient) {
        if ( ingredient == null ) {
            return null;
        }

        IngredientDTO ingredientDTO = new IngredientDTO();

        return ingredientDTO;
    }
}
