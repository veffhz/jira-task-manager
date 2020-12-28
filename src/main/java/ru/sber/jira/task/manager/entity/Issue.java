package ru.sber.jira.task.manager.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class Issue {
    @NotBlank
    @ApiModelProperty(notes = "Тип issue", required = true, example = "task")
    private String type;
    @NotBlank
    @ApiModelProperty(notes = "Имя issue (summary)", required = true, example = "Issue 1")
    private String name;
}
