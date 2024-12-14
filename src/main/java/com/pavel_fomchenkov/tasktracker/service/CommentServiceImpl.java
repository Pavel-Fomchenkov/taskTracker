package com.pavel_fomchenkov.tasktracker.service;

import com.pavel_fomchenkov.tasktracker.dto.CommentDTO;
import com.pavel_fomchenkov.tasktracker.exception.CommentNotFoundException;
import com.pavel_fomchenkov.tasktracker.exception.TaskNotFoundException;
import com.pavel_fomchenkov.tasktracker.model.Comment;
import com.pavel_fomchenkov.tasktracker.model.Role;
import com.pavel_fomchenkov.tasktracker.model.User;
import com.pavel_fomchenkov.tasktracker.repository.CommentRepository;
import com.pavel_fomchenkov.tasktracker.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.converter.json.GsonBuilderUtils;
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
     * @param taskId     идентификатор задачи
     * @param commentDTO комментарий
     * @return комментарий
     */
    @Override
    public Comment create(Long taskId, CommentDTO commentDTO) {
        User currentUser = userService.getCurrentUser();
        Comment comment = new Comment();
        comment.setTask(taskRepository.findById(taskId).orElseThrow(() ->
                new TaskNotFoundException("Задача id: " + taskId + " не найдена")));
        comment.setAuthor(currentUser);
        comment.setText(commentDTO.getText());
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
     * @param commentDTO исправленный комментарий
     * @return комментарий из базы данных
     */
//    TODO сделать выбрасывание исключения если нет доступа
    @Override
    public Comment edit(CommentDTO commentDTO) {
        User author = userService.getById(this.getById(commentDTO.getId()).getAuthor().getId());
        if (userService.validateAuthor(author)) {
            Comment commentFromDb = this.getById(commentDTO.getId());
            commentFromDb.setText(commentDTO.getText());
            commentFromDb.setDate(new Date());
            return repository.save(commentFromDb);
        }
        return null;
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
