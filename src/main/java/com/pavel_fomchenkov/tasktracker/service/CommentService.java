package com.pavel_fomchenkov.tasktracker.service;

import com.pavel_fomchenkov.tasktracker.model.Comment;

import java.util.List;

public interface CommentService {
    /**
     * Создание нового комментария к задаче
     *
     * @param taskId  идентификатор задачи
     * @param comment комментарий
     * @return комментарий
     */
    Comment create(Long taskId, Comment comment);

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
     * Редактирование комментария
     *
     * @param comment исправленный комментарий
     * @return комментарий из базы данных
     */
    Comment edit(Comment comment);

    /**
     * Удаление комментария по его id
     *
     * @param id идентификатор комментария
     */
    void deleteComment(Long id);
}
