package com.ratemyapartment.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "") // TODO: insert table name
public class EntityTemplate { // TODO: change class name
    ///////////////////////////////////////////////////////////////////////////
    // Default Constructor
    public EntityTemplate() {} // TODO: change name
    // Json Constructor
    public EntityTemplate(@JsonProperty("example") String example) { // TODO: insert fields with json property, change name
        this.example = example; // TODO: change name
        this.created_at = new Timestamp(System.currentTimeMillis());
        this.updated_at = new Timestamp(System.currentTimeMillis());
    }

    ///////////////////////////////////////////////////////////////////////////
    // Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "") // TODO: insert column name
    private String example; // TODO: change name

    @Column(name = "created_at")
    private Timestamp created_at;

    @Column(name = "updated_at")
    private Timestamp updated_at;

    ///////////////////////////////////////////////////////////////////////////
    // Getter Methods
    public Long getId() {
        return id;
    }

    public String getExample() {
        return example;
    } // TODO: change name

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

    public void setExample(String example) { // TODO: change name
        this.example = example; // TODO: change name
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
