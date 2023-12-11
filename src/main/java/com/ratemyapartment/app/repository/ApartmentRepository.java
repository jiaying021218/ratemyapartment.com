package com.ratemyapartment.app.repository;

import com.ratemyapartment.app.model.Apartment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    @Query("SELECT a FROM Apartment a WHERE a.id = :id")
    Apartment getApartmentById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Apartment SET name = :name WHERE id = :id")
    void updateApartmentNameById(@Param("id") Long id, @Param("name") String name);

    @Modifying
    @Transactional
    @Query("UPDATE Apartment SET address_line_1 = :address_line_1, address_line_2 = :address_line_2, city = :city, state = :state, zip_code = :zip_code, neighborhood = :neighborhood WHERE id = :id")
    void updateApartmentAddressById(@Param("id") Long id,
                                    @Param("address_line_1") String address_line_1,
                                    @Param("address_line_2") String address_line_2,
                                    @Param("city") String city,
                                    @Param("state") String state,
                                    @Param("zip_code") String zip_code,
                                    @Param("neighborhood") String neighborhood);

    @Modifying
    @Transactional
    @Query("UPDATE Apartment SET average_rating = :average_rating WHERE id = :id")
    void updateApartmentAverageRatingById(@Param("id") Long id, @Param("average_rating") float average_rating);

    @Modifying
    @Transactional
    @Query("UPDATE Apartment SET status = :status, status_note = :status_note WHERE id = :id")
    void updateApartmentStatusById(@Param("id") Long id, @Param("status") String status, @Param("status_note") String status_note);

    @Query("SELECT a FROM Apartment a where a.name = :name")
    Apartment getApartmentByName(@Param("name") String name);

    @Query("SELECT a FROM Apartment a WHERE a.city = :city")
    List<Apartment> getApartmentByCity(@Param("city") String city);

    @Query("SELECT a FROM Apartment a WHERE a.state = :state")
    List<Apartment> getApartmentByState(@Param("state") String state);

    @Query("SELECT a FROM Apartment a WHERE a.neighborhood = :neighborhood")
    List<Apartment> getApartmentByNeighborhood(@Param("neighborhood") String neighborhood);
}
