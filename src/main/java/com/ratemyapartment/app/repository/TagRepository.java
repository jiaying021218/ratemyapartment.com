package com.ratemyapartment.app.repository;

import com.ratemyapartment.app.model.Tag;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("SELECT t FROM Tag t WHERE t.id = :id")
    Tag getTagById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query ("UPDATE Tag SET name = :name WHERE id = :id")
    void updateTagNameById(@Param("id") Long id, @Param("name") String name);

    @Modifying
    @Transactional
    @Query ("UPDATE Tag SET positive_negative = :positive_negative WHERE id = :id")
    void updateTagPositiveNegativeById(@Param("id") Long id, @Param("positive_negative") String positive_negative);
}
