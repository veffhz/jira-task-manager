package ru.sber.jira.task.manager;

import org.springframework.mock.web.MockMultipartFile;
import ru.sber.jira.task.manager.entity.Issue;
import ru.sber.jira.task.manager.entity.TaskDto;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;

public class Mocks {

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
        issue.setType("task");
        return issue;
    }

}
