package com.example.elasticintegration.configuration;

import co.elastic.clients.transport.TransportUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.net.ssl.SSLContext;
import java.time.Duration;

@Configuration
@ConfigurationProperties("elastic.integration")
@Data
@EnableElasticsearchRepositories(basePackages = "com.example.elasticintegration.repository")
@ComponentScan(basePackages = {"com.example.elasticintegration.service"})
public class ElasticsearchClientConfig extends ElasticsearchConfiguration {
    private String esHost;
    private String username;
    private String password;
    private String fingerprint;

    @Override
    public ClientConfiguration clientConfiguration() {
        SSLContext sslContext = TransportUtils
                .sslContextFromCaFingerprint(fingerprint);

        return ClientConfiguration.builder()
                .connectedTo(esHost)
                .usingSsl(sslContext)
                .withConnectTimeout(Duration.ofSeconds(5))
                .withSocketTimeout(Duration.ofSeconds(3))
                .withBasicAuth(username, password)
                .build();
    }


}
