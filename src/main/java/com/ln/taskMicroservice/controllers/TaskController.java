package com.ln.taskMicroservice.controllers;

import com.ln.taskMicroservice.model.TaskDTO;
import com.ln.taskMicroservice.services.TaskService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/")
    public ResponseEntity<Long> createTask(@RequestBody @Validated TaskDTO task) {
        Long taskId = taskService.createTask(task);
        return new ResponseEntity<>(taskId, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<Iterable<TaskDTO>> getAllTasks() {
        Iterable<TaskDTO> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskStatusAndResult(@PathVariable Long id) {
        TaskDTO task = taskService.getTaskById(id);
        if (task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
    }
}