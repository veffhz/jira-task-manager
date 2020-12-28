package ru.sber.jira.task.manager.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
public class TaskDto {
    @NotBlank
    private String projectKey;
    private List<@Valid Issue> issues;
}
