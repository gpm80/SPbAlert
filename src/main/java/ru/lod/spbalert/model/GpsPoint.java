package ru.lod.spbalert.model;

/**
 * Created by Petr Gusarov
 */
public class GpsPoint {

    /**
     * Широта
     */
    private double latitude;
    /**
     * Долгота
     */
    private double longitude;

    /**
     * Билдер
     *
     * @param latitude  широта
     * @param longitude долгота
     * @return
     */
    public static GpsPoint of(double latitude, double longitude) {
        GpsPoint point = new GpsPoint();
        point.setLatitude(latitude);
        point.setLongitude(longitude);
        return point;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
