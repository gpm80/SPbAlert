package ru.lod.spbalert.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lod.spbalert.support.ExcelParser;

/**
 * Created by Petr Gusarov
 */
@Service
public class FillingService {

    private static Logger logger = LoggerFactory.getLogger(FillingService.class);
    @Autowired
    private AlertService alertService;

    @PostConstruct
    public void init() {
        if (alertService.isEmpty()) {
            Executors.newSingleThreadExecutor().submit(() -> {
                logger.info("fill starting...");
                File file = new File("src/test/resources/dataset.xlsx");
                try (InputStream inputStream = new FileInputStream(file)) {
                    alertService.save(ExcelParser.parse(inputStream));
                    logger.info("fill success");
                } catch (IOException e) {
                    logger.error("error fill", e);
                }
            });
        } else {
            logger.info("fill not required");
        }
    }


}
