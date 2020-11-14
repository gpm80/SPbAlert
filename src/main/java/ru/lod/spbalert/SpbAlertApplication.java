package ru.lod.spbalert;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication(scanBasePackages = {"ru.lod.spbalert"})
@EnableElasticsearchRepositories(basePackages = {"ru.lod.spbalert.repository"})
public class SpbAlertApplication{ //extends SpringBootServletInitializer {

    private static Logger logger = LoggerFactory.getLogger(SpbAlertApplication.class);
    @Value("${elastic.url}")
    private String url;


    public static void main(String[] args) {
        SpringApplication.run(SpbAlertApplication.class, args);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer propsConfigurer = new PropertySourcesPlaceholderConfigurer();
        propsConfigurer.setIgnoreResourceNotFound(true);
        List<Resource> resources = new ArrayList<>();
        resources.add(new ClassPathResource("default.properties"));
        try {
            resources.add(new ClassPathResource(InetAddress.getLocalHost().getHostName() + ".properties"));
        } catch (UnknownHostException e) {
            logger.error("Could not get name local host");
            throw new RuntimeException(e);
        }
        propsConfigurer.setLocations(resources.toArray(new Resource[resources.size()]));
        return propsConfigurer;
    }

    @Bean
    public RestHighLevelClient client() {
        ClientConfiguration clientConfiguration
            = ClientConfiguration.builder()
            .connectedTo(url)
            .build();
        return RestClients.create(clientConfiguration).rest();
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(client());
    }
}
