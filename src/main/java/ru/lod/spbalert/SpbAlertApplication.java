package ru.lod.spbalert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"ru.lod.spbalert"})
public class SpbAlertApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpbAlertApplication.class, args);
    }

}
