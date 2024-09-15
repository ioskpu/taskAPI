package com.sabas.rest.Controller;

import com.sabas.rest.Model.Task;
import com.sabas.rest.Repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping(value = "/tasks")
    public List<Task> getAllTasks() {
        return todoRepository.findAll();
    }

    @GetMapping(value="/tasks/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = todoRepository.findById(id);
        return task.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/savetask")
    public String saveTask (@RequestBody Task task) {
        todoRepository.save(task);
        return "Task saved";
    }

    @PutMapping("/update/{id}")
    public String updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        Task updtaedTask = todoRepository.findById(id).orElse(null);
        updatedTask.setTitle(updatedTask.getTitle());
        updatedTask.setDescription(updatedTask.getDescription());
        updatedTask.setCompleted(updatedTask.isCompleted());
        todoRepository.save(updatedTask);
        return "Task updated";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        Task deletedTask = todoRepository.findById(id).get();
        todoRepository.delete(deletedTask);
        return "Task deleted";
    }
}
