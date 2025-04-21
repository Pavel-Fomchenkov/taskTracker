package com.pavel_fomchenkov.tasktracker.controller;

import com.pavel_fomchenkov.tasktracker.dto.TaskDTO;
import com.pavel_fomchenkov.tasktracker.dto.TaskDTOWithComments;
import com.pavel_fomchenkov.tasktracker.exception.TaskNotFoundException;
import com.pavel_fomchenkov.tasktracker.mapper.TaskMapper;
import com.pavel_fomchenkov.tasktracker.model.Status;
import com.pavel_fomchenkov.tasktracker.model.Task;
import com.pavel_fomchenkov.tasktracker.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@Tag(name = "Задачи")
public class TaskController {
    private final TaskService service;
    private final TaskMapper mapper;

//        CREATE

    /**
     * Создание новой задачи
     *
     * @return задача
     */
    @PostMapping()
    @Operation(summary = "Создание новой задачи")
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO taskDTO) {
        Task newTask = service.create(taskDTO);
        return ResponseEntity.ok(newTask);
    }
//    READ

    /**
     * Получение списка всех задач
     *
     * @param page номер страницы (offset)
     * @param size лимит выдачи
     * @return задачи
     */
    @GetMapping("all")
    @Operation(summary = "Получение информации о всех задачах")
    public ResponseEntity<List<TaskDTO>> getAll(@RequestParam @Min(0) int page, @RequestParam @Min(1) @Max(10000) int size) {
        List<TaskDTO> tasks = service.getAllDTO(page, size);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Получение задач по статусу
     *
     * @param status статус задачи
     * @param page   номер страницы (offset/size)
     * @param size   лимит выдачи
     * @return задачи
     */
    @GetMapping("status")
    @Operation(summary = "Получение информации о задачах с указанным статусом")
    public ResponseEntity<List<TaskDTO>> getByStatus(@RequestParam Status status, @RequestParam @Min(0) int page,
                                                     @RequestParam @Min(1) @Max(10000) int size) {
        List<TaskDTO> tasks = service.getByStatusDTO(status, page, size);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Получение задачи по id
     *
     * @param id id
     * @return задача
     */
    @GetMapping()
    @Operation(summary = "Получение задачи по id")
    public ResponseEntity<TaskDTOWithComments> getById(@RequestParam Long id) {
        Task task = service.getById(id);
        TaskDTOWithComments taskDTOWithComments = mapper.mapToTaskDTOWithComments(task);
        return ResponseEntity.ok(taskDTOWithComments);
    }

    /**
     * Получение списка задач по id автора и опционально статусу
     *
     * @param authorId id автора
     * @param status   статус задачи (опционально)
     * @return задача
     */
    @GetMapping("author")
    @Operation(summary = "Получение списка задач по id автора и опционально статусу")
    public ResponseEntity<List<TaskDTOWithComments>> getByAuthorId(@RequestParam Long authorId, @Nullable Status status) {
        List<Task> tasks;
        if (status == null) {
            tasks = service.getByAuthorId(authorId);
        } else tasks = service.getByAuthorIdAndStatus(authorId, status);
        List<TaskDTOWithComments> tasksDTOWithComments = tasks.stream().map(mapper::mapToTaskDTOWithComments).toList();
        return ResponseEntity.ok(tasksDTOWithComments);
    }

    @GetMapping("performer")
    @Operation(summary = "Получение списка задач по id исполнителя и опционально статусу")
    public ResponseEntity<List<TaskDTOWithComments>> getByPerformerId(@RequestParam Long performerId, @Nullable Status status) {
        List<Task> tasks;
        if (status == null) {
            tasks = service.getByPerformerId(performerId);
        } else tasks = service.getByPerformerIdAndStatus(performerId, status);
        List<TaskDTOWithComments> tasksDTOWithComments = tasks.stream().map(mapper::mapToTaskDTOWithComments).toList();
        return ResponseEntity.ok(tasksDTOWithComments);
    }

//    TODO сделать получение задач по статусу
//    TODO убедиться что методы получения задач со статусом корректно возвращают исполнителей и комменты
//    TODO из репозитория нужно убрать запросы в базу с указанием конкретного репозитория
//    UPDATE

    /**
     * Редактирование задачи
     *
     * @return задача
     */
    @PatchMapping
    @Operation(summary = "Редактирование задачи")
    public ResponseEntity<Task> editTask(@RequestBody Task task) {
        task = service.editTask(task);
        return ResponseEntity.ok(task);
    }

    /**
     * Добавление соисполнителя к задаче
     *
     * @param userId id соисполнителя
     * @param task   задача
     * @return задача
     */
    @PatchMapping("addPerformer")
    @Operation(summary = "Добавление исполнителя к задаче")
    public ResponseEntity<Task> addPerformerToTask(@RequestParam Long userId, @RequestBody Task task) {
        task = service.addPerformer(userId, task);
        return ResponseEntity.ok(task);
    }

    /**
     * Удаление соисполнителя из задачи
     *
     * @param userId id соисполнителя
     * @param task   задача
     * @return задача
     */
    @PatchMapping("removePerformer")
    @Operation(summary = "Удаление соисполнителя из задачи")
    public ResponseEntity<Task> removePerformerFromTask(@RequestParam Long userId, @RequestBody Task task) {
        task = service.removePerformer(userId, task);
        return ResponseEntity.ok(task);
    }

//    DELETE

    /**
     * Удаление задачи
     *
     * @param id идентификатор задачи
     * @return сообщение о завершении метода
     */
    @DeleteMapping()
    @Operation(summary = "Удаление задачи")
    public ResponseEntity<String> delete(@RequestParam Long id) {
        try {
            service.deleteTask(id);
        } catch (AccessDeniedException e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
        } catch (TaskNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
        return ResponseEntity.ok("Задача id " + id + " удалена из базы данных");
    }
}