package ru.sber.jira.task.manager.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class Issue {
    @NotBlank
    private String type;
    @NotBlank
    private String name;
}
