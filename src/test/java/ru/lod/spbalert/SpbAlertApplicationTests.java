package ru.lod.spbalert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class SpbAlertApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(SpbAlertApplicationTests.class);

    //@Test
    //@Ignore
    //void uploadData() {
    //    final File file = new File("dataset.xlsx");
    //    final HttpEntity entity = MultipartEntityBuilder.create()
    //        .addPart("file", new FileBody(file))
    //        .build();
    //    final HttpPost httpPost = new HttpPost("http://localhost:8080/alert/test/1");
//  //      final HttpPost httpPost = new HttpPost("http://localhost:8080/alert/upload");
    //    httpPost.setEntity(entity);
    //    final HttpClient client = HttpClientBuilder.create().build();
    //    try {
    //        HttpResponse execute = client.execute(httpPost);
    //        execute.getEntity();
    //    } catch (IOException e) {
    //        logger.debug(e.getMessage(), e);
    //    }
    //}

    public static void main(String[] args) throws IOException {
        File file = new File("src/test/resources/dataset.xlsx");
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/alert/upload";
        HttpMethod requestMethod = HttpMethod.POST;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);


        MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
        ContentDisposition contentDisposition = ContentDisposition
            .builder("form-data")
            .name("file")
            .filename(file.getName())
            .build();

        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
        HttpEntity<byte[]> fileEntity = new HttpEntity<>(Files.readAllBytes(file.toPath()), fileMap);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileEntity);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, requestMethod, requestEntity, String.class);
    }

}
