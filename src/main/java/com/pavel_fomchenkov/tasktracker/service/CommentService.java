package com.pavel_fomchenkov.tasktracker.service;

import com.pavel_fomchenkov.tasktracker.dto.CommentDTO;
import com.pavel_fomchenkov.tasktracker.model.Comment;

import java.util.List;

public interface CommentService {
    /**
     * Создание нового комментария к задаче
     *
     * @param taskId  идентификатор задачи
     * @param commentDTO комментарий
     * @return комментарий
     */
    Comment create(Long taskId, CommentDTO commentDTO);

    /**
     * Получение списка всех комментариев
     *
     * @return комментарии
     */
    List<Comment> getAll();

    /**
     * Получение комментария по id
     *
     * @param id идентификатор комментария
     * @return комментарий
     */
    Comment getById(Long id);

    /**
     * Получение комментариев по id задачи
     *
     * @param taskId идентификатор задачи
     * @return список комментариев
     */
    List<Comment> getByTaskId(Long taskId);

    /**
     * Редактирование комментария
     *
     * @param commentDTO исправленный комментарий
     * @return комментарий из базы данных
     */
    Comment edit(CommentDTO commentDTO);
    /**
     * Удаление комментария по его id
     *
     * @param id идентификатор комментария
     */
    void deleteComment(Long id);
}
