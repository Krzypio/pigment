package com.krzypio.pigment.controller;

import com.krzypio.pigment.entity.Treatment;
import com.krzypio.pigment.exception.other.TreatmentNotFoundException;
import com.krzypio.pigment.repository.TreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class TreatmentController {

    @Autowired
    TreatmentRepository treatmentRepository;

    @GetMapping("/treatments")
    public List<Treatment> getTreatments(){
        return treatmentRepository.findAll();
    }

    @GetMapping("/treatments/{id}")
    public Treatment retrieveUserById(@PathVariable long id){
        Optional<Treatment> retrievedTreatment = treatmentRepository.findById(id);
        retrievedTreatment.orElseThrow(() -> new TreatmentNotFoundException("Treatment with id " + id + " not found."));
        return retrievedTreatment.get();
    }

    @PostMapping("/treatments")
    public ResponseEntity createTreatment(@Valid @RequestBody Treatment treatment){
        Treatment savedTreatment = treatmentRepository.save(treatment);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedTreatment.getId())
                .toUri();

        return  ResponseEntity.created(location).body(savedTreatment);
    }

    @DeleteMapping("/treatments/{id}")
    public ResponseEntity deleteById(@PathVariable long id){
        Optional<Treatment> existingUser = treatmentRepository.findById(id);
        existingUser.orElseThrow(() -> new TreatmentNotFoundException("Treatment with id " + id + " not found."));
        treatmentRepository.deleteById(id);
        return ResponseEntity.ok("Treatment with id " + id + " deleted.");
    }
}
