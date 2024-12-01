package com.szakdologzat.repiceapp.repository;

import com.szakdologzat.repiceapp.domain.Rating;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Rating entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query("select rating from Rating rating where rating.user.login = ?#{authentication.name}")
    List<Rating> findByUserIsCurrentUser();
}
