package ru.sber.jira.task.manager.config;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.URL;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.net.URI;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "jira-manager")
public class AppProperties {
    @Email
    private String userName;
    @NotBlank
    private String apiToken;
    @URL
    private String jiraUri;
    private String language;

    public URI getJiraUri() {
        return URI.create(this.jiraUri);
    }
}
