package com.ratemyapartment.app.api;


import com.ratemyapartment.app.model.Rating;
import com.ratemyapartment.app.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping("")
    public List<Rating> getAllRating() {
        return ratingService.getAllRating();
    }

    @PostMapping("")
    public void addRating(@RequestBody Rating rating) {
        ratingService.addRating(rating);
    }

    @GetMapping("/{id}")
    public Rating getRatingById(@PathVariable Long id) {
        return ratingService.getRatingById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteRatingById(@PathVariable Long id) {
        ratingService.deleteRatingById(id);
    }

    @GetMapping("/apartment/{id}")
    public List<Rating> getRatingByApartmentId(@PathVariable Long id) {
        return ratingService.getRatingByApartmentId(id);
    }

    @GetMapping("/apartment/{id}/{status}")
    public List<Rating> getRatingByApartmentIdAndStatus(@PathVariable Long id, @PathVariable String status) {
        return ratingService.getRatingByApartmentIdAndStatus(id, status);
    }

    @GetMapping("/value/{value}")
    public List<Rating> getRatingByValue(@PathVariable Float value) {
        return ratingService.getRatingByValue(value);
    }

    @PutMapping("/{id}/detail")
    public void updateRatingRatingById(@PathVariable Long id, @RequestBody Rating rating) {
        ratingService.updateRatingRatingById(
                id,
                rating.getTitle(),
                rating.getDescription(),
                rating.getValue(),
                rating.getWould_recommend());
    }

    @PutMapping("/{id}/status")
    public void updateRatingStatusById(@PathVariable Long id, @RequestBody Rating rating) {
        ratingService.updateRatingStatusById(
                id,
                rating.getStatus(),
                rating.getStatus_note());
    }
}
