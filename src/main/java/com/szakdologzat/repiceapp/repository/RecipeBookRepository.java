package com.szakdologzat.repiceapp.repository;

import com.szakdologzat.repiceapp.domain.RecipeBook;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RecipeBook entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecipeBookRepository extends JpaRepository<RecipeBook, Long> {
    @Query("select recipeBook from RecipeBook recipeBook where recipeBook.user.login = ?#{authentication.name}")
    List<RecipeBook> findByUserIsCurrentUser();

    @Query("SELECT CASE WHEN COUNT(rb) > 0 THEN true ELSE false END from RecipeBook rb where rb.user.login = ?#{authentication.name}")
    boolean isOwnByUser();

    @Query(
        "select rb from RecipeBook rb where rb.isPrivate != true " +
        "AND (:title IS NULL OR lower(rb.title) LIKE lower(concat('%', :title, '%'))) " +
        "AND (:description IS NULL OR lower(rb.description) LIKE lower(concat('%', :description, '%')))" +
        "AND (:theme IS NULL OR lower(rb.theme) LIKE lower(concat('%', :theme, '%')))" +
        "AND (:tag IS NULL OR lower(rb.tags) LIKE lower(concat('%', :tag, '%')))"
    )
    Page<RecipeBook> findAllBySearch(
        @Param("title") String title,
        @Param("description") String description,
        @Param("theme") String theme,
        @Param("tag") String tag,
        Pageable pageable
    );

    @Query("select rb from RecipeBook rb where rb.isPrivate != true and rb.user.login = ?1")
    List<RecipeBook> findAllByUser(String login);

    @Query("select rb from RecipeBook rb where rb.user.login = ?1")
    List<RecipeBook> findAllByUserWithPrivate(String login);

    @Query("select count(rb) from RecipeBook rb where rb.user.login = ?1 and rb.isPrivate != true")
    Long countByUser(String login);

    @Query("select rb.user.login from RecipeBook rb where rb.id = ?1")
    String getOwner(Long recipeBookId);
}
