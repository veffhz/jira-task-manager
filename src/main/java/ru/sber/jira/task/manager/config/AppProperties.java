package ru.sber.jira.task.manager.config;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@Getter
@Setter
@ConfigurationProperties(prefix = "jira-manager")
public class AppProperties {

    private String userName;
    private String apiToken;
    private String jiraUri;
    private String language;

    public URI getJiraUri() {
        return URI.create(this.jiraUri);
    }
}
