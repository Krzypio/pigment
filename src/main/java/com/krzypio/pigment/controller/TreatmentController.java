package com.krzypio.pigment.controller;

import com.krzypio.pigment.entity.ProductionBatch;
import com.krzypio.pigment.entity.Treatment;
import com.krzypio.pigment.exception.other.ProductionBatchNotFoundException;
import com.krzypio.pigment.exception.other.TreatmentNotFoundException;
import com.krzypio.pigment.repository.TreatmentRepository;
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
import java.util.stream.Collectors;

@RestController
public class TreatmentController {

    @Autowired
    TreatmentRepository treatmentRepository;

    @GetMapping("/treatments")
    public List<Treatment> getTreatments(){
        return treatmentRepository.findAll();
    }

    @GetMapping("/treatmentsStandard")
    public List<Treatment> getTreatmentsStandard(){
        List<Treatment> allTreatments = treatmentRepository.findAll();
        return allTreatments.stream().filter(t -> t.getCategory() == Treatment.Category.STANDARD).collect(Collectors.toList());
    }

    @GetMapping("/treatmentsDrug")
    public List<Treatment> getTreatmentsDrug(){
        List<Treatment> allTreatments = treatmentRepository.findAll();
        return allTreatments.stream().filter(t -> t.getCategory() == Treatment.Category.DRUG).collect(Collectors.toList());
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

    /**
     * This method can change Category, Description and Name. AgeWeeks are not used, but must be added (anything). To update AgeWeek - treatment connection use AgeWeek/{id}  (replaceAgeWeek).
     * @param newTreatment
     * @param id
     * @return
     */
    @PutMapping("/treatments/{id}")
    public ResponseEntity replaceTreatment(@Valid @RequestBody Treatment newTreatment, @PathVariable long id){
        return treatmentRepository.findById(id)
                .map(t -> {
                    t.setCategory(newTreatment.getCategory());
                    t.setDescription(newTreatment.getDescription());
                    t.setName(newTreatment.getName());
                    Treatment savedTreatment = treatmentRepository.save(t);

                    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}").buildAndExpand(savedTreatment.getId())
                            .toUri();

                    HttpHeaders headers = new HttpHeaders();
                    headers.setLocation(location);
                    ResponseEntity responseEntity = new ResponseEntity<Treatment>(savedTreatment, headers, HttpStatus.CREATED);

                    return  responseEntity;
                }).orElseThrow(() -> new TreatmentNotFoundException("Treatment with id " + id + " not found."));
    }
}
