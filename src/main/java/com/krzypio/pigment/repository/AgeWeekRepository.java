package com.krzypio.pigment.repository;

import com.krzypio.pigment.entity.AgeWeek;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgeWeekRepository extends JpaRepository<AgeWeek, Long> {
    Optional<AgeWeek> findByWeekOfLive(int id);
}
