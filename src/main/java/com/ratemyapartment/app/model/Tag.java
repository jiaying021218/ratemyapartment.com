package com.ratemyapartment.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "Tag")
public class Tag {
    ///////////////////////////////////////////////////////////////////////////
    // Default Constructor
    public Tag() {}
    // Json Constructor
    public Tag(@JsonProperty("name") String name,
               @JsonProperty("positive_negative") String positive_negative) {
        this.name = name;
        this.positive_negative = positive_negative;
        this.created_at = new Timestamp(System.currentTimeMillis());
        this.updated_at = new Timestamp(System.currentTimeMillis());
    }

    ///////////////////////////////////////////////////////////////////////////
    // Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "positive_negative")
    private String positive_negative;

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

    public String getPositive_Negative() {
        return positive_negative;
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

    public void setPositive_negative(String positive_negative) {
        this.positive_negative = positive_negative;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
