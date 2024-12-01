package com.szakdologzat.repiceapp.repository;

import com.szakdologzat.repiceapp.domain.FavoriteRelation;
import com.szakdologzat.repiceapp.domain.Recipe;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FavoriteRelation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FavoriteRelationRepository extends JpaRepository<FavoriteRelation, Long> {
    @Query("select favoriteRelation from FavoriteRelation favoriteRelation where favoriteRelation.user.login = ?#{authentication.name}")
    List<FavoriteRelation> findByUserIsCurrentUser();

    @Query(
        "SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM FavoriteRelation f WHERE f.recipe.id = :recipeId AND f.user.id = :userId"
    )
    boolean existsByUserIdAndRecipeId(Long userId, Long recipeId);

    @Query("select f from FavoriteRelation f where f.user.id = ?1 and f.recipe.id = ?2")
    Optional<FavoriteRelation> findByUserIdAndRecipeId(Long userId, Long recipeId);

    @Query("select COUNT(f) from FavoriteRelation f where f.recipe.id = ?1")
    Long getFavoriteCount(Long recipeId);
}
