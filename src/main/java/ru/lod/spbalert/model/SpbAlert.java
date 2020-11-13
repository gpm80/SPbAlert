package ru.lod.spbalert.model;

import java.util.Date;

/**
 */
public class SpbAlert extends GpsPoint {

    private Date date;
    private String type;
    //TODO Общие поля сообщения


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
