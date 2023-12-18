package com.ln.taskMicroservice.controllers;

import com.ln.taskMicroservice.entities.Task;
import com.ln.taskMicroservice.model.TaskDTO;
import com.ln.taskMicroservice.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class TaskControllerTest {

    @Autowired
    TaskController taskController;

    @Autowired
    TaskRepository taskRepository;

    @Rollback
    @Test
    void createTask() {
        TaskDTO taskDTO = TaskDTO.builder()
                .input("ABCD")
                .pattern("BCD")
                .build();
        ResponseEntity<Long> taskIdResponseEntity = taskController.createTask(taskDTO);
        assertThat(taskIdResponseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));

        Long savedId = taskIdResponseEntity.getBody();
        Task task = taskRepository.findById(savedId).get();
        assertThat(task).isNotNull();
    }

    @Rollback
    @Test
    void testGetAllTasks() {
        Task testTask1 = new Task();
        testTask1.setInput("ABCD");
        testTask1.setPattern("BCD");

        Task testTask2 = new Task();
        testTask1.setInput("ABCD");
        testTask1.setPattern("BCD");

        Task testTask3 = new Task();
        testTask1.setInput("ABCD");
        testTask1.setPattern("BCD");

        List<Task> testTasks = List.of(testTask1, testTask2, testTask3);
        taskRepository.saveAll(testTasks);

        ResponseEntity<Iterable<TaskDTO>> allTasks = taskController.getAllTasks();
        Iterable<TaskDTO> taskDTOs = allTasks.getBody();
        long size = StreamSupport.stream(taskDTOs.spliterator(), false).count();
        assert(size == 3);
    }
}