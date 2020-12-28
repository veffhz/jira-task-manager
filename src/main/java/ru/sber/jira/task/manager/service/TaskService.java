package ru.sber.jira.task.manager.service;

import com.atlassian.jira.rest.client.api.domain.IssueType;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ru.sber.jira.task.manager.entity.TaskDto;
import ru.sber.jira.task.manager.exceptions.FileParseException;

import java.io.IOException;

@Slf4j
@Service
public class TaskService {

    private final JiraService jiraService;
    private final ObjectMapper objectMapper;

    @Autowired
    public TaskService(JiraService jiraService, ObjectMapper objectMapper) {
        this.jiraService = jiraService;
        this.objectMapper = objectMapper;
    }

    @Async
    public void runTask(TaskDto taskDto) {
        log.info("Beginning start task: {}", taskDto);

        taskDto.getIssues().parallelStream().forEach(
                issue -> {
                    // Чтобы работало кеширование, getIssueTypeByName должен быть вызыван из другого компонента
                    IssueType issueType = jiraService.getIssueTypeByName(issue.getType());

                    jiraService.createIssue(
                            taskDto.getProjectKey(), issueType, issue.getName()
                    );
                }
        );
    }

    @Async
    public void runTask(MultipartFile file) {
        try {
            TaskDto taskDto = objectMapper.readValue(file.getInputStream(), TaskDto.class);
            runTask(taskDto);
        } catch (IOException e) {
            throw new FileParseException(e.getMessage(), e);
        }
    }
}
