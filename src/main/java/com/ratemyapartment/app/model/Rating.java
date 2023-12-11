package com.ratemyapartment.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Rating")
public class Rating {
    ///////////////////////////////////////////////////////////////////////////
    // Default Constructor
    public Rating() {}
    // Json Constructor
    public Rating(@JsonProperty("apartment_id") Long apartment_id,
                  @JsonProperty("title") String title,
                  @JsonProperty("description") String description,
                  @JsonProperty("value") int value,
                  @JsonProperty("would_recommend") String would_recommend,
                  @JsonProperty("status") String status,
                  @JsonProperty("status_note") String status_note) {
        this.apartment_id = apartment_id;
        this.title = title;
        this.description = description;
        this.value = value;
        this.would_recommend = would_recommend;
        this.status = Objects.requireNonNullElse(status, "Submitted");
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

    @Column(name = "apartment_id")
    private Long apartment_id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "value")
    private int value;

    @Column(name = "would_recommend")
    private String would_recommend;

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

    public Long getApartment_id() {
        return apartment_id;
    }
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }

    public String getWould_recommend() {
        return would_recommend;
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

    public void setApartment_id(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setWould_recommend(String would_recommend) {
        this.would_recommend = would_recommend;
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
