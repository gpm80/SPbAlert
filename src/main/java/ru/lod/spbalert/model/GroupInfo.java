package ru.lod.spbalert.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Группа значений данных
 */
public class GroupInfo {

    private String geoHash;
    private Set<String> types;
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

    public int getActual() {
        //TODO сделать прогрессивную актуальность!!!
        return getAlertList().size();
    }
}
