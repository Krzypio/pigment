package com.krzypio.pigment.runner;

import com.krzypio.pigment.entity.AgeWeek;
import com.krzypio.pigment.entity.Treatment;
import com.krzypio.pigment.repository.AgeWeekRepository;
import com.krzypio.pigment.repository.TreatmentRepository;
import org.assertj.core.util.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class AgeWeek_Treatment implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(AgeWeek_Treatment.class);

    @Autowired
    private AgeWeekRepository ageWeekRepository;
    @Autowired
    private TreatmentRepository treatmentRepository;

    @Override
    public void run(String... args) throws Exception {
        List<AgeWeek> ageWeeks = createAgeWeeks();
        List<Treatment> treatments = createTreatments();
        connectAgeWeeksAndTreatments(ageWeeks, treatments);
    }

    private List<AgeWeek> createAgeWeeks(){
        List<AgeWeek> ageWeeks = new ArrayList<>();
        for(int i=0; i<26; i++){
            AgeWeek ageWeek = new AgeWeek(i);
            ageWeeks.add(ageWeek);
        }
        List<AgeWeek> addedAgeWeeks = ageWeekRepository.saveAll(ageWeeks);
        log.info("New AgeWeek are created: " + ageWeeks);
        return addedAgeWeeks;
    }

    private List<Treatment> createTreatments(){
        Treatment claws = new Treatment("claws", "Cutting a claws");
        Treatment tails = new Treatment("tails", "Cutting a tails");
        Treatment ironInjection = new Treatment("ironIjection", "Intramuscular injection of iron");
        List<Treatment> addedTreatments = treatmentRepository.saveAll(Arrays.asList(claws, tails, ironInjection));
        log.info("New Treatments are created: " + addedTreatments);
        return addedTreatments;
    }

    private void connectAgeWeeksAndTreatments(List<AgeWeek> ageWeeks, List<Treatment> treatments){
        for(AgeWeek ageWeek : ageWeeks){
            ageWeek.setTreatments(Sets.newHashSet(treatments));
            ageWeekRepository.save(ageWeek);
        }
        List<AgeWeek> addedAgeWeeksAndTreatments = ageWeekRepository.saveAll(ageWeeks);
        log.info("New connections AgeWeek - Treatments are created: " + addedAgeWeeksAndTreatments);
    }
}
