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

    public Double getActual() {
        //TODO сделать прогрессивную актуальность!!!
        //по времени считать чем меньше время тем выше вес
        final List<SpbAlert> sorted = getAlertList().stream().sorted((o1, o2) ->
            o1.getDate().after(o2.getDate()) ? 1 : (o1.getDate() == o2.getDate() ? 0 : -1))
            .collect(Collectors.toList());
        final Date current = sorted.get(sorted.size() - 1).getDate();
        final List<Double> doubles = sorted.stream()
            .map(spbAlert -> current.getTime() - spbAlert.getDate().getTime())
            .map(Long::doubleValue)
            .collect(Collectors.toList());
        final List<Double> alerts = new ArrayList<>();
        // группировать уведомления по 30 сек
        for (double d = 0 ; d < doubles.get(0) + 30000 ; d = d + 30000) {
            final double time = d;
            final double res = doubles.stream()
                .filter(aDouble -> {
                    return aDouble <= time && aDouble + 30000 > time;
                })
                .count();
            alerts.add(res);
        }
        return derivative(alerts);
    }

    private Double derivative(List<Double> alerts) {
        // У нового уведомления наивысший рейтинг
        if (alerts.size() == 1) {
            return 2d;
        }
        if (alerts.size() == 2) {
            double v = alerts.get(1) - alerts.get(0);
            return (v / Math.sqrt(1 + v * v)) + 1;
        }
        if (alerts.size() > 2) {
            //TODO уменьшающие коэффициенты ранних событий при увеличении времени
            double result = 0d;
            for(int i = 1; i <= alerts.size() - 2; i++) {
                double range = alerts.get(i + 1) - alerts.get(i - 1);
                // При отсутствии
                if (range != 0) {
                    result = result + (range/Math.sqrt(4+range*range)) + 1;
                } else {
                    result++;
                }
            }
            return result / (alerts.size() - 2);
        }
        return 0d;
    }
}
