package ru.sber.jira.task.manager.config;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableAsync
@Configuration
@EnableCaching
@EnableSwagger2
@EnableConfigurationProperties(AppProperties.class)
public class AppConfig {

    @Bean
    public JiraRestClient jiraRestClient(AppProperties properties) {
        return new AsynchronousJiraRestClientFactory()
                .createWithBasicHttpAuthentication(
                        properties.getJiraUri(),
                        properties.getUserName(),
                        properties.getApiToken()
                );
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("issueTypes");
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        return mapper;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/**"))
                .build();
    }

}
