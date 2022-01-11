package com.krzypio.pigment.entity;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AgeWeek {
    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    @PositiveOrZero
    private int weekOfLive;

    @JsonIgnore
    @ManyToMany (fetch = FetchType.LAZY)
    private Set<Treatment> treatments;

    private AgeWeek() {
    }

    public AgeWeek(int weekOfLive) {
        this.weekOfLive = weekOfLive;
    }

    public long getId() {
        return id;
    }

    public int getWeekOfLive() {
        return weekOfLive;
    }

    public void setWeekOfLive(int weekOfLive) {
        this.weekOfLive = weekOfLive;
    }

    public Set<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(Set<Treatment> treatments) {
        this.treatments = treatments;
    }

    @Override
    public String toString() {
        return "AgeWeek{" +
                "id=" + id +
                ", weekOfLive=" + weekOfLive +
                '}';
    }
}
