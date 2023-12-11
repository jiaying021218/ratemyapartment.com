package com.ratemyapartment.app.service;


import com.ratemyapartment.app.model.Rating;
import com.ratemyapartment.app.repository.ApartmentRepository;
import com.ratemyapartment.app.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;

    static List<String> list_of_status = Arrays.asList("Submitted", "Hold", "Approved", "Rejected");

    public List<Rating> getAllRating() {
        return ratingRepository.findAll();
    }

    public void addRating(Rating rating) {
        ratingRepository.save(rating);
    }

    public Rating getRatingById(Long id) {
        return ratingRepository.getRatingById(id);
    }

    public void deleteRatingById(Long id) {
        ratingRepository.delete(ratingRepository.getRatingById(id));
    }

    public List<Rating> getRatingByApartmentId(Long id) {
        return ratingRepository.getRatingByApartmentId(id);
    }

    public List<Rating> getRatingByApartmentIdAndStatus(Long id, String status) {
        if (!list_of_status.contains(status)) {
            throw new IllegalArgumentException("Rating status must be Submitted, Hold, Approved, or Rejected");
        }
        return ratingRepository.getRatingByApartmentIdAndStatus(id, status);
    }

    public List<Rating> getRatingByValue(Float value) {
        return ratingRepository.getRatingByValue(value);
    }

    public void updateRatingRatingById(Long id, String title, String description, int value, String would_recommend) {
        if (!Objects.equals(would_recommend, "Yes") && !Objects.equals(would_recommend, "No")) {
            throw new IllegalArgumentException("Rating would_recommend must be Yes or No");
        }
        ratingRepository.updateRatingRatingById(id, title, description, value, would_recommend);
        ratingRepository.updateRatingStatusById(id, "Hold", "Rating was edited by user");
    }

    public void updateRatingStatusById(Long id, String status, String status_note) {
        if (!list_of_status.contains(status)) {
            throw new IllegalArgumentException("Rating status must be Submitted, Hold, Approved, or Rejected");
        }
        ratingRepository.updateRatingStatusById(id, status, status_note);
        updateApartmentAverageRatingById(ratingRepository.getRatingById(id).getApartment_id());
    }

    ///////////////////////////////////////////////////////////////////////////
    private void updateApartmentAverageRatingById(Long id) {
        List<Rating> list_of_approved_rating = ratingRepository.getRatingByApartmentIdAndStatus(id, "Approved");
        float value = 0;
        for (Rating r : list_of_approved_rating) {
            value += r.getValue();
        }
        value = value / list_of_approved_rating.size();
        apartmentRepository.updateApartmentAverageRatingById(id, value);
    }
}
