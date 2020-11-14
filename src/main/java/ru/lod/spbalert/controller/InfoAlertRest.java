package ru.lod.spbalert.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private InfoAlertService infoAlertService;

    @PostMapping(path = "/get")
    public List<GroupInfo> getInfo(@RequestBody RequestInfo requestInfo) {
        return infoAlertService.find(requestInfo);
    }

}
