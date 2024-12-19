package com.pavel_fomchenkov.tasktracker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTOMin {
    @Id
    private Long id;

    @Schema(description = "Имя пользователя", example = "Иван Иванов")
    @Size(min = 3, max = 30, message = "Имя пользователя должно содержать от 3 до 30 символов")
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String username;
}
