package ru.sber.jira.task.manager.config;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
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

}
