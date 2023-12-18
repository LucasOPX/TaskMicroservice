package com.ln.taskMicroservice.services;

import com.ln.taskMicroservice.model.TaskDTO;

public interface TaskService {

    void processTask(TaskDTO task);

    Iterable<TaskDTO> getAllTasks();

    TaskDTO getTaskById(Long id);

    Long createTask(TaskDTO task);

}
