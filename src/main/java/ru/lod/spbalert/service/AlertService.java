package ru.lod.spbalert.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lod.spbalert.dao.AlertDocument;
import ru.lod.spbalert.model.SpbAlert;
import ru.lod.spbalert.repository.AlertRepository;

@Service
public class AlertService {

    @Autowired
    private AlertRepository alertRepository;

    public AlertDocument save(SpbAlert spbAlert) {
        return alertRepository.save(AlertDocument.of(spbAlert));
    }

    public List<SpbAlert> testSave(Integer count) {
        //TODO генератор тестовых
        final SpbAlert spbAlert = new SpbAlert();
        spbAlert.setDate(new Date());
        spbAlert.setType("test");
        final AlertDocument saved = alertRepository.save(AlertDocument.of(spbAlert));
        return Collections.singletonList(saved.getSpbAlert());
    }
}
