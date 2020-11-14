package ru.lod.spbalert.repository;

import java.util.stream.Stream;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ru.lod.spbalert.dao.AlertDocument;

public interface AlertRepository extends ElasticsearchRepository<AlertDocument, String> {
    Stream<AlertDocument> findByCategory(String category);
}
