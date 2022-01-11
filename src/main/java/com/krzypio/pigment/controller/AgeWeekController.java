package com.krzypio.pigment.controller;

import com.krzypio.pigment.entity.AgeWeek;
import com.krzypio.pigment.entity.Treatment;
import com.krzypio.pigment.exception.other.AgeWeekNotFoundException;
import com.krzypio.pigment.exception.other.TreatmentNotFoundException;
import com.krzypio.pigment.repository.AgeWeekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class AgeWeekController {

    @Autowired
    AgeWeekRepository ageWeekRepository;

    @GetMapping("/ageWeeks")
    public List<AgeWeek> getAgeWeeks(){
        return ageWeekRepository.findAll();
    }

    @GetMapping("/ageWeeks/{id}")
    public AgeWeek retrieveAgeWeekById(@PathVariable long id){
        Optional<AgeWeek> retrievedAgeWeek = ageWeekRepository.findById(id);
        retrievedAgeWeek.orElseThrow(() -> new AgeWeekNotFoundException("AgeWeek with id " + id + " not found."));
        return retrievedAgeWeek.get();
    }

    @PostMapping("/ageWeeks")
    public ResponseEntity createAgeWeek(@Valid @RequestBody AgeWeek ageWeek){
        AgeWeek savedAgeWeek = ageWeekRepository.save(ageWeek);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedAgeWeek.getId())
                .toUri();

        return  ResponseEntity.created(location).body(savedAgeWeek);
    }

    @DeleteMapping("/ageWeeks/{id}")
    public ResponseEntity deleteById(@PathVariable long id){
        Optional<AgeWeek> existingAgeWeek = ageWeekRepository.findById(id);
        existingAgeWeek.orElseThrow(() -> new AgeWeekNotFoundException("AgeWeek with id " + id + " not found."));
        ageWeekRepository.deleteById(id);
        return ResponseEntity.ok("AgeWeek with id " + id + " deleted.");
    }
}
