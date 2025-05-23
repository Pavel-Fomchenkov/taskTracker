package com.pavel_fomchenkov.tasktracker.controller;

import com.pavel_fomchenkov.tasktracker.dto.CommentDTO;
import com.pavel_fomchenkov.tasktracker.mapper.CommentMapper;
import com.pavel_fomchenkov.tasktracker.model.Comment;
import com.pavel_fomchenkov.tasktracker.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@Tag(name = "Комментарии")
public class CommentController {
    private final CommentService service;
    private final CommentMapper mapper;
//    CREATE

    /**
     * Создание нового комментария к задаче
     *
     * @param taskId  идентификатор задачи
     * @param commentDTO комментарий
     * @return комментарий
     */
    @PostMapping()
    @Operation(summary = "Создание нового комментария к задаче")
    public ResponseEntity<CommentDTO> createComment(@RequestParam Long taskId, @RequestBody CommentDTO commentDTO) {
        CommentDTO newComment = mapper.mapToCommentDTO(service.create(taskId, commentDTO));
        return ResponseEntity.ok(newComment);
    }
//    READ

    /**
     * Получение списка всех комментариев
     *
     * @return комментарии
     */
    @GetMapping("all")
    @Operation(summary = "Получение информации о всех комментариях")
    public ResponseEntity<List<CommentDTO>> getAll() {
        List<CommentDTO> comments = service.getAll().stream().map(mapper::mapToCommentDTO).toList();
        return ResponseEntity.ok(comments);
    }

    /**
     * Получение комментария по id
     *
     * @param id идентификатор комментария
     * @return комментарий
     */
    @GetMapping()
    @Operation(summary = "Получение комментария по id")
    public ResponseEntity<CommentDTO> getById(Long id) {
        CommentDTO comment = mapper.mapToCommentDTO(service.getById(id));
        return ResponseEntity.ok(comment);
    }

    /**
     * Получение комментариев по id задачи
     *
     * @param taskId идентификатор задачи
     * @return список комментариев
     */
    @GetMapping("task")
    @Operation(summary = "Получение комментариев по id задачи")
    public ResponseEntity<List<CommentDTO>> getByTaskId(Long taskId) {
        List<Comment> comments = service.getByTaskId(taskId);
        return ResponseEntity.ok(comments.stream().map(mapper::mapToCommentDTO).collect(Collectors.toList()));
    }

//    UPDATE

    /**
     * Редактирование комментария
     *
     * @param commentDTO исправленный комментарий
     * @return комментарий из базы данных
     */
    @PatchMapping()
    @Operation(summary = "Редактирование комментария")
    public ResponseEntity<CommentDTO> edit(@RequestBody CommentDTO commentDTO) {
        CommentDTO updatedComment = mapper.mapToCommentDTO(service.edit(commentDTO));
        return ResponseEntity.ok(updatedComment);
    }

//    DELETE

    /**
     * Удаление комментария по его id
     *
     * @param id идентификатор комментария
     * @return сообщение о завершении метода
     */
    @DeleteMapping()
    @Operation(summary = "Удаление комментария")
    public ResponseEntity<String> delete(@RequestParam Long id) {
        service.deleteComment(id);
        return ResponseEntity.ok("Комментарий id " + id + " отсутствует в базе данных");
    }
}
