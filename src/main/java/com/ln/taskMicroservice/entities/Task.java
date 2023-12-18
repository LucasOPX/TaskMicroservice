package com.ln.taskMicroservice.entities;

import com.ln.taskMicroservice.model.TaskStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;



@Data
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @NotNull
    private String pattern;

    @NotBlank
    @NotNull
    private String input;
    private int position;
    private int typos;
    private int progress;
    private TaskStatus status;
}
