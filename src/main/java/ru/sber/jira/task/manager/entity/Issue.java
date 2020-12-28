package ru.sber.jira.task.manager.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Issue {
    private String type;
    private String name;
}
