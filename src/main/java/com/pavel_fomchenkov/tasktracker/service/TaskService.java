package com.pavel_fomchenkov.tasktracker.service;

import com.pavel_fomchenkov.tasktracker.dto.TaskDTO;
import com.pavel_fomchenkov.tasktracker.dto.TaskDTOWithComments;
import com.pavel_fomchenkov.tasktracker.model.Task;

import java.util.List;

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
}
