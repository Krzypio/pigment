package com.krzypio.pigment.controller;

import com.krzypio.pigment.entity.AgeWeek;
import com.krzypio.pigment.entity.Treatment;
import com.krzypio.pigment.exception.other.AgeWeekNotFoundException;
import com.krzypio.pigment.exception.other.TreatmentNotFoundException;
import com.krzypio.pigment.repository.AgeWeekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    /**
     * This methods can change WeekOfLive and Treatments. Use this method to connect AgeWeek with Treatment.
     * @param newAgeWeek
     * @param id
     * @return
     */
    @PutMapping("/ageWeeks/{id}")
    public ResponseEntity replaceAgeWeek(@Valid @RequestBody AgeWeek newAgeWeek, @PathVariable long id){
        return ageWeekRepository.findById(id)
                .map(aw -> {
                    aw.setWeekOfLive(newAgeWeek.getWeekOfLive());
                    aw.setTreatments(newAgeWeek.getTreatments());
                    AgeWeek savedAgeWeek = ageWeekRepository.save(aw);

                    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}").buildAndExpand(savedAgeWeek.getId())
                            .toUri();

                    HttpHeaders headers = new HttpHeaders();
                    headers.setLocation(location);
                    ResponseEntity responseEntity = new ResponseEntity<AgeWeek>(savedAgeWeek, headers, HttpStatus.CREATED);

                    return  responseEntity;
                }).orElseThrow(() -> new AgeWeekNotFoundException("AgeWeek with id " + id + " not found."));
    }
}
