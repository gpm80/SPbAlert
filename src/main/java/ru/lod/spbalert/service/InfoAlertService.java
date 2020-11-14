package ru.lod.spbalert.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lod.spbalert.common.DtUtil;
import ru.lod.spbalert.dao.GroupAlertDocument;
import ru.lod.spbalert.model.GroupInfo;
import ru.lod.spbalert.model.RequestInfo;
import ru.lod.spbalert.repository.GroupAlertRepository;

/**
 * сервис выдачи информации
 */
@Service
public class InfoAlertService {

    @Autowired
    private GroupAlertRepository groupAlertRepository;

    public Set<GroupInfo> find(RequestInfo requestInfo) {
        final Date end = requestInfo.getTimePoint();
        final LocalDateTime localDateTime = DtUtil.toDateTime(end);
        final Date start = DtUtil.toDate(localDateTime.minusHours(requestInfo.getRetroHour()));
        final Stream<GroupAlertDocument> stream = groupAlertRepository.findByTimePointBetween(start, end);
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
        // Сортировка групп событий по прогрессивным весам
        Set<GroupInfo> sortGroupInfo = new TreeSet<>(new GroupInfoComparator());
        sortGroupInfo.addAll(groupMap.values());
        return sortGroupInfo;
    }

    private class GroupInfoComparator implements Comparator<GroupInfo> {

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
            return Integer.compare(o1.getActual(), o2.getActual());
        }
    }
}
