package com.szakdologzat.repiceapp.repository;

import com.szakdologzat.repiceapp.domain.Rating;
import com.szakdologzat.repiceapp.domain.Recipe;
import com.szakdologzat.repiceapp.domain.enumeration.FoodCategory;
import com.szakdologzat.repiceapp.service.dto.RecipeDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Recipe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {
    @Query("select recipe from Recipe recipe where recipe.user.login = ?#{authentication.name}")
    List<Recipe> findByUserIsCurrentUser();

    Page<Recipe> findAll(Specification<Recipe> spec, Pageable pageable);

    @Query("select r from Recipe r where r.user.login = ?1")
    Page<Recipe> findFirstUserRecipes(String login, Pageable pageable);

    @Query("select r from Recipe r order by r.createdDate desc LIMIT 10")
    List<Recipe> findLatestRecipes();

    @Query("SELECT r FROM Recipe r " + "LEFT JOIN r.favoriteRelations fr " + "GROUP BY r " + "ORDER BY COUNT(fr) DESC " + "LIMIT 10")
    List<Recipe> findHighestFavoriteCounts();

    @Query("select r from Recipe r where r.foodCategory = ?1")
    List<Recipe> findSameFoodCategoryRecipes(FoodCategory foodCategory);

    @Query("SELECT r FROM Rating r WHERE r.recipe.id = ?1")
    Optional<List<Rating>> findRecipeRatings(Long id);

    @Query("select count(r) from Recipe r where r.user.login = ?1")
    Long countRecipesByUser(String login);

    @Query(
        "SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Recipe r WHERE r.id = ?1 AND r.user.login = ?#{authentication.name}"
    )
    boolean isUserTheOwner(Long recipeId);
    /*

    @Query("select r from Recipe r order by r.createdDate desc)
    List<Recipe> findHighestRatedRecipes();
*/

}
