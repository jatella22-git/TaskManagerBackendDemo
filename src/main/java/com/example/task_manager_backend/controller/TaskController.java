package com.example.task_manager_backend.controller;

import com.example.task_manager_backend.entity.Task;
import com.example.task_manager_backend.repository.TaskRepository;
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

    // Inyectamos una instancia de TaskRepository para interactuar con la base de datos
    @Autowired
    private TaskRepository taskRepository;

    /**
     * Retrieves a list of all existing tasks.
     * Mapped as a GET request to the base URL /tasks.
     *
     * @return A list of Task objects.
     */
    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
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
        Optional<Task> task = taskRepository.findById(id);
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
        return taskRepository.save(task);
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
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            Task existingTask = task.get();
            existingTask.setTitle(taskDetails.getTitle());
            existingTask.setDescription(taskDetails.getDescription());
            existingTask.setCompleted(taskDetails.isCompleted());
            taskRepository.save(existingTask);
            return ResponseEntity.ok(existingTask);
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
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            taskRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
