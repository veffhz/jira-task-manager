package ru.sber.jira.task.manager.service;

import com.atlassian.jira.rest.client.api.JiraRestClient;

import com.atlassian.jira.rest.client.api.domain.IssueType;
import com.atlassian.jira.rest.client.api.domain.input.ComplexIssueInputFieldValue;
import com.atlassian.jira.rest.client.api.domain.input.FieldInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ru.sber.jira.task.manager.service.impl.JiraServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Test for Jira service")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestContextConfig.class})
class JiraServiceImplTest {

    @Autowired
    private JiraRestClient jiraRestClient;

    @SpyBean
    private JiraServiceImpl jiraService;

    @Test
    @DisplayName("Test invoke createIssue")
    void shouldRunCreateIssueWithStringParam() {
        String projectKey = "PR";
        String issueName = "task";
        String summary = "Task 1";

        jiraService.createIssue(projectKey, issueName, summary);

        verify(jiraService, times(1)).createIssue(
                anyString(), ArgumentMatchers.any(IssueType.class), anyString()
        );
    }

    @Test
    @DisplayName("Test invoke createIssue")
    void shouldRunCreateIssue() {
        String projectKey = "PR";
        String issueName = "task";
        String summary = "Task 1";

        IssueType task = jiraService.getIssueTypeByName(issueName);

        jiraService.createIssue(projectKey, task, summary);

        verify(jiraRestClient, times(1)).getIssueClient();

        ArgumentCaptor<IssueInput> captor = ArgumentCaptor.forClass(IssueInput.class);
        verify(jiraRestClient.getIssueClient()).createIssue(captor.capture());

        IssueInput issueInput = captor.getValue();

        String project = parseIssueInput(issueInput, "project");
        assertEquals(project, projectKey);

        String summaryFromIssue = parseIssueInput(issueInput, "summary");
        assertEquals(summaryFromIssue, summary);
    }

    @Test
    @DisplayName("Test invoke getIssueTypeByName")
    void shouldGetIssueTypeByName() {
        IssueType task = jiraService.getIssueTypeByName("task");
        assertEquals(task.getName(), "Task");
    }

    private String parseIssueInput(IssueInput issueInput, String field) {
        FieldInput project = issueInput.getField(field);
        Object value = project.getValue();
        if (value instanceof String) {
            return String.valueOf(value);
        }
        return String.valueOf(((ComplexIssueInputFieldValue) value).getValuesMap().get("key"));
    }

}