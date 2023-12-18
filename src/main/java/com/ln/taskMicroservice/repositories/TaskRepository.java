package com.ln.taskMicroservice.repositories;

import com.ln.taskMicroservice.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}