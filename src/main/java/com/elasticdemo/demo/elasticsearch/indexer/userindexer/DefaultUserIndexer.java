package com.elasticdemo.demo.elasticsearch.indexer.userindexer;

import com.elasticdemo.demo.elasticsearch.indexer.userindexer.dto.UserDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.elasticsearch.common.xcontent.XContentType.JSON;

@Service
public class DefaultUserIndexer implements UserIndexer {
    @Autowired
    private Client client;
    @Autowired
    private ObjectMapper mapper;

    public UserDto getUserDocumentById(Long id) {
        GetResponse response = client
            .prepareGet("users_v1", "user", String.valueOf(id))
            .get();

        String json = response.getSourceAsString();

        UserDto userDto = new UserDto();
        try {
            JsonNode root = mapper.readTree(json);

            userDto.id = root.path("id").asLong();
            userDto.firstName = root.path("firstName").asText();
            userDto.lastName = root.path("lastName").asText();
            userDto.age = root.path("age").isNull()
                ? null
                : root.path("age").asInt();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userDto;
    }

    public void indexUser(UserDto userDto) {
        ObjectNode builder = mapper.createObjectNode()
            .put("id", userDto.id)
            .put("firstName", userDto.firstName)
            .put("lastName", userDto.lastName)
            .put("age", userDto.age);

        String json = builder.toString();

        client.prepareUpdate("users_v1", "user", String.valueOf(userDto.id))
            .setDoc(json, JSON)
            .setUpsert(json, JSON)
            .get();
    }
}
