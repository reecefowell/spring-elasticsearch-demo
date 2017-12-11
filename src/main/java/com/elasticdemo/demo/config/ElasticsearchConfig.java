package com.elasticdemo.demo.config;

import com.elasticdemo.demo.elasticsearch.ElasticsearchProperties;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {
    @Autowired
    private ElasticsearchProperties elasticsearchProperties;

    @Bean
    public Client client() throws UnknownHostException {
        PreBuiltTransportClient client = new PreBuiltTransportClient(Settings.EMPTY);

        for (String host : elasticsearchProperties.getHosts()) {
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), 9300));
        }

        return client;
    }
}
