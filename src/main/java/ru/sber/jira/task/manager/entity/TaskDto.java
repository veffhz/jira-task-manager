package ru.sber.jira.task.manager.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TaskDto {
    private String projectKey;
    private List<Issue> issues;
}
