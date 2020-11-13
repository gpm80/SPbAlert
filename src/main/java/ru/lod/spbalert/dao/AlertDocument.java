package ru.lod.spbalert.dao;

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

    @Id
    private String id;

    @Field(index = false)
    private String search;

    @Field(type = FieldType.Nested, includeInParent = true)
    private SpbAlert spbAlert;

    public AlertDocument() {
    }

    public static AlertDocument of(SpbAlert spbAlert) {
        final AlertDocument alertDocument = new AlertDocument();
        alertDocument.setSpbAlert(spbAlert);
        //TODO alertCommon.setSearch(...);
        return alertDocument;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
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
            ", search='" + search + '\'' +
            ", spbAlert=" + spbAlert +
            '}';
    }
}
