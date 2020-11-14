package ru.lod.spbalert.dao;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "processed-alert")
public class ProcessedAlertDocument extends AlertDocument {

    private Integer index;
    private String comment;

    @Override
    public String toString() {
        return "ProcessedAlertDocument{" +
            "id='" + id + '\'' +
            ", category='" + category + '\'' +
            ", spbAlert=" + spbAlert +
            ", index=" + index +
            ", comment='" + comment + '\'' +
            '}';
    }
}
