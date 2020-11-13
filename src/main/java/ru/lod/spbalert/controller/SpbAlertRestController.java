package ru.lod.spbalert.controller;

import java.io.InputStream;
import java.util.Map;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.lod.spbalert.dao.Response;
import ru.lod.spbalert.service.AlertService;

@RestController
@RequestMapping("rest")
public class SpbAlertRestController {

    @Autowired
    private AlertService alertService;

    @GetMapping(value = "/alert", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response alert(@RequestParam Map<String, String> params) {
        return alertService.getResponse(params);
    }

    @PostMapping(value="/upload", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response upload(@FormDataParam("file") InputStream input) {
        return alertService.getResponse(input);
    }

}
