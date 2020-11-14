package ru.lod.spbalert.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by Petr Gusarov
 */
@Document(indexName = DboIndex.ALERT_TYPES)
public class TypeAlertDocument {

    @Id
    private String id;

    @Field(index = false)
    private String type;

    @Field(type = FieldType.Keyword)
    private String keywords;

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

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
