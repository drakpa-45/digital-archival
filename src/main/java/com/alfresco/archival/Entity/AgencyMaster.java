package com.alfresco.archival.Entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "arc_agency_master")
public class AgencyMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "name_description")
    private String nameDescription;

    // Constructor
    public AgencyMaster() {
    }

    public AgencyMaster(String name, String nameDescription) {
        this.name = name;
        this.nameDescription = nameDescription;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameDescription() {
        return nameDescription;
    }

    public void setNameDescription(String nameDescription) {
        this.nameDescription = nameDescription;
    }

    @Override
    public String toString() {
        return "AgencyMaster{id=" + id + ", name='" + name + "', nameDescription='" + nameDescription + "'}";
    }
}
