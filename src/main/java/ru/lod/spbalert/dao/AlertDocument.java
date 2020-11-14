package ru.lod.spbalert.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import ru.lod.spbalert.model.SpbAlert;

/**
 * Входящие сообщения (без обработки)
 */
@Document(indexName = "alert-common")
public class AlertDocument {

    private static final Logger logger = LoggerFactory.getLogger(AlertDocument.class);

    @Id
    protected String id;

    @Field(index = false)
    protected String category;

    @Field(type = FieldType.Nested, includeInParent = true)
    protected SpbAlert spbAlert;

    public AlertDocument() {
    }

    public static AlertDocument of(SpbAlert spbAlert) {
        final AlertDocument alertDocument = new AlertDocument();
        alertDocument.setSpbAlert(spbAlert);
        //TODO alertCommon.setSearch(...);
        return alertDocument;
    }

    public static AlertDocument of(Map<String, String> params) {
        final AlertDocument alertDocument = new AlertDocument();
        alertDocument.setCategory(params.getOrDefault("category", null));
        final SpbAlert spbAlert = new SpbAlert();
        spbAlert.setDistrict(params.getOrDefault("district", null));
        spbAlert.setLongitude(Double.parseDouble(params.getOrDefault("longitude", "0")));
        spbAlert.setLatitude(Double.parseDouble(params.getOrDefault("latitude", "0")));
        spbAlert.setBuildingEac(Double.parseDouble(params.getOrDefault("buildingEac", "0")));
        spbAlert.setAddressEac(Double.parseDouble(params.getOrDefault("addressEac", "0")));
        if (params.containsKey("date") ) {
            final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy hh:mm");
            try {
                spbAlert.setDate(formatter.parse(params.get("date")));
            } catch (ParseException e) {
                logger.debug(e.getMessage(), e);
            }
        }
        alertDocument.setSpbAlert(spbAlert);
        return alertDocument;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public SpbAlert getSpbAlert() {
        return spbAlert;
    }

    public void setSpbAlert(SpbAlert spbAlert) {
        this.spbAlert = spbAlert;
    }

    @Override
    public String toString() {
        return "AlertCommon{" +
            "id='" + id + '\'' +
            ", category='" + category + '\'' +
            ", spbAlert=" + spbAlert +
            '}';
    }
}
