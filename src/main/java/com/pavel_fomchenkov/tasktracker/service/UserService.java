package com.pavel_fomchenkov.tasktracker.service;

import com.pavel_fomchenkov.tasktracker.dto.UserDTO;
import com.pavel_fomchenkov.tasktracker.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    User save(User user);

    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    User create(User user);

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    User getByUsername(String username);

    /**
     * Получение пользователя по id пользователя
     *
     * @return пользователь
     */
    User getById(Long id);

    /**
     * Получение списка всех пользователей
     *
     * @return пользователи
     */
    List<User> getAll();

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    UserDetailsService userDetailsService();

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    User getCurrentUser();

    /**
     * Изменение данных текущего пользователя
     *
     * @param userDTO пользователь
     * @return пользователь из базы данных
     */
    User updateMe(UserDTO userDTO);

    /**
     * Изменение данных пользователя
     *
     * @param userDTO пользователь
     * @return пользователь из базы данных
     */
    User updateUser(UserDTO userDTO);

    /**
     * Удаление текущего пользователя
     * <p>
     */
    void deleteCurrentUser();

    /**
     * Удаление пользователя по id
     * <p>
     */
    void deleteUser(Long id);

    /**
     * Выдача прав администратора текущему пользователю
     * <p>
     * Нужен для демонстрации
     */
    @Deprecated
    void getAdmin();

    boolean isUserAdmin();

    /**
     * Проверка наличия пользователя в базе по id
     *
     * @param id id пользователя
     * @return true если пользователь существует
     */
    boolean exists(Long id);

    /**
     * Проверка прав доступа к контенту
     *
     * @param author автор редактируемого контента
     * @return true если пользователь является автором или админом
     */
    boolean validateAuthor(User author);
}
