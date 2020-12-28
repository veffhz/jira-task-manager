package ru.sber.jira.task.manager;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.MetadataRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.IssueType;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;

import io.atlassian.util.concurrent.Promise;

import org.mockito.Mockito;

import org.springframework.mock.web.MockMultipartFile;

import ru.sber.jira.task.manager.entity.Issue;
import ru.sber.jira.task.manager.entity.TaskDto;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Mocks {

    public static final String TASK_NAME = "task";

    public static JiraRestClient getJiraRestClient() {
        JiraRestClient jiraRestClient = Mockito.spy(JiraRestClient.class);

        MetadataRestClient metadataRestClient = metadataRestClient();
        when(jiraRestClient.getMetadataClient()).thenReturn(metadataRestClient);

        IssueRestClient issueRestClient = issueClient();
        when(jiraRestClient.getIssueClient()).thenReturn(issueRestClient);
        return jiraRestClient;
    }

    public static IssueRestClient issueClient() {
        IssueRestClient issueRestClient = Mockito.mock(IssueRestClient.class);

        Promise<BasicIssue> basicIssue = getBasicIssue();
        when(issueRestClient.createIssue(any(IssueInput.class))).thenReturn(basicIssue);
        return issueRestClient;
    }

    public static MetadataRestClient metadataRestClient() {
        MetadataRestClient metadataRestClient = Mockito.mock(MetadataRestClient.class);

        Promise<Iterable<IssueType>> issueTypes = issueTypes();
        when(metadataRestClient.getIssueTypes()).thenReturn(issueTypes);
        return metadataRestClient;
    }

    public static Promise<Iterable<IssueType>> issueTypes() {
        Promise<Iterable<IssueType>> mock = mock(Promise.class);

        IssueType issueType = issueType();
        when(mock.claim()).thenReturn(Collections.singletonList(issueType));
        return mock;
    }

    public static Promise<BasicIssue> getBasicIssue() {
        Promise<BasicIssue> mock = mock(Promise.class);

        BasicIssue issue = basicIssue();
        when(mock.claim()).thenReturn(issue);
        return mock;
    }

    public static MockMultipartFile jsonFromFile(String path) throws IOException {
        URL resource = Mocks.class.getClassLoader().getResource(path);

        Objects.requireNonNull(resource);

        return new MockMultipartFile(
                "file", path,"multipart/form-data", resource.openStream()
        );
    }

    public static TaskDto badTaskDto(String projectKey) {
        TaskDto taskDto = new TaskDto();
        taskDto.setProjectKey(projectKey);
        taskDto.setIssues(Arrays.asList(
                new Issue(), new Issue()
        ));
        return taskDto;
    }

    public static TaskDto taskDto(String projectKey) {
        TaskDto taskDto = new TaskDto();
        taskDto.setProjectKey(projectKey);
        taskDto.setIssues(Arrays.asList(
                issue("name1"), issue("name2"), issue("name3")
        ));
        return taskDto;
    }

    public static Issue issue(String name) {
        Issue issue = new Issue();
        issue.setName(name);
        issue.setType(TASK_NAME);
        return issue;
    }

    public static IssueType issueType() {
        IssueType mock = mock(IssueType.class);
        when(mock.getName()).thenReturn("Task");
        return mock;
    }

    public static BasicIssue basicIssue() {
        BasicIssue mock = mock(BasicIssue.class);
        when(mock.getKey()).thenReturn("PR-0");
        return mock;
    }

}
