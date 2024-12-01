package com.szakdologzat.repiceapp.repository;

import com.szakdologzat.repiceapp.domain.RecipeBookRelation;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Data JPA repository for the RecipeBookRelation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecipeBookRelationRepository extends JpaRepository<RecipeBookRelation, Long> {
    @Query("select rbr from RecipeBookRelation rbr where rbr.recipeBook.user.login = ?#{authentication.name}")
    Set<RecipeBookRelation> findByRecipeId();

    @Query(
        "SELECT CASE WHEN COUNT(rbr) > 0 THEN true ELSE false END " +
        "FROM RecipeBookRelation rbr " +
        "WHERE rbr.recipe.id = :recipeId " +
        "AND rbr.recipeBook.id = :recipeBookId " +
        "AND rbr.recipeBook.user.login = ?#{authentication.name}"
    )
    boolean isRecipeInRecipeBook(@Param("recipeId") Long recipeId, @Param("recipeBookId") Long recipeBookId);

    @Transactional
    @Modifying
    @Query(
        "delete from RecipeBookRelation rbr where rbr.recipe.id = ?1 and rbr.recipeBook.id = ?2 and rbr.recipeBook.user.login = ?#{authentication.name}"
    )
    void deleteByRecipeAndRecipeBookId(Long recipeId, Long recipeBookId);

    @Query("select rbr from RecipeBookRelation rbr where rbr.recipeBook.id = ?1")
    Set<RecipeBookRelation> findAllByRecipeBookId(Long recipeBookId);

    //check is the the logged in user is the owner of the recipeBook
    @Query(
        "SELECT CASE WHEN COUNT(rbr) > 0 THEN true ELSE false END FROM RecipeBookRelation rbr where rbr.recipeBook.user.login = ?#{authentication.name} and rbr.recipeBook.id = ?1"
    )
    boolean isUserTheOwner(Long recipeBookId);

    @Transactional
    @Query(
        "select rbr from RecipeBookRelation rbr where rbr.recipe.id = ?1 and rbr.recipeBook.id = ?2 and rbr.recipeBook.user.login = ?#{authentication.name}"
    )
    Optional<RecipeBookRelation> getRbrByRecipeBookIdAndRecipeId(Long recipeId, Long recipeBookId);
}
