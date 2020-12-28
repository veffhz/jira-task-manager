package ru.sber.jira.task.manager;

import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.IssueType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import ru.sber.jira.task.manager.entity.Issue;
import ru.sber.jira.task.manager.entity.TaskDto;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;

public class Mocks {

    public static final String TASK_NAME = "task";

    public static ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        return mapper;
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
        return Mockito.mock(IssueType.class);
    }

    public static BasicIssue basicIssue() {
        return Mockito.mock(BasicIssue.class);
    }

}
