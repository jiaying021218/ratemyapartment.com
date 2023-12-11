package com.ratemyapartment.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity

@Table(name = "Apartment")
public class Apartment {
    ///////////////////////////////////////////////////////////////////////////
    // Default Constructor
    public Apartment() {}
    // Json Constructor
    public Apartment(@JsonProperty("name") String name,
                     @JsonProperty("address_line_1") String address_line_1,
                     @JsonProperty("address_line_2") String address_line_2,
                     @JsonProperty("city") String city,
                     @JsonProperty("state") String state,
                     @JsonProperty("zip_code") String zip_code,
                     @JsonProperty("neighborhood") String neighborhood,
                     @JsonProperty("status") String status,
                     @JsonProperty("status_note") String status_note) {
        this.name = name;
        this.address_line_1 = address_line_1;
        this.address_line_2 = address_line_2;
        this.city = city;
        this.state = state;
        this.zip_code = zip_code;
        this.neighborhood = neighborhood;
        this.status = "Submitted";
        this.status_note = status_note;
        this.created_at = new Timestamp(System.currentTimeMillis());
        this.updated_at = new Timestamp(System.currentTimeMillis());
    }

    ///////////////////////////////////////////////////////////////////////////
    // Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name") //
    private String name; //

    @Column(name = "address_line_1")
    private String address_line_1;

    @Column(name = "address_line_2")
    private String address_line_2;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip_code")
    private String zip_code;

    @Column(name = "neighborhood")
    private String neighborhood;

    @Column(name = "average_rating")
    private Float average_rating;

    @Column(name = "status")
    private String status;

    @Column(name = "status_note")
    private String status_note;

    @Column(name = "created_at")
    private Timestamp created_at;

    @Column(name = "updated_at")
    private Timestamp updated_at;

    ///////////////////////////////////////////////////////////////////////////
    // Getter Methods
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress_line_1() {
        return address_line_1;
    }

    public String getAddress_line_2() {
        return address_line_2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip_code() {
        return zip_code;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public Float getAverage_rating() {
        return average_rating;
    }

    public String getStatus() {
        return status;
    }

    public String getStatus_note() {
        return status_note;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Setter Methods
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress_line_1(String address_line_1) {
        this.address_line_1 = address_line_1;
    }

    public void setAddress_line_2(String address_line_2) {
        this.address_line_2 = address_line_2;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public void setAverage_rating(Float average_rating) {
        this.average_rating = average_rating;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStatus_note(String status_note) {
        this.status_note = status_note;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
