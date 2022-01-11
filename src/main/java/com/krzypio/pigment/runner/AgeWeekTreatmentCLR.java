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
import java.util.stream.Collectors;

@Component
public class AgeWeekTreatmentCLR implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(AgeWeekTreatmentCLR.class);

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
        for(int i=0; i<4; i++){
            AgeWeek ageWeek = new AgeWeek(i);
            ageWeeks.add(ageWeek);
        }
        List<AgeWeek> addedAgeWeeks = ageWeekRepository.saveAll(ageWeeks);
        log.info("New AgeWeek are created: " + ageWeeks);
        return addedAgeWeeks;
    }

    private List<Treatment> createTreatments(){
        Treatment claws = new Treatment("claws", "Cutting a claws", Treatment.Category.STANDARD);
        Treatment tails = new Treatment("tails", "Cutting a tails", Treatment.Category.STANDARD);
        Treatment ironInjection = new Treatment("ironInjection", "Intramuscular injection of iron", Treatment.Category.DRUG);
        List<Treatment> addedTreatments = treatmentRepository.saveAll(Arrays.asList(claws, tails, ironInjection));
        log.info("New Treatments are created: " + addedTreatments);
        return addedTreatments;
    }

    private void connectAgeWeeksAndTreatments(List<AgeWeek> ageWeeks, List<Treatment> treatments){
        List<AgeWeek> ages0 = ageWeeks.stream().filter(a -> a.getWeekOfLive() == 0).collect(Collectors.toList());
        List<AgeWeek> ages1 = ageWeeks.stream().filter(a -> a.getWeekOfLive() == 1).collect(Collectors.toList());
        List<AgeWeek> ages2 = ageWeeks.stream().filter(a -> a.getWeekOfLive() == 2).collect(Collectors.toList());

        List<Treatment> tr0 = treatments.stream().filter(a -> Arrays.asList("ironInjection").contains(a.getName())).collect(Collectors.toList());
        List<Treatment> tr1 = treatments.stream().filter(a -> Arrays.asList("claws").contains(a.getName())).collect(Collectors.toList());
        List<Treatment> tr2 = treatments.stream().filter(a -> Arrays.asList("tails").contains(a.getName())).collect(Collectors.toList());

        for(AgeWeek ageWeek : ages0){
            ageWeek.setTreatments(Sets.newHashSet(tr0));
        }

        for(AgeWeek ageWeek : ages1){
            ageWeek.setTreatments(Sets.newHashSet(tr1));
        }

        for(AgeWeek ageWeek : ages2){
            ageWeek.setTreatments(Sets.newHashSet(tr2));
        }

        List<AgeWeek> addedAgeWeeksAndTreatments = ageWeekRepository.saveAll(ages0);
        addedAgeWeeksAndTreatments.addAll(ageWeekRepository.saveAll(ages1));
        addedAgeWeeksAndTreatments.addAll(ageWeekRepository.saveAll(ages2));
        log.info("New connections AgeWeek - Treatments are created: " + addedAgeWeeksAndTreatments);
    }
}
