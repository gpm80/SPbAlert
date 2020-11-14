package ru.lod.spbalert.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.lod.spbalert.model.SpbAlert;
import ru.lod.spbalert.service.AlertService;

@RestController
@RequestMapping("/alert")
public class SpbAlertRest {

    @Autowired
    private AlertService alertService;

    @PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean alert(@RequestParam SpbAlert spbAlert) {
        alertService.save(spbAlert);
        return true;
    }

    @GetMapping(path = "/test/{count}")
    public List<SpbAlert> testSave(@PathVariable("count") Integer count){
        return alertService.testSave(count);
    }

//    @PostMapping(value="/upload", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public Response upload(@FormDataParam("file") InputStream input) {
//        return alertService.getResponse(input);
//    }

}
