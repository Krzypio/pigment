package com.krzypio.pigment.controller;

import com.krzypio.pigment.entity.AgeWeek;
import com.krzypio.pigment.entity.ProductionBatch;
import com.krzypio.pigment.exception.other.AgeWeekNotFoundException;
import com.krzypio.pigment.exception.other.ProductionBatchNotFoundException;
import com.krzypio.pigment.repository.AgeWeekRepository;
import com.krzypio.pigment.repository.ProductionBatchRepository;
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
public class ProductionBatchController {

    @Autowired
    ProductionBatchRepository productionBatchRepository;

    @Autowired
    AgeWeekRepository ageWeekRepository;

    @GetMapping("/productionBatches")
    public List<ProductionBatch> getProductionBatches(){
        return productionBatchRepository.findAll();
    }

    @GetMapping("/productionBatches/{id}")
    public ProductionBatch retrieveProductionBatchById(@PathVariable long id){
        Optional<ProductionBatch> retrievedProductionBatch = productionBatchRepository.findById(id);
        retrievedProductionBatch.orElseThrow(() -> new ProductionBatchNotFoundException("ProductionBatch with id " + id + " not found."));
        return retrievedProductionBatch.get();
    }

    @GetMapping("/productionBatches/{id}/ageWeek")
    public AgeWeek retrieveAgeWeekForProductionBatchById(@PathVariable long id){
        Optional<ProductionBatch> retrievedProductionBatch = productionBatchRepository.findById(id);
        retrievedProductionBatch.orElseThrow(() -> new ProductionBatchNotFoundException("ProductionBatch with id " + id + " not found."));
        int weekOfLife = retrievedProductionBatch.get().calculateWeekOfLife();
        Optional<AgeWeek> retrievedAgeWeek = ageWeekRepository.findByWeekOfLive(weekOfLife);
        retrievedProductionBatch.orElseThrow(() -> new AgeWeekNotFoundException("For ProductionBatch with id " + id + " not found AgeWeek with value " + weekOfLife));
        return retrievedAgeWeek.get();
    }

    @PostMapping("/productionBatches")
    public ResponseEntity createAgeWeek(@Valid @RequestBody ProductionBatch productionBatch){
        ProductionBatch savedProductionBatch = productionBatchRepository.save(productionBatch);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedProductionBatch.getId())
                .toUri();

        return  ResponseEntity.created(location).body(savedProductionBatch);
    }

    @DeleteMapping("/productionBatches/{id}")
    public ResponseEntity deleteById(@PathVariable long id){
        Optional<ProductionBatch> existingProductionBatch = productionBatchRepository.findById(id);
        existingProductionBatch.orElseThrow(() -> new AgeWeekNotFoundException("ProductionBatch with id " + id + " not found."));
        productionBatchRepository.deleteById(id);
        return ResponseEntity.ok("ProductionBatch with id " + id + " deleted.");
    }

    @PutMapping("/productionBatches/{id}")
    public ResponseEntity replaceProductionBatch(@Valid @RequestBody ProductionBatch newProductionBatch, @PathVariable long id){
        return productionBatchRepository.findById(id)
                .map(pb -> {
                    pb.setBirthPeriodStart(newProductionBatch.getBirthPeriodStart());
                    pb.setBirthPeriodEnd(newProductionBatch.getBirthPeriodEnd());
                    pb.setSaleDate(newProductionBatch.getSaleDate());
                    ProductionBatch savedProductionBatch = productionBatchRepository.save(pb);

                    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}").buildAndExpand(savedProductionBatch.getId())
                            .toUri();

                    HttpHeaders headers = new HttpHeaders();
                    headers.setLocation(location);
                    ResponseEntity responseEntity = new ResponseEntity<ProductionBatch>(savedProductionBatch, headers, HttpStatus.CREATED);

                    return  responseEntity;
                }).orElseThrow(() -> new ProductionBatchNotFoundException("ProductionBatch with id " + id + " not found."));
    }
}
