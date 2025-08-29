package com.example.task_manager_backend.controller;

import com.example.task_manager_backend.entity.Task;
import com.example.task_manager_backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CRUD operations of tasks.
 * Provides endpoints to create, read, update, and delete tasks.
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    //We inject an instance of TaskService to interact with the database
    @Autowired
    private TaskService taskService;

    /**
     * Retrieves a list of all existing tasks.
     * Mapped as a GET request to the base URL /tasks.
     *
     * @return A list of Task objects.
     */
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    /**
     * Retrieves a specific task by its ID.
     * Mapped as a GET request to the URL /tasks/{id}.
     *
     * @param id The ID of the task to retrieve.
     * @return A ResponseEntity containing the task if found (HTTP 200 OK) or
     * a 404 Not Found status if it does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.getTaskById(id);
        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new task.
     * Mapped as a POST request to the base URL /tasks.
     *
     * @param task The Task object to create, sent in the request body.
     * @return The task saved in the database, including its generated ID.
     */
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    /**
     * Updates an existing task by its ID.
     * Mapped as a PUT request to the URL /tasks/{id}.
     *
     * @param id The ID of the task to update.
     * @param taskDetails The Task object containing the new data to update, sent in the request body.
     * @return A ResponseEntity containing the updated task if found (HTTP 200 OK) or
     * a 404 Not Found status if it does not exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        Task updatedTask = taskService.updateTask(id, taskDetails);
        if (updatedTask != null) {
            return ResponseEntity.ok(updatedTask);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a task by its ID.
     * Mapped as a DELETE request to the URL /tasks/{id}.
     *
     * @param id The ID of the task to delete.
     * @return A ResponseEntity with HTTP 204 No Content if successfully deleted or
     * a 404 Not Found status if it does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
