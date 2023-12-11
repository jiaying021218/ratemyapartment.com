package com.ratemyapartment.app.repository;

import com.ratemyapartment.app.model.Rating;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT r FROM Rating r WHERE r.id = :id")
    public Rating getRatingById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Rating SET title = :title, description = :description, value = :value, would_recommend = :would_recommend WHERE id = :id")
    public void updateRatingRatingById(@Param("id") Long id, @Param("title") String title, @Param("description") String description, @Param("value") int value, @Param("would_recommend") String would_recommend);

    @Modifying
    @Transactional
    @Query("UPDATE Rating SET status = :status, status_note = :status_note WHERE id = :id")
    public void updateRatingStatusById(@Param("id") Long id, @Param("status") String status, @Param("status_note") String status_note);

    @Query("SELECT r FROM Rating r WHERE r.apartment_id = : id")
    public List<Rating> getRatingByApartmentId(@Param("id") Long id);

    @Query("SELECT r FROM Rating r WHERE r.apartment_id = :id and r.status = :status")
    public List<Rating> getRatingByApartmentIdAndStatus(@Param("id") Long id, @Param("status") String status);

    @Query("SELECT r FROM Rating r WHERE r.value = :value")
    public List<Rating> getRatingByValue(@Param("value") Float value);
}
