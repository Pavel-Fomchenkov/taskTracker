package com.pavel_fomchenkov.tasktracker.controller;

import com.pavel_fomchenkov.tasktracker.dto.TaskDTO;
import com.pavel_fomchenkov.tasktracker.dto.TaskDTOWithComments;
import com.pavel_fomchenkov.tasktracker.mapper.TaskMapper;
import com.pavel_fomchenkov.tasktracker.model.Task;
import com.pavel_fomchenkov.tasktracker.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
     * @return задача
     */
    @GetMapping()
    @Operation(summary = "Получение задачи по id")
    public ResponseEntity<TaskDTO> getById(@RequestParam Long id) {
        TaskDTO task = mapper.mapToTaskDTO(service.getById(id));
//        List<TaskDTO> tasks = service.getAll().stream().map(mapper::mapToTaskDTO).collect(Collectors.toList());
        return ResponseEntity.ok(task);
    }

//    UPDATE
    @PatchMapping
    @Operation(summary = "Редактирование задачи")
    public ResponseEntity<Task> editTask(@RequestBody Task task) {
        task = service.editTask(task);
        return ResponseEntity.ok(task);
    }
// TODO сделать добавление и удаление соисполнителей, при добавлении и удалении исполнителей создавать комментарий
//    "Иван Иванов добавил/удалил исполнителя Петра Петрова
/*    @PatchMapping
    @Operation(summary = "Добавление исполнителя к задаче")
    public ResponseEntity<Task> addPerformerToTask(@RequestBody Task task) {
        task = service.addPerformer(task);
        return ResponseEntity.ok(task);
    }*/


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
