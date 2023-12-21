package com.ln.taskMicroservice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class TaskRequest {

        @NotBlank
        @NotNull
        private String pattern;

        @NotBlank
        @NotNull
        private String input;

}
