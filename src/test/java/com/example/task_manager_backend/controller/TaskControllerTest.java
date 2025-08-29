package com.example.task_manager_backend.controller;

import com.example.task_manager_backend.entity.Task;
import com.example.task_manager_backend.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    private ObjectMapper objectMapper;

    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Test Task 1");
        task1.setDescription("Description test task 1");
        task1.setCompleted(false);

        task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Test Task 2");
        task2.setDescription("Description test task 2");
        task2.setCompleted(true);
    }

    @Test
    public void getAllTasks_shouldReturnListOfTasks() throws Exception {
        when(taskService.getAllTasks()).thenReturn(Arrays.asList(task1, task2));

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value(task1.getTitle()))
                .andExpect(jsonPath("$[1].title").value(task2.getTitle()));

        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    public void getTaskById_shouldReturnTask_whenTaskExists() throws Exception {
        when(taskService.getTaskById(1L)).thenReturn(Optional.of(task1));

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(task1.getTitle()));

        verify(taskService, times(1)).getTaskById(1L);
    }

    @Test
    public void getTaskById_shouldReturnNotFound_whenTaskDoesNotExist() throws Exception {
        when(taskService.getTaskById(100L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(taskService, times(1)).getTaskById(100L);
    }

    @Test
    public void createTask_shouldCreateAndReturnTask() throws Exception {
        when(taskService.createTask(any(Task.class))).thenReturn(task1);

        mockMvc.perform(MockMvcRequestBuilders.post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(task1.getTitle()));

        verify(taskService, times(1)).createTask(any(Task.class));
    }

    @Test
    public void updateTask_shouldUpdateAndReturnTask_whenTaskExists() throws Exception {
        when(taskService.updateTask(anyLong(), any(Task.class))).thenReturn(task1);

        mockMvc.perform(MockMvcRequestBuilders.put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(task1.getTitle()));

        verify(taskService, times(1)).updateTask(eq(1L), any(Task.class));
    }

    @Test
    public void updateTask_shouldReturnNotFound_whenTaskDoesNotExist() throws Exception {
        when(taskService.updateTask(anyLong(), any(Task.class))).thenReturn(null);

        Task tareaFicticia = new Task();
        tareaFicticia.setId(100L);
        tareaFicticia.setTitle("Tarea ficticia");
        tareaFicticia.setDescription("Esta tarea no existe en la base de datos");
        tareaFicticia.setCompleted(false);

        mockMvc.perform(MockMvcRequestBuilders.put("/tasks/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tareaFicticia)))
                .andExpect(status().isNotFound());

        verify(taskService, times(1)).updateTask(eq(100L), any(Task.class));
    }

    @Test
    public void deleteTask_shouldReturnNoContent_whenTaskExists() throws Exception {
        doNothing().when(taskService).deleteTask(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(taskService, times(1)).deleteTask(1L);
    }

    @Test
    public void deleteTask_shouldReturnNotFound_whenTaskDoesNotExist() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(taskService).deleteTask(100L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/tasks/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(taskService, times(1)).deleteTask(100L);
    }

}
