package ru.sber.jira.task.manager.service;

import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.IssueType;

public interface JiraService {
    IssueType getIssueTypeByName(String name);
    BasicIssue createIssue(String projectKey, String issueName, String issueSummary);
    BasicIssue createIssue(String projectKey, IssueType issueType, String issueSummary);
}
