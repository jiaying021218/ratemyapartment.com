package com.ratemyapartment.app.service;

import com.ratemyapartment.app.model.Apartment;
import com.ratemyapartment.app.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ApartmentService {

    @Autowired
    private ApartmentRepository apartmentRepository;

    static List<String> list_of_status = Arrays.asList("Submitted", "Hold", "Approved", "Rejected");

    public List<Apartment> getAllApartment() {
        return apartmentRepository.findAll();
    }

    public void addApartment(Apartment apartment) {
        apartmentRepository.save(apartment);
    }

    public Apartment getApartmentById(Long id) {
        return apartmentRepository.getApartmentById(id);
    }

    public void deleteApartmentById(Long id) {
        apartmentRepository.delete(apartmentRepository.getApartmentById(id));
    }

    public void updateApartmentNameById(Long id, String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Apartment name cannot be blank");
        }
        apartmentRepository.updateApartmentNameById(id, name);
    }

    public void updateApartmentAddressById(Long id, String a1, String a2, String city, String state, String zip_code, String neighborhood) {
        isValidAddress(a1, city, state, zip_code);
        apartmentRepository.updateApartmentAddressById(id, a1, a2, city, state, zip_code, neighborhood);
    }

    public void updateApartmentStatusById(Long id, String status, String status_note) {
        if (!list_of_status.contains(status)) {
            throw new IllegalArgumentException("Apartment status must be Submitted, Hold, Approved, Rejected");
        }
        apartmentRepository.updateApartmentStatusById(id, status, status_note);
    }

    public Apartment getApartmentByName(String name) {
        return apartmentRepository.getApartmentByName(name);
    }

    public void updateApartmentAverageRatingById(Long id, Float rating) {
        apartmentRepository.updateApartmentAverageRatingById(id, rating);
    }

    public List<Apartment> getApartmentByCity(String city) {
        return apartmentRepository.getApartmentByCity(city);
    }

    public List<Apartment> getApartmentByState(String state) {
        return apartmentRepository.getApartmentByState(state);
    }

    public List<Apartment> getApartmentByNeighborhood(String neighborhood) {
        return apartmentRepository.getApartmentByNeighborhood(neighborhood);
    }

    ///////////////////////////////////////////////////////////////////////////
    private void isValidAddress(String a1, String city, String state, String zip_code) {
        if (a1 == null) {
            throw new IllegalArgumentException("Apartment address_line_1 cannot be bull");
        }
        if (city == null) {
            throw new IllegalArgumentException("Apartment city cannot be bull");
        }
        if (state == null) {
            throw new IllegalArgumentException("Apartment state cannot be bull");
        }
        if (zip_code == null) {
            throw new IllegalArgumentException("Apartment zip_code cannot be bull");
        }
    }
}
