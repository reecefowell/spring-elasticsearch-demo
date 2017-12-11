package com.elasticdemo.demo.elasticsearch;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("elasticsearch")
public class ElasticsearchProperties {
    private List<String> hosts;

    public List<String> getHosts() {
        return hosts;
    }

    public void setHosts(List<String> hosts) {
        this.hosts = hosts;
    }
}
