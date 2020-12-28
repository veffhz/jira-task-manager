package ru.sber.jira.task.manager.service.impl;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.IssueType;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import ru.sber.jira.task.manager.exceptions.IssueNotFoundException;
import ru.sber.jira.task.manager.service.JiraService;

import java.util.stream.StreamSupport;

@Slf4j
@Service
public class JiraServiceImpl implements JiraService {

    private final JiraRestClient jiraRestClient;

    @Autowired
    public JiraServiceImpl(JiraRestClient jiraRestClient) {
        this.jiraRestClient = jiraRestClient;
    }

    @Override
    public BasicIssue createIssue(String projectKey, String issueName, String summary) {
        IssueType issueType = getIssueTypeByName(issueName);
        return createIssue(projectKey, issueType, summary);
    }

    @Override
    public BasicIssue createIssue(String projectKey, IssueType issueType, String summary) {
        IssueInput newIssue = new IssueInputBuilder()
                .setProjectKey(projectKey)
                .setIssueType(issueType)
                .setSummary(summary).build();
        IssueRestClient issueClient = jiraRestClient.getIssueClient();
        BasicIssue createdIssue = issueClient.createIssue(newIssue).claim();
        log.info("created issue {}", createdIssue.getKey());
        return createdIssue;
    }

    @Cacheable("issueTypes")
    public IssueType getIssueTypeByName(String name) {
        if (log.isDebugEnabled()) {
            log.debug("get issue type by name '{}'", name);
        }
        return StreamSupport.stream(getIssueTypes().spliterator(), true)
                .filter(issue -> issue.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(IssueNotFoundException::new);
    }

    private Iterable<IssueType> getIssueTypes() {
        return jiraRestClient.getMetadataClient().getIssueTypes().claim();
    }

}
