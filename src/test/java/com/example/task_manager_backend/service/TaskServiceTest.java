package com.example.task_manager_backend.service;

import com.example.task_manager_backend.entity.Task;
import com.example.task_manager_backend.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
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
    void getAllTasks_shouldReturnListOfTasks() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));
        List<Task> tasks = taskService.getAllTasks();
        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void getTaskById_shouldReturnTask_whenTaskExists() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));
        Optional<Task> foundTask = taskService.getTaskById(1L);
        assertTrue(foundTask.isPresent());
        assertEquals(task1.getTitle(), foundTask.get().getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void getTaskById_shouldReturnEmpty_whenTaskDoesNotExist() {
        when(taskRepository.findById(100L)).thenReturn(Optional.empty());
        Optional<Task> foundTask = taskService.getTaskById(100L);
        assertFalse(foundTask.isPresent());
        verify(taskRepository, times(1)).findById(100L);
    }

    @Test
    void createTask_shouldReturnCreatedTask() {
        when(taskRepository.save(task1)).thenReturn(task1);
        Task createdTask = taskService.createTask(task1);
        assertNotNull(createdTask);
        assertEquals(task1.getTitle(), createdTask.getTitle());
        verify(taskRepository, times(1)).save(task1);
    }

    @Test
    void updateTask_shouldUpdateAndReturnTask_whenTaskExists() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));
        when(taskRepository.save(task1)).thenReturn(task1);

        Task taskDetails = new Task();
        taskDetails.setTitle("Updated Title");
        taskDetails.setCompleted(true);
        Task updatedTask = taskService.updateTask(1L, taskDetails);

        assertNotNull(updatedTask);
        assertEquals("Updated Title", updatedTask.getTitle());
        assertTrue(updatedTask.isCompleted());

        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void updateTask_shouldReturnNull_whenTaskDoesNotExist() {
        when(taskRepository.findById(100L)).thenReturn(Optional.empty());

        Task taskDetails = new Task();
        taskDetails.setTitle("Updated Title");

        Task updatedTask = taskService.updateTask(100L, taskDetails);

        assertNull(updatedTask);
        verify(taskRepository, times(1)).findById(100L);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void deleteTask_shouldCallDeleteById() {
        taskService.deleteTask(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }
}
