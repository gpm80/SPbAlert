package ru.lod.spbalert.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lod.spbalert.common.DtUtil;
import ru.lod.spbalert.dao.GroupAlertDocument;
import ru.lod.spbalert.model.GroupInfo;
import ru.lod.spbalert.model.RequestInfo;
import ru.lod.spbalert.model.SpbAlert;
import ru.lod.spbalert.repository.GroupAlertRepository;

/**
 * сервис выдачи информации
 */
@Service
public class InfoAlertService {

    private static Logger logger = LoggerFactory.getLogger(InfoAlertService.class);
    @Autowired
    private GroupAlertRepository groupAlertRepository;
    @Autowired
    private DecisionMakingService decisionMakingService;

    public List<GroupInfo> find(RequestInfo requestInfo) {
        final Date end = requestInfo.getTimePoint();
        final LocalDateTime localDateTime = DtUtil.toDateTime(end);
        final Date start = DtUtil.toDate(localDateTime.minusHours(requestInfo.getRetroHour()));
        final Stream<GroupAlertDocument> stream =
            groupAlertRepository.findBySpbAlert_DateBetween(start.getTime(), end.getTime());
        Map<String, GroupInfo> groupMap = new HashMap<>();
        // группируем
        stream.forEach(doc -> {
            final String geoHash = StringUtils.substring(doc.getGeoHash(), 0, requestInfo.getScatter());
            groupMap.compute(geoHash, (hash, groupInfo) -> {
                if (groupInfo == null) {
                    groupInfo = new GroupInfo();
                }
                groupInfo.setGeoHash(hash);
                groupInfo.getAlertList().add(doc.getSpbAlert());
                groupInfo.getTypes().add(doc.getType());
                return groupInfo;
            });
        });
        // Сортировка групп событий по важности
        final ArrayList<GroupInfo> result = new ArrayList<>(groupMap.values());
        result.forEach(groupInfo -> {
            // входящие в группу районы города
            groupInfo.setDistrict(groupInfo.getAlertList().stream()
                .map(SpbAlert::getDistrict)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toSet()).stream().collect(Collectors.joining(", "))
            );
            groupInfo.setCountAlert(groupInfo.getAlertList().size());
            final Double making = decisionMakingService.making(groupInfo.getAlertList());
            if (making != null) {
                groupInfo.setActual(making);
            }
            // Зачистка исходной аталитики
            groupInfo.getAlertList().clear();
        });
        // Сортировка
        result.sort(new GroupInfoComparator());
        logger.info("after sort {}", result);
        return result;
    }

    /**
     * Компаратор для сортировки результатов.
     */
    private static class GroupInfoComparator implements Comparator<GroupInfo> {

        @Override
        public int compare(GroupInfo o1, GroupInfo o2) {
            if (o1 == o2) {
                return 0;
            }
            if (o1 == null) {
                return 1;
            } else if (o2 == null) {
                return -1;
            }
            int compare = Double.compare(o2.getActual(), o1.getActual());
            if (compare == 0) {
                return Integer.compare(o2.getCountAlert(), o1.getCountAlert());
            }
            return compare;
        }
    }
}
