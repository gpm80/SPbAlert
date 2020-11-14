package ru.lod.spbalert.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.lod.spbalert.dao.AlertDocument;
import ru.lod.spbalert.model.SpbAlert;
import ru.lod.spbalert.repository.AlertRepository;

@Service
public class AlertService {

    private static Logger logger = LoggerFactory.getLogger(AlertService.class);
    @Autowired
    private AlertRepository alertRepository;
    @Autowired
    private GroupAlertService groupAlertService;

    public AlertDocument save(Map<String, String> params) {
        return save(AlertDocument.of(params));
    }

    public AlertDocument save(AlertDocument alertDocument) {
        return alertRepository.save(alertDocument);
    }

    public void save(List<AlertDocument> alertDocuments) {
        alertDocuments.stream().forEach(alertDocument -> {
            final AlertDocument saved = alertRepository.save(alertDocument);
            final boolean process = groupAlertService.process(saved);
            if (!process) {
                logger.warn("dont save group");
            }
        });
    }

    public boolean isEmpty() {
        return !alertRepository.findAll(PageRequest.of(0, 10)).hasContent();
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
