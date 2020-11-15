package ru.lod.spbalert.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Группа значений данных
 */
public class GroupInfo {

    private String geoHash;
    private Set<String> types;
    private String district;
    private int countAlert;
    private double actual;
    private List<SpbAlert> alertList;

    public String getGeoHash() {
        return geoHash;
    }

    public void setGeoHash(String geoHash) {
        this.geoHash = geoHash;
    }

    public Set<String> getTypes() {
        if (types == null) {
            types = new HashSet<>();
        }
        return types;
    }

    public void setTypes(Set<String> types) {
        this.types = types;
    }

    public List<SpbAlert> getAlertList() {
        if (alertList == null) {
            alertList = new ArrayList<>();
        }
        return alertList;
    }

    public void setAlertList(List<SpbAlert> alertList) {
        this.alertList = alertList;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getCountAlert() {
        return countAlert;
    }

    public void setCountAlert(int countAlert) {
        this.countAlert = countAlert;
    }

    public double getActual() {
        return actual;
    }

    public void setActual(double actual) {
        this.actual = actual;
    }

    @Override
    public String toString() {
        return "GroupInfo{" +
            "geoHash='" + geoHash + '\'' +
            ", types=" + types +
            ", district='" + district + '\'' +
            ", countAlert=" + countAlert +
            ", actual=" + actual +
            '}';
    }
}
