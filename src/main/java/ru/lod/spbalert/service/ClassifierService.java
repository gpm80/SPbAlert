package ru.lod.spbalert.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lod.spbalert.dao.TypeAlertDocument;
import ru.lod.spbalert.repository.TypeAlertDocumentRepository;

/**
 * Классификатор происшествия
 */
@Service
public class ClassifierService {

    private static HashMap<String, String> types = new HashMap<>();
    @Autowired
    private TypeAlertDocumentRepository typeAlertDocumentRepository;

    {
        types.put("пожар", "пожар возгороание");
        types.put("водоснабжение", "откгючение гвс падение давления");
    }

    /**
     * стартовая инициализация типов происществий
     */
    @PostConstruct
    public void init() {
        for (String key : types.keySet()) {
            final List<TypeAlertDocument> byType = typeAlertDocumentRepository.findByType(key);
            if (byType.isEmpty()) {
                final TypeAlertDocument typeAlert = new TypeAlertDocument();
                typeAlert.setType(key);
                typeAlert.setKeywords(types.get(key));
            }
        }
    }

    public String classify(String keyword) {
//        final Optional<TypeAlertDocument> any = typeAlertDocumentRepository.findByKeywordsLike(keyword)
//            .findAny();
//        if (any.isPresent()) {
//            return any.get().getType();
//        }
        //TODO по умолчанию пока что пожар )
        return "пожар";
    }
}
