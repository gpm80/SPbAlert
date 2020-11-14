package ru.lod.spbalert.controller;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lod.spbalert.model.GroupInfo;
import ru.lod.spbalert.model.RequestInfo;
import ru.lod.spbalert.service.InfoAlertService;

/**
 * Created by Petr Gusarov
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/info")
public class InfoAlertRest {

    private static Logger logger = LoggerFactory.getLogger(InfoAlertRest.class);
    @Autowired
    private InfoAlertService infoAlertService;

    @PostMapping(path = "/get", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<GroupInfo> getInfo(@RequestBody RequestInfo requestInfo) {
        logger.info("{}", requestInfo);
        final List<GroupInfo> groupInfos = infoAlertService.find(requestInfo);
        logger.info("size result: {}", groupInfos.size());
        return groupInfos;
    }
}
