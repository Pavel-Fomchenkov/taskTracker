package com.pavel_fomchenkov.tasktracker.controller;

import com.pavel_fomchenkov.tasktracker.dto.TaskDTO;
import com.pavel_fomchenkov.tasktracker.dto.TaskDTOWithComments;
import com.pavel_fomchenkov.tasktracker.mapper.TaskMapper;
import com.pavel_fomchenkov.tasktracker.model.Status;
import com.pavel_fomchenkov.tasktracker.model.Task;
import com.pavel_fomchenkov.tasktracker.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.persistence.Enumerated;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
     * @return задачи
     */
    @GetMapping("all")
    @Operation(summary = "Получение информации о всех задачах")
    public ResponseEntity<List<TaskDTO>> getAll() {
        List<TaskDTO> tasks = service.getAllDTO();
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

//    TODO сделать получение задач по id автора
//    TODO сделать получение задач по id исполнителя
//    TODO сделать получение задач по статусу
//    TODO сделать получение задач по статусу и id автора
//    TODO сделать получение задач по статусу и id исполнителя
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
        service.deleteTask(id);
        return ResponseEntity.ok("Задача id " + id + " отсутствует в базе данных");
    }
}
