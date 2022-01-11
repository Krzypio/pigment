package com.krzypio.pigment.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Entity
public class ProductionBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private Date birthPeriodStart;
    private Date birthPeriodEnd;
    private Date saleDate;

    private ProductionBatch() {
    }

    public ProductionBatch(Date birthPeriodStart) {
        this.birthPeriodStart = birthPeriodStart;
    }

    public long getId() {
        return id;
    }

    public Date getBirthPeriodStart() {
        return birthPeriodStart;
    }

    public void setBirthPeriodStart(Date birthPeriodStart) {
        this.birthPeriodStart = birthPeriodStart;
    }

    public Date getBirthPeriodEnd() {
        return birthPeriodEnd;
    }

    public void setBirthPeriodEnd(Date birthPeriodEnd) {
        this.birthPeriodEnd = birthPeriodEnd;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public int calculateWeekOfLife(){
        Date dataFrom = birthPeriodStart;
        int daysBetween = (int) ChronoUnit.DAYS.between(dataFrom.toInstant(), new Date().toInstant());
        return daysBetween/7;
    }

    @Override
    public String toString() {
        return "productionBatch{" +
                "id=" + id +
                ", birthPeriodStart=" + birthPeriodStart +
                ", birthPeriodEnd=" + birthPeriodEnd +
                ", saleDate=" + saleDate +
                ", weekOfLife=" + calculateWeekOfLife() +
                '}';
    }
}
