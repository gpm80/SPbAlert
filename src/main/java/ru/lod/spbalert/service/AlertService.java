package ru.lod.spbalert.service;

import java.io.InputStream;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;
import ru.lod.spbalert.dao.Response;

@Service
public class AlertService {

    @Autowired
    private ElasticsearchRestTemplate template;

    public Response getResponse(Map<String, String> params) {
        return new Response();
    }

    public Response getResponse(InputStream input) {
        return new Response();
    }
}
