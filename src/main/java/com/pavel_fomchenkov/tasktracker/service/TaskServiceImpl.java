package com.pavel_fomchenkov.tasktracker.service;

import com.pavel_fomchenkov.tasktracker.dto.TaskDTO;
import com.pavel_fomchenkov.tasktracker.exception.TaskNotFoundException;
import com.pavel_fomchenkov.tasktracker.mapper.TaskMapper;
import com.pavel_fomchenkov.tasktracker.model.Status;
import com.pavel_fomchenkov.tasktracker.model.Task;
import com.pavel_fomchenkov.tasktracker.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
//        task.setAuthor( SecurityContextHolder.getContext().getAuthentication().getName());
//        if (taskDTO.getStartDate() == null ||
//                taskDTO.getStartDate().before(new Date())) {
//            taskDTO.setStartDate(new Date());
//        }
//        taskDTO.setStatus(Status.ACTIVE);
//        return repository.save(mapper.mapToTask(taskDTO));
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
     * @param id идентификатор задачи
     * @return задача
     */
    @Override
    public Task getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new TaskNotFoundException("Задача id: " + id + " не найдена"));
    }

    /**
     * Удаление задачи по id
     * <p>
     */
    @Override
    public void deleteTask(Long id) {
        repository.deleteById(id);
    }
}
