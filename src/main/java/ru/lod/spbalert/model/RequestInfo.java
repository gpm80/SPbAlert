package ru.lod.spbalert.model;

import java.util.Date;

/**
 * Запрос данных по ситуации
 */
public class RequestInfo {

    /**
     * зарброс от 1 до 12
     */
    private int scatter;
    private Date timePoint;
    private int retroHour;

    public RequestInfo() {
    }

    public int getScatter() {
        return scatter;
    }

    public void setScatter(int scatter) {
        this.scatter = scatter;
    }

    public Date getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(Date timePoint) {
        this.timePoint = timePoint;
    }

    public int getRetroHour() {
        return retroHour;
    }

    public void setRetroHour(int retroHour) {
        this.retroHour = retroHour;
    }
}
