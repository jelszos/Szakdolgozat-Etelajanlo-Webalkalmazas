package com.szakdologzat.repiceapp.service.mapper;

import com.szakdologzat.repiceapp.domain.Ingredient;
import com.szakdologzat.repiceapp.domain.Instruction;
import com.szakdologzat.repiceapp.domain.Rating;
import com.szakdologzat.repiceapp.domain.Recipe;
import com.szakdologzat.repiceapp.domain.RecipeBook;
import com.szakdologzat.repiceapp.domain.RecipeBookRelation;
import com.szakdologzat.repiceapp.domain.User;
import com.szakdologzat.repiceapp.service.dto.IngredientDTO;
import com.szakdologzat.repiceapp.service.dto.InstructionDTO;
import com.szakdologzat.repiceapp.service.dto.RatingDTO;
import com.szakdologzat.repiceapp.service.dto.RecipeBookDTO;
import com.szakdologzat.repiceapp.service.dto.RecipeBookRelationDTO;
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
public class RecipeBookRelationMapperImpl implements RecipeBookRelationMapper {

    @Override
    public RecipeBookRelation toEntity(RecipeBookRelationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        RecipeBookRelation recipeBookRelation = new RecipeBookRelation();

        recipeBookRelation.setId( dto.getId() );
        recipeBookRelation.recipe( recipeDTOToRecipe( dto.getRecipe() ) );
        recipeBookRelation.recipeBook( recipeBookDTOToRecipeBook( dto.getRecipeBook() ) );

        return recipeBookRelation;
    }

    @Override
    public List<RecipeBookRelation> toEntity(List<RecipeBookRelationDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<RecipeBookRelation> list = new ArrayList<RecipeBookRelation>( dtoList.size() );
        for ( RecipeBookRelationDTO recipeBookRelationDTO : dtoList ) {
            list.add( toEntity( recipeBookRelationDTO ) );
        }

        return list;
    }

    @Override
    public List<RecipeBookRelationDTO> toDto(List<RecipeBookRelation> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RecipeBookRelationDTO> list = new ArrayList<RecipeBookRelationDTO>( entityList.size() );
        for ( RecipeBookRelation recipeBookRelation : entityList ) {
            list.add( toDto( recipeBookRelation ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(RecipeBookRelation entity, RecipeBookRelationDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getRecipe() != null ) {
            if ( entity.getRecipe() == null ) {
                entity.recipe( new Recipe() );
            }
            recipeDTOToRecipe1( dto.getRecipe(), entity.getRecipe() );
        }
        if ( dto.getRecipeBook() != null ) {
            if ( entity.getRecipeBook() == null ) {
                entity.recipeBook( new RecipeBook() );
            }
            recipeBookDTOToRecipeBook1( dto.getRecipeBook(), entity.getRecipeBook() );
        }
    }

    @Override
    public RecipeBookRelationDTO toDto(RecipeBookRelation s) {
        if ( s == null ) {
            return null;
        }

        RecipeBookRelationDTO recipeBookRelationDTO = new RecipeBookRelationDTO();

        recipeBookRelationDTO.setId( s.getId() );

        return recipeBookRelationDTO;
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
    public RecipeBookDTO toDtoRecipeBookId(RecipeBook recipeBook) {
        if ( recipeBook == null ) {
            return null;
        }

        RecipeBookDTO recipeBookDTO = new RecipeBookDTO();

        recipeBookDTO.setId( recipeBook.getId() );

        return recipeBookDTO;
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

    protected RecipeBook recipeBookDTOToRecipeBook(RecipeBookDTO recipeBookDTO) {
        if ( recipeBookDTO == null ) {
            return null;
        }

        RecipeBook recipeBook = new RecipeBook();

        recipeBook.setIsPrivate( recipeBookDTO.getIsPrivate() );
        recipeBook.setId( recipeBookDTO.getId() );
        recipeBook.setTitle( recipeBookDTO.getTitle() );
        recipeBook.setTheme( recipeBookDTO.getTheme() );
        recipeBook.setDescription( recipeBookDTO.getDescription() );
        recipeBook.setTags( recipeBookDTO.getTags() );
        recipeBook.setCreatedDate( recipeBookDTO.getCreatedDate() );
        recipeBook.user( userDTOToUser( recipeBookDTO.getUser() ) );

        return recipeBook;
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

    protected void recipeBookDTOToRecipeBook1(RecipeBookDTO recipeBookDTO, RecipeBook mappingTarget) {
        if ( recipeBookDTO == null ) {
            return;
        }

        mappingTarget.setIsPrivate( recipeBookDTO.getIsPrivate() );
        if ( recipeBookDTO.getId() != null ) {
            mappingTarget.setId( recipeBookDTO.getId() );
        }
        if ( recipeBookDTO.getTitle() != null ) {
            mappingTarget.setTitle( recipeBookDTO.getTitle() );
        }
        if ( recipeBookDTO.getTheme() != null ) {
            mappingTarget.setTheme( recipeBookDTO.getTheme() );
        }
        if ( recipeBookDTO.getDescription() != null ) {
            mappingTarget.setDescription( recipeBookDTO.getDescription() );
        }
        if ( recipeBookDTO.getTags() != null ) {
            mappingTarget.setTags( recipeBookDTO.getTags() );
        }
        if ( recipeBookDTO.getCreatedDate() != null ) {
            mappingTarget.setCreatedDate( recipeBookDTO.getCreatedDate() );
        }
        if ( recipeBookDTO.getUser() != null ) {
            if ( mappingTarget.getUser() == null ) {
                mappingTarget.user( new User() );
            }
            userDTOToUser1( recipeBookDTO.getUser(), mappingTarget.getUser() );
        }
    }
}
