package com.krzypio.pigment.entity;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Treatment {
    @Id
    @GeneratedValue
    private long id;

    @Size(min = 2, message = "Name should have at least 2 characters")
    private String name;

    @Size(min = 2, message = "Description should have at least 2 characters")
    private String description;

    @ManyToMany(mappedBy = "treatments", fetch = FetchType.LAZY)
    private Set<AgeWeek> ageWeeks;

    private Treatment() {
    }

    public Treatment(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<AgeWeek> getAgeWeeks() {
        return ageWeeks;
    }

    public void setAgeWeeks(Set<AgeWeek> ageWeeks) {
        this.ageWeeks = ageWeeks;
    }

    @Override
    public String toString() {
        return "Treatment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
