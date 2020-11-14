package ru.lod.spbalert.dao;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import ru.lod.spbalert.model.SpbAlert;

/**
 * Документ обработанной информации.
 */
@Document(indexName = DboIndex.ALERT_GROUP)
public class GroupAlertDocument {

    @Id
    private String id;

    @Field(index = false)
    private String type;

    @Field(index = false)
    private String geoHash;

    @Field(index = false)
    private Date timePoint;

    @Field(type = FieldType.Nested, includeInParent = true)
    private SpbAlert spbAlert;

    public static GroupAlertDocument of(SpbAlert spbAlert) {
        final GroupAlertDocument document = new GroupAlertDocument();
        document.setSpbAlert(spbAlert);
        document.setTimePoint(spbAlert.getDate());
        return document;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGeoHash() {
        return geoHash;
    }

    public void setGeoHash(String geoHash) {
        this.geoHash = geoHash;
    }

    public Date getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(Date timePoint) {
        this.timePoint = timePoint;
    }

    public SpbAlert getSpbAlert() {
        return spbAlert;
    }

    public void setSpbAlert(SpbAlert spbAlert) {
        this.spbAlert = spbAlert;
    }

    @Override
    public String toString() {
        return "GroupAlertDocument{" +
            "id='" + id + '\'' +
            ", type='" + type + '\'' +
            ", geoHash='" + geoHash + '\'' +
            ", spbAlert=" + spbAlert +
            '}';
    }
}
