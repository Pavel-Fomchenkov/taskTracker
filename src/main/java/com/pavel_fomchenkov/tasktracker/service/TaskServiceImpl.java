package com.pavel_fomchenkov.tasktracker.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pavel_fomchenkov.tasktracker.dto.TaskDTO;
import com.pavel_fomchenkov.tasktracker.exception.TaskNotFoundException;
import com.pavel_fomchenkov.tasktracker.mapper.TaskMapper;
import com.pavel_fomchenkov.tasktracker.model.*;
import com.pavel_fomchenkov.tasktracker.repository.TaskRepository;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository repository;
    private final UserService userService;
    private final TaskMapper mapper;

    /**
     * Создание новой задачи
     *
     * @return задача
     */
    @Override
    public Task create(TaskDTO taskDTO) {
        taskDTO.setAuthorName(SecurityContextHolder.getContext().getAuthentication().getName());
        if (taskDTO.getStartDate() == null ||
                taskDTO.getStartDate().before(new Date())) {
            taskDTO.setStartDate(new Date());
        }
        taskDTO.setStatus(Status.ACTIVE);
        return repository.save(mapper.mapToTask(taskDTO));
    }

    /**
     * Создание новой задачи
     *
     * @return задача
     */
    @Override
    public Task create(Task task) {
        task.setAuthor(userService.getCurrentUser());
        if (task.getStartDate() == null || task.getStartDate().before(new Date())) {
            task.setStartDate(new Date());
        }
        task.setStatus(Status.ACTIVE);
        return repository.save(task);
    }

    /**
     * Получение списка всех задач
     *
     * @return задачи
     */
    @Override
    public List<TaskDTO> getAllDTO() {
        return repository.findAllDTO();
    }

    /**
     * Получение задачи по id
     *
     * @param id идентификатор задачи
     * @return задача
     */
    @Override
    public Task getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new TaskNotFoundException("Задача id: " + id + " не найдена"));
    }

    /**
     * Редактирование задачи
     *
     * @param task задача
     * @return обновленная задача
     */
    @Override
    public Task editTask(Task task) {
        Task taskFromBD = this.getById(task.getId());
        boolean isAdmin = isAdmin();
        if (isAdmin || taskFromBD.getAuthor().getUsername().equals(getCurrentUserName())) {
            if (isAdmin) taskFromBD.setAuthor(task.getAuthor());
            taskFromBD.setStartDate(task.getStartDate());
            taskFromBD.setFinishDate(task.getFinishDate());
            taskFromBD.setDescription(task.getDescription());
            taskFromBD.setStatus(task.getStatus());
            taskFromBD.setPriority(task.getPriority());
            return repository.save(taskFromBD);
        } else throw new TaskNotFoundException("У пользователя нет прав на изменение задачи id " + task.getId());
    }
// TODO необходимо добавить методы добавления и удаления соисполнителей в задачу
//    TODO нужно переделать методы, чтобы пользователю возвращались задачи в укороченном виде
//     TODO + (без комментариев, с коротким списком соисполнителей)

    /**
     * Удаление задачи по id
     * <p>
     */
    @Override
    public void deleteTask(Long id) {
        repository.deleteById(id);
    }


    private String getCurrentUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private Boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
    }

}