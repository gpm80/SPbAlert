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
        //по времени считать чем меньше время тем выше вес
        final Date current = new Date(System.currentTimeMillis());
        final List<SpbAlert> sorted = getAlertList().stream().sorted((o1, o2) ->
            o1.getDate().after(o2.getDate()) ? 1 : (o1.getDate() == o2.getDate() ? 0 : -1)).collect(Collectors.toList());

        return getAlertList().size();
    }
}
