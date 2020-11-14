package ru.lod.spbalert.repository;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ru.lod.spbalert.dao.TypeAlertDocument;

/**
 * Created by Petr Gusarov
 */
public interface TypeAlertDocumentRepository extends ElasticsearchRepository<TypeAlertDocument, String> {

    List<TypeAlertDocument> findByType(String type);

    Stream<TypeAlertDocument> findByKeywordsLike(String keyword);

}
