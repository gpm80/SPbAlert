package ru.lod.spbalert.repository;

import java.util.Date;
import java.util.stream.Stream;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ru.lod.spbalert.dao.GroupAlertDocument;

/**
 * репозиторий для работы с сгруппированными данными.
 */
public interface GroupAlertRepository extends ElasticsearchRepository<GroupAlertDocument, String> {

    Stream<GroupAlertDocument> findByTimePointBetween(Date start, Date end);

    Stream<GroupAlertDocument> findBySpbAlert_DateBetween(Long start, Long end);

}
