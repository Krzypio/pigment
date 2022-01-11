package com.krzypio.pigment.runner;

import com.krzypio.pigment.entity.ProductionBatch;
import com.krzypio.pigment.entity.User;
import com.krzypio.pigment.repository.ProductionBatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductionBatchCLR implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ProductionBatchCLR.class);

    @Autowired
    private ProductionBatchRepository productionBatchRepository;

    @Override
    public void run(String... args) throws Exception {
        ProductionBatch p1 = new ProductionBatch(new GregorianCalendar(2022, 0, 3).getTime());
        ProductionBatch p2 = new ProductionBatch(new GregorianCalendar(2022, 0, 9).getTime());
        ProductionBatch p3 = new ProductionBatch(new GregorianCalendar(2022, 0, 10).getTime());
        List<ProductionBatch> productionBatches = productionBatchRepository.saveAll(Arrays.asList(p1, p2, p3));
        log.info("New ProductionBatch are created: " + productionBatches);
    }
}
