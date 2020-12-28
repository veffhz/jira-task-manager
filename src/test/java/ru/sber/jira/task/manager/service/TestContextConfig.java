package ru.sber.jira.task.manager.service;

import com.atlassian.jira.rest.client.api.JiraRestClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.sber.jira.task.manager.Mocks;

@Configuration
public class TestContextConfig {

    @Bean
    public JiraRestClient jiraRestClient() {
        return Mocks.getJiraRestClient();
    }
}
