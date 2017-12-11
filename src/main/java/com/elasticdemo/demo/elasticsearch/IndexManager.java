package com.elasticdemo.demo.elasticsearch;

import com.google.common.io.CharStreams;
import java.io.IOException;
import java.io.InputStreamReader;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.IndexNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import static org.elasticsearch.common.xcontent.XContentType.JSON;

@Component
public class IndexManager {
    @Autowired
    private Client client;
    @Autowired
    private ResourceLoader resourceLoader;

    public void reindex() {
        client
            .admin()
            .indices()
            .prepareRefresh("users_v1")
            .get();
    }

    public void recreate() {
        deleteIndex("users_v1");
        createIndex("users_v1", "user");
    }

    private void deleteIndex(String index) {
        try {
            client
                .admin()
                .indices()
                .prepareDelete(index)
                .get();
        } catch (IndexNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createIndex(String index, String type) {
        Resource resource = resourceLoader.getResource("classpath:indexes/" + index + ".json");
        try (InputStreamReader reader = new InputStreamReader(resource.getInputStream())) {
            String mapping = CharStreams.toString(reader);

            client
                .admin()
                .indices()
                .prepareCreate(index)
                .addMapping(type, mapping, JSON)
                .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
