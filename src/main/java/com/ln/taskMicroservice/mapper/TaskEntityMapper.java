package com.ln.taskMicroservice.mapper;

import com.ln.taskMicroservice.entities.Task;
import com.ln.taskMicroservice.model.TaskDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TaskEntityMapper {

    TaskEntityMapper INSTANCE = Mappers.getMapper(TaskEntityMapper.class);

    TaskDTO map(Task taks);

    Task map(TaskDTO task);

    List<TaskDTO> map(List<Task> tasks);

}
