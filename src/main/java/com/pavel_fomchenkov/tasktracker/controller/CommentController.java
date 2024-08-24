package com.pavel_fomchenkov.tasktracker.controller;

import com.pavel_fomchenkov.tasktracker.mapper.CommentMapper;
import com.pavel_fomchenkov.tasktracker.model.Comment;
import com.pavel_fomchenkov.tasktracker.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * @param comment комментарий
     * @return комментарий
     */
    @PostMapping()
    public ResponseEntity<Comment> createComment(@RequestParam Long taskId, @RequestBody Comment comment) {
        Comment newComment = service.create(taskId, comment);
        return ResponseEntity.ok(newComment);
    }
//    READ

    /**
     * Получение списка всех комментариев
     *
     * @return комментарии
     */
//    TODO исправить ошибку
    @GetMapping("all")
    @Operation(summary = "Получение информации о всех комментариях")
    public ResponseEntity<List<Comment>> getAll() {
        List<Comment> comments = service.getAll();
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
    public ResponseEntity<Comment> getById(Long id) {
        Comment comment = service.getById(id);
        return ResponseEntity.ok(comment);
    }

//    UPDATE

    /**
     * Редактирование комментария
     *
     * @param comment исправленный комментарий
     * @return комментарий из базы данных
     */
    @PatchMapping()
    public ResponseEntity<Comment> edit(@RequestBody Comment comment) {
        Comment updatedComment = service.edit(comment);
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
