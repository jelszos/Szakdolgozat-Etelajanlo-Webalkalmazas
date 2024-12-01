package com.szakdologzat.repiceapp.repository;

import com.szakdologzat.repiceapp.domain.Ingredient;
import java.util.Set;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Ingredient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    @Query("SELECT DISTINCT i FROM Ingredient i")
    Set<Ingredient> findAllUniqueIngredients();
}
