package ru.lod.spbalert.repository;

import java.util.Date;
import java.util.stream.Stream;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ru.lod.spbalert.dao.GroupAlertDocument;

public interface GroupAlertRepository extends ElasticsearchRepository<GroupAlertDocument, String> {

    Stream<GroupAlertDocument> findByTimePointBetween(Date start, Date end);

}
