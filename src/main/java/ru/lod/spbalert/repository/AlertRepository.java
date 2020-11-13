package ru.lod.spbalert.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ru.lod.spbalert.dao.AlertDocument;

public interface AlertRepository extends ElasticsearchRepository<AlertDocument, String> {

}
