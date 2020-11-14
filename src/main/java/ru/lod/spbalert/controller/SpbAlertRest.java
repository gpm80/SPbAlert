package ru.lod.spbalert.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.lod.spbalert.dao.ProcessedAlertDocument;
import ru.lod.spbalert.model.SpbAlert;
import ru.lod.spbalert.service.AlertService;
import ru.lod.spbalert.support.ExcelParser;

@RestController
@RequestMapping("/alert")
public class SpbAlertRest {

    @Autowired
    private AlertService alertService;

    @PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean alert(@RequestParam Map<String, String> params) {
        alertService.save(params);
        return true;
    }

    @GetMapping(path = "/test/{count}")
    public List<SpbAlert> testSave(@PathVariable("count") Integer count){
        return alertService.testSave(count);
    }

    @PostMapping(value="/upload", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Boolean upload(@RequestParam("file") MultipartFile input) throws IOException {
        alertService.save(ExcelParser.parse(input.getInputStream()));
        return true;
    }

    @GetMapping(path = "/getAlert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProcessedAlertDocument getAlert(@RequestParam Map<String, String> param) {
        return null;
    }

    @GetMapping(path = "/getAlerts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProcessedAlertDocument> getAlerts() {
        return null;
    }



}
