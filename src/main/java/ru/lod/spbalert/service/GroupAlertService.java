package ru.lod.spbalert.service;

import org.elasticsearch.geometry.utils.Geohash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lod.spbalert.dao.AlertDocument;
import ru.lod.spbalert.dao.GroupAlertDocument;
import ru.lod.spbalert.model.SpbAlert;
import ru.lod.spbalert.repository.AlertRepository;
import ru.lod.spbalert.repository.GroupAlertRepository;

/**
 * Created by Petr Gusarov
 */
@Service
public class GroupAlertService {

    private static Logger logger = LoggerFactory.getLogger(GroupAlertService.class);
    @Autowired
    private GroupAlertRepository groupAlertRepository;
    @Autowired
    private ClassifierService classifierService;
    @Autowired
    private AlertRepository alertRepository;

    /**
     * атомарная обработка каждой поступающей записи.
     *
     * @param alertDocument исходные данные
     * @return
     */
    public boolean process(AlertDocument alertDocument) {
        try {
            final String classify = classifierService.classify(alertDocument.getCategory());
            final GroupAlertDocument groupAlert = new GroupAlertDocument();
            groupAlert.setType(classify);
            final SpbAlert spbAlert = alertDocument.getSpbAlert();
            groupAlert.setSpbAlert(spbAlert);
            groupAlert.setGeoHash(Geohash.stringEncode(spbAlert.getLatitude(), spbAlert.getLongitude()));
            final GroupAlertDocument save = groupAlertRepository.save(groupAlert);
            return save != null;
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            return false;
        }
    }


}
