package ru.sber.jira.task.manager.api;

import io.swagger.annotations.ApiOperation;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import ru.sber.jira.task.manager.service.TaskService;

@Slf4j
@RestController
public class TaskApi {

    private final TaskService taskService;

    @Autowired
    public TaskApi(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(path = "/api/task/load", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "Запуск создания задач в Jira.", response = String.class)
    public ResponseEntity<String> startTask(@RequestPart MultipartFile file) {
        log.info("start task api call");
        taskService.runTask(file);
        return new ResponseEntity<>("Started!", HttpStatus.OK);
    }

}
