package com.pavel_fomchenkov.tasktracker.service;

import com.pavel_fomchenkov.tasktracker.exception.CommentNotFoundException;
import com.pavel_fomchenkov.tasktracker.exception.TaskNotFoundException;
import com.pavel_fomchenkov.tasktracker.model.Comment;
import com.pavel_fomchenkov.tasktracker.model.Role;
import com.pavel_fomchenkov.tasktracker.model.User;
import com.pavel_fomchenkov.tasktracker.repository.CommentRepository;
import com.pavel_fomchenkov.tasktracker.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repository;
    private final TaskRepository taskRepository;
    private final UserService userService;

    /**
     * Создание нового комментария к задаче
     *
     * @param taskId  идентификатор задачи
     * @param comment комментарий
     * @return комментарий
     */
    @Override
    public Comment create(Long taskId, Comment comment) {
        comment.setTask(taskRepository.findById(taskId).orElseThrow(() ->
                new TaskNotFoundException("Задача id: " + taskId + " не найдена")));
        comment.setAuthor(userService.getCurrentUser());
        comment.setDate(new Date());
        return repository.save(comment);
    }

    /**
     * Получение списка всех комментариев
     *
     * @return комментарии
     */
    @Override
    public List<Comment> getAll() {
        return repository.findAll();
    }

    /**
     * Получение комментария по id
     *
     * @param id идентификатор комментария
     * @return комментарий
     */
    @Override
    public Comment getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new CommentNotFoundException("Комментарий id: " + id +
                " не найден"));
    }

    /**
     * Получение комментариев по id задачи
     *
     * @param taskId идентификатор задачи
     * @return список комментариев
     */
    @Override
    public List<Comment> getByTaskId(Long taskId) {
        return repository.findAllByTaskId(taskId);
    }

    /**
     * Редактирование комментария
     *
     * @param comment исправленный комментарий
     * @return комментарий из базы данных
     */
    @Override
    public Comment edit(Comment comment) {
        User currentUser = userService.getCurrentUser();
        if (!currentUser.equals(comment.getAuthor()) && !currentUser.getRole().equals(Role.ROLE_ADMIN)) {
            throw new AccessDeniedException("Доступ запрещен");
        }
        Comment commentFromDb = this.getById(comment.getId());
        commentFromDb.setText(comment.getText());
        commentFromDb.setDate(new Date());
        return repository.save(commentFromDb);
    }

    /**
     * Удаление комментария по его id
     *
     * @param id идентификатор комментария
     */
    @Override
    public void deleteComment(Long id) {
        repository.deleteById(id);
    }
}
