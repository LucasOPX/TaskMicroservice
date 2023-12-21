package com.ln.taskMicroservice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskDTO {

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


