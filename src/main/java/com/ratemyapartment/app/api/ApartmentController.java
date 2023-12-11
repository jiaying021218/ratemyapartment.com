package com.ratemyapartment.app.api;


import com.ratemyapartment.app.model.Apartment;
import com.ratemyapartment.app.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path="/apartment")
public class ApartmentController { // TODO: HTML

    @Autowired
    private ApartmentService apartmentService;

    @GetMapping("")
    public List<Apartment> getAllApartment() {
        return apartmentService.getAllApartment();
    }

    @PostMapping("")
    public void addApartment(@RequestBody Apartment apartment) {
        apartmentService.addApartment(apartment);
    }

    @GetMapping("/{id}")
    public String getApartmentById(@PathVariable Long id, Model model) {
        Apartment apartment = apartmentService.getApartmentById(id);

        model.addAttribute("apartment", apartment);
        return "apartment";
    }

    @DeleteMapping("/{id}")
    public void deleteApartmentById(@PathVariable Long id) {
        apartmentService.deleteApartmentById(id);
    }

    @PutMapping("/{id}/name")
    public void updateApartmentNameById(@PathVariable Long id, @RequestBody Apartment apartment) {
        apartmentService.updateApartmentNameById(id, apartment.getName());
    }

    @PutMapping("/{id}/address")
    public void updateApartmentAddressById(@PathVariable Long id, @RequestBody Apartment apartment) {
        apartmentService.updateApartmentAddressById(
                id,
                apartment.getAddress_line_1(),
                apartment.getAddress_line_2(),
                apartment.getCity(),
                apartment.getState(),
                apartment.getZip_code(),
                apartment.getNeighborhood());
    }

    @PutMapping("/{id}/status")
    public void updateApartmentStatusById(@PathVariable Long id, @RequestBody Apartment apartment) {
        apartmentService.updateApartmentStatusById(
                id,
                apartment.getStatus(),
                apartment.getStatus_note());
    }

    @GetMapping("/name/{name}")
    public String getApartmentByName(@PathVariable String name, Model model) {
        Apartment a = apartmentService.getApartmentByName(name);
        if(a == null) {
            return "<h1>Apartment NOT FOUND</h1>";
        }
        model.addAttribute("apartment", a);
        return "apartment";
    }

    @GetMapping("/city/{city}")
    public List<Apartment> getApartmentByCity(@PathVariable String city) {
        return apartmentService.getApartmentByCity(city);
    }

    @GetMapping("/state/{state}")
    public List<Apartment> getApartmentByState(@PathVariable String state) {
        return apartmentService.getApartmentByState(state);
    }

    @GetMapping("/neighborhood/{neighborhood}")
    public List<Apartment> getApartmentByNeighborhood(@PathVariable String neighborhood) {
        return apartmentService.getApartmentByNeighborhood(neighborhood);
    }
}
