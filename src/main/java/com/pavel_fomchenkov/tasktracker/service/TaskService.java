package com.pavel_fomchenkov.tasktracker.service;

import com.pavel_fomchenkov.tasktracker.dto.TaskDTO;
import com.pavel_fomchenkov.tasktracker.dto.TaskDTOWithComments;
import com.pavel_fomchenkov.tasktracker.model.Status;
import com.pavel_fomchenkov.tasktracker.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    /**
     * Создание новой задачи
     *
     * @return задача
     */
    Task create(Task task);

    /**
     * Создание новой задачи
     *
     * @return задача
     */
    Task create(TaskDTO taskDTO);

    /**
     * Получение списка всех задач
     *
     * @return задачи
     */
    List<TaskDTO> getAllDTO();

    /**
     * Получение задачи по id
     *
     * @param id идентификатор задачи
     * @return задача
     */
    Task getById(Long id);

    /**
     * Редактирование задачи
     *
     * @param task задача
     * @return обновленная задача
     */
    Task editTask(Task task);

    /**
     * Удаление задачи по id
     *
     * @param id идентификатор задачи
     */
    void deleteTask(Long id);

    /**
     * Добавление соисполнителя к задаче
     *
     * @param userId id соисполнителя
     * @param task   задача
     * @return задача
     */
    Task addPerformer(Long userId, Task task);

    /**
     * Удаление соисполнителя из задачи
     *
     * @param userId id соисполнителя
     * @param task   задача
     * @return задача
     */
    Task removePerformer(Long userId, Task task);

    /**
     * Получение списка задач по id автора и опционально статусу
     *
     * @param authorId id автора
     * @param status   статус задачи (опционально)
     * @return задача
     */
    List<Task> getByAuthorIdAndStatus(Long authorId, Status status);

    /**
     * Получение списка задач по id автора и опционально статусу
     *
     * @param authorId id автора
     * @return задача
     */
    List<Task> getByAuthorId(Long authorId);
}
