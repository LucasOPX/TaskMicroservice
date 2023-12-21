package com.ln.taskMicroservice.services;

import com.ln.taskMicroservice.entities.Task;
import com.ln.taskMicroservice.mapper.TaskEntityMapper;
import com.ln.taskMicroservice.model.TaskDTO;
import com.ln.taskMicroservice.model.TaskRequest;
import com.ln.taskMicroservice.model.TaskStatus;
import com.ln.taskMicroservice.repositories.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ExecutorService executorService;


    @Async
    public void processTask(TaskDTO task) {

        try {
            task.setStatus(TaskStatus.IN_PROGRESS);
            taskRepository.save(TaskEntityMapper.INSTANCE.map(task));

            for (int i = 0; i <= 100; i += 10) {
                Thread.sleep(1000);
                task.setProgress(i);
                taskRepository.save(TaskEntityMapper.INSTANCE.map(task));
            }
            findBestMatchPosition(task);
            task.setStatus(TaskStatus.COMPLETED);
            taskRepository.save(TaskEntityMapper.INSTANCE.map(task));

        } catch (InterruptedException e) {
            task.setStatus(TaskStatus.FAILED);
            taskRepository.save(TaskEntityMapper.INSTANCE.map(task));
        }
    }

    private void findBestMatchPosition(TaskDTO task) {
        int patternLength = task.getPattern().length();
        int inputLength = task.getInput().length();
        int bestMatchPosition = -1;
        int minTypos = Integer.MAX_VALUE;

        for (int i = 0; i <= inputLength - patternLength; i++) {
            int typos = calculateTypos(task.getPattern(), task.getInput().substring(i, i + patternLength));

            if (typos < minTypos) {
                minTypos = typos;
                bestMatchPosition = i;
            }
        }
        task.setPosition(bestMatchPosition);
        task.setTypos(minTypos);
    }

    private int calculateTypos(String pattern, String substring) {
        int typos = 0;
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) != substring.charAt(i)) {
                typos++;
            }
        }
        return typos;
    }

    public Iterable<TaskDTO> getAllTasks() {
        return TaskEntityMapper.INSTANCE.map(taskRepository.findAll());
    }

    public TaskDTO getTaskById(Long id) {
        return TaskEntityMapper.INSTANCE.map(taskRepository.findById(id).orElse(null));
    }

    public Long createTask(TaskRequest taskRequest) {
        TaskDTO taskDTO = TaskDTO.builder()
                .input(taskRequest.getInput())
                .pattern(taskRequest.getPattern())
                .status(TaskStatus.IN_PROGRESS)
                .build();

        Task savedEntity = taskRepository.save(TaskEntityMapper.INSTANCE.map(taskDTO));
        TaskDTO savedTask= TaskEntityMapper.INSTANCE.map(savedEntity);

        executorService.submit(() -> processTask(savedTask));

//        Thread taskProcessingThread = new Thread(() -> processTask(savedTask));
//        taskProcessingThread.start();
        return savedTask.getId();
    }
}
