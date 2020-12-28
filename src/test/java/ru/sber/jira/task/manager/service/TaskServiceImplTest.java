package ru.sber.jira.task.manager.service;

import com.atlassian.jira.rest.client.api.domain.IssueType;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ru.sber.jira.task.manager.entity.TaskDto;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static ru.sber.jira.task.manager.Mocks.*;

@DisplayName("Test for Task service")
@ExtendWith(SpringExtension.class)
class TaskServiceImplTest {

    @SpyBean
    private TaskService taskService;

    @MockBean
    private JiraService jiraService;

    @SpyBean
    private ObjectMapper objectMapper;

    @BeforeEach
    void init() {
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }

    @Test
    @DisplayName("Test invoke runTask")
    void shouldRunCreateIssueWithTaskDto() {
        TaskDto taskDto = taskDto("PR");

        IssueType issueType = issueType();

        when(jiraService.getIssueTypeByName(TASK_NAME)).thenReturn(issueType);
        when(jiraService.createIssue(anyString(), any(IssueType.class), contains("name"))).thenReturn(basicIssue());

        taskService.runTask(taskDto);

        verify(jiraService, times(taskDto.getIssues().size())).createIssue(
                anyString(), any(IssueType.class), contains("name")
        );
    }

    @Test
    @DisplayName("Test invoke runTask")
    void shouldRunCreateIssueWithMultiPartFile() throws IOException {
        MockMultipartFile mockMultipartFile = jsonFromFile("InputFile.json");

        IssueType issueType = issueType();

        when(jiraService.getIssueTypeByName(TASK_NAME)).thenReturn(issueType);
        when(jiraService.createIssue(anyString(), any(IssueType.class), contains("name"))).thenReturn(basicIssue());

        taskService.runTask(mockMultipartFile);

        verify(jiraService, times(3)).createIssue(
                anyString(), any(IssueType.class), contains("Issue")
        );
    }

}