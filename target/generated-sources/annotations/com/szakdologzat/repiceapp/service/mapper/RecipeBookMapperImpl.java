package com.szakdologzat.repiceapp.service.mapper;

import com.szakdologzat.repiceapp.domain.RecipeBook;
import com.szakdologzat.repiceapp.domain.User;
import com.szakdologzat.repiceapp.service.dto.RecipeBookDTO;
import com.szakdologzat.repiceapp.service.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-01T10:18:44+0100",
    comments = "version: 1.6.2, compiler: javac, environment: Java 22.0.2 (Homebrew)"
)
@Component
public class RecipeBookMapperImpl extends RecipeBookMapper {

    @Override
    public RecipeBook toEntity(RecipeBookDTO dto) {
        if ( dto == null ) {
            return null;
        }

        RecipeBook recipeBook = new RecipeBook();

        recipeBook.setIsPrivate( dto.getIsPrivate() );
        recipeBook.setId( dto.getId() );
        recipeBook.setTitle( dto.getTitle() );
        recipeBook.setTheme( dto.getTheme() );
        recipeBook.setDescription( dto.getDescription() );
        recipeBook.setTags( dto.getTags() );
        recipeBook.setCreatedDate( dto.getCreatedDate() );
        recipeBook.user( userDTOToUser( dto.getUser() ) );

        return recipeBook;
    }

    @Override
    public List<RecipeBook> toEntity(List<RecipeBookDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<RecipeBook> list = new ArrayList<RecipeBook>( dtoList.size() );
        for ( RecipeBookDTO recipeBookDTO : dtoList ) {
            list.add( toEntity( recipeBookDTO ) );
        }

        return list;
    }

    @Override
    public List<RecipeBookDTO> toDto(List<RecipeBook> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RecipeBookDTO> list = new ArrayList<RecipeBookDTO>( entityList.size() );
        for ( RecipeBook recipeBook : entityList ) {
            list.add( toDtoWithRecipeImages( recipeBook ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(RecipeBook entity, RecipeBookDTO dto) {
        if ( dto == null ) {
            return;
        }

        entity.setIsPrivate( dto.getIsPrivate() );
        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getTitle() != null ) {
            entity.setTitle( dto.getTitle() );
        }
        if ( dto.getTheme() != null ) {
            entity.setTheme( dto.getTheme() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getTags() != null ) {
            entity.setTags( dto.getTags() );
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
    }

    @Override
    public RecipeBookDTO toDto(RecipeBook s) {
        if ( s == null ) {
            return null;
        }

        RecipeBookDTO recipeBookDTO = new RecipeBookDTO();

        recipeBookDTO.setUser( toDtoUserId( s.getUser() ) );
        recipeBookDTO.setIsPrivate( s.getIsPrivate() );
        recipeBookDTO.setId( s.getId() );
        recipeBookDTO.setTitle( s.getTitle() );
        recipeBookDTO.setTheme( s.getTheme() );
        recipeBookDTO.setDescription( s.getDescription() );
        recipeBookDTO.setTags( s.getTags() );
        recipeBookDTO.setCreatedDate( s.getCreatedDate() );

        return recipeBookDTO;
    }

    @Override
    public RecipeBookDTO toDtoWithIsRecipeInTheList(RecipeBook s, long recipeId) {
        if ( s == null ) {
            return null;
        }

        RecipeBookDTO recipeBookDTO = new RecipeBookDTO();

        if ( s != null ) {
            recipeBookDTO.setIsPrivate( s.getIsPrivate() );
            recipeBookDTO.setId( s.getId() );
            recipeBookDTO.setTitle( s.getTitle() );
            recipeBookDTO.setTheme( s.getTheme() );
            recipeBookDTO.setDescription( s.getDescription() );
            recipeBookDTO.setTags( s.getTags() );
            recipeBookDTO.setCreatedDate( s.getCreatedDate() );
            recipeBookDTO.setUser( userToUserDTO( s.getUser() ) );
        }
        recipeBookDTO.setIsRecipeInList( recipeBookRelationService.isRecipeInTheList(recipeId, s.getId()) );

        return recipeBookDTO;
    }

    @Override
    public RecipeBookDTO toDtoWithRecipeImages(RecipeBook s) {
        if ( s == null ) {
            return null;
        }

        RecipeBookDTO recipeBookDTO = new RecipeBookDTO();

        recipeBookDTO.setUser( toDtoUserId( s.getUser() ) );
        recipeBookDTO.setIsPrivate( s.getIsPrivate() );
        recipeBookDTO.setId( s.getId() );
        recipeBookDTO.setTitle( s.getTitle() );
        recipeBookDTO.setTheme( s.getTheme() );
        recipeBookDTO.setDescription( s.getDescription() );
        recipeBookDTO.setTags( s.getTags() );
        recipeBookDTO.setCreatedDate( s.getCreatedDate() );

        recipeBookDTO.setRecipeImages( recipeBookRelationService.findImagesInRecipeBook(s.getId()) );

        return recipeBookDTO;
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
        userDTO.setAvatar( user.getAvatar() );
        userDTO.setLastName( user.getLastName() );

        return userDTO;
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
