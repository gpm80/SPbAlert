package ru.lod.spbalert.repository;

import java.util.stream.Stream;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ru.lod.spbalert.dao.AlertDocument;

public interface AlertRepository extends ElasticsearchRepository<AlertDocument, String> {

    /**
     * Поиск по типу обращения.
     *
     * @param search
     * @return
     */
    Stream<AlertDocument> findBySearch(String search);

}
