package ru.lod.spbalert.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ru.lod.spbalert.dao.GroupAlertDocument;

public interface GroupAlertRepository extends ElasticsearchRepository<GroupAlertDocument, String> {

}
