package ru.sber.jira.task.manager.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import ru.sber.jira.task.manager.entity.TaskDto;
import ru.sber.jira.task.manager.service.TaskService;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.sber.jira.task.manager.Mocks.*;

@Import(TaskApi.class)
@ExtendWith(SpringExtension.class)
@DisplayName("Test for Task Api")
@WebMvcTest(controllers = TaskApi.class)
class TaskApiTest {

    @MockBean
    private TaskService taskService;

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper mapper;

    @Test
    @DisplayName("Test start task on /api/task/json")
    void shouldPostOnTaskApiFromJson() throws Exception {
        TaskDto taskDto = taskDto("PR");

        this.mvc.perform(post("/api/task/json")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(taskDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(this.taskService, times(1)).runTask(taskDto);
    }

    @Test
    @DisplayName("Test fail task on /api/task/json")
    void shouldFailPostOnTaskApiFromJson() throws Exception {
        TaskDto taskDto = badTaskDto("PR");

        this.mvc.perform(post("/api/task/json")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(taskDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(this.taskService, never()).runTask(taskDto);
    }

    @Test
    @DisplayName("Test start task on /api/task/file")
    void shouldPostOnTaskApiFromJsonFile() throws Exception {
        MockMultipartFile mockMultipartFile = jsonFromFile("InputFile.json");

        this.mvc.perform(MockMvcRequestBuilders.multipart("/api/task/file")
                .file(mockMultipartFile))
                .andExpect(status().isOk());

        verify(this.taskService, times(1)).runTask(any(MultipartFile.class));
    }
}