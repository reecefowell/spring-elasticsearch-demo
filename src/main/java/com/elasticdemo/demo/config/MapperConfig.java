package com.elasticdemo.demo.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

@Configuration
public class MapperConfig {
    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder() {
            @Override
            public void configure(ObjectMapper mapper) {
                super.configure(mapper);
                mapper.setVisibility(mapper.getVisibilityChecker().with(JsonAutoDetect.Visibility.NONE));
                mapper.disable(WRITE_DATES_AS_TIMESTAMPS);
            }
        };
    }
}
