package ru.lod.spbalert.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import ru.lod.spbalert.model.SpbAlert;

/**
 * Сервис приоритетной логики.
 */
@Service
public class DecisionMakingService {

    /**
     * Расчетная модель индекса приоритета задачи.
     *
     * @param spbAlerts
     * @return
     */
    public Double making(List<SpbAlert> spbAlerts) {
        final List<SpbAlert> sorted = spbAlerts.stream().sorted((o1, o2) ->
            o1.getDate().after(o2.getDate()) ? 1 : (o1.getDate() == o2.getDate() ? 0 : -1))
            .collect(Collectors.toList());
        final Date current = sorted.get(sorted.size() - 1).getDate();
        final List<Double> doubles = sorted.stream()
            .map(spbAlert -> current.getTime() - spbAlert.getDate().getTime())
            .map(Long::doubleValue)
            .collect(Collectors.toList());
        final List<Double> alerts = new ArrayList<>();
        // группировать уведомления по 30 сек
        for (double d = 0; d < doubles.get(0) + 30000; d = d + 30000) {
            final double time = d;
            final double res = doubles.stream()
                .filter(aDouble -> {
                    return aDouble <= time && aDouble + 30000 > time;
                })
                .count();
            alerts.add(res);
        }
        return BigDecimal.valueOf(derivative(alerts))
            .setScale(4, BigDecimal.ROUND_HALF_UP)
            .doubleValue();
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
            for (int i = 1; i <= alerts.size() - 2; i++) {
                double range = alerts.get(i + 1) - alerts.get(i - 1);
                // При отсутствии
                if (range != 0) {
                    result = result + (range / Math.sqrt(4 + range * range)) + 1;
                } else {
                    result++;
                }
            }
            return result / (alerts.size() - 2);
        }
        return 0d;
    }


}
