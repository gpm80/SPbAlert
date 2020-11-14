package ru.lod.spbalert.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ru.lod.spbalert.model.SpbAlert;
import ru.lod.spbalert.service.AlertService;
import ru.lod.spbalert.support.ExcelParser;

@RestController
@RequestMapping("/alert")
public class SpbAlertRest {

    private static Logger logger = LoggerFactory.getLogger(SpbAlertRest.class);
    @Autowired
    private AlertService alertService;

    @Scheduled(initialDelay = 1000 * 10, fixedDelay = Long.MAX_VALUE)
    public void postConstruct() throws IOException {
        if (alertService.count() == 0) {
            logger.info("load data sets...");
            final File file = new File("src/main/resources/dataset.xlsx");
            final RestTemplate restTemplate = new RestTemplate();
            final String url = "http://localhost:8080/alert/upload";
            final HttpMethod requestMethod = HttpMethod.POST;

            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            final MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
            final ContentDisposition contentDisposition = ContentDisposition
                .builder("form-data")
                .name("file")
                .filename(file.getName())
                .build();

            fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
            final HttpEntity<byte[]> fileEntity = new HttpEntity<>(Files.readAllBytes(file.toPath()), fileMap);

            final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", fileEntity);

            final HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            restTemplate.exchange(url, requestMethod, requestEntity, String.class);
        }
    }

    @PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean alert(@RequestParam Map<String, String> params) {
        alertService.save(params);
        return true;
    }

    @GetMapping(path = "/test/{count}")
    public List<SpbAlert> testSave(@PathVariable("count") Integer count) {
        return alertService.testSave(count);
    }

    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Boolean upload(@RequestParam("file") MultipartFile input) throws IOException {
        alertService.save(ExcelParser.parse(input.getInputStream()));
        return true;
    }
}
