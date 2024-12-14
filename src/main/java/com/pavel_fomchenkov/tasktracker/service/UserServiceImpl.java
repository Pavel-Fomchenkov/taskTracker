package com.pavel_fomchenkov.tasktracker.service;

import com.pavel_fomchenkov.tasktracker.dto.UserDTO;
import com.pavel_fomchenkov.tasktracker.exception.UserAlreadyExistsException;
import com.pavel_fomchenkov.tasktracker.model.Role;
import com.pavel_fomchenkov.tasktracker.model.User;
import com.pavel_fomchenkov.tasktracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    @Override
    public User save(User user) {
        return repository.save(user);
    }


    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    @Override
    public User create(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("Пользователь с таким именем уже существует");
        }
        if (repository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("Пользователь с таким email уже существует");
        }
        return save(user);
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    @Override
    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }

    /**
     * Получение пользователя по id пользователя
     *
     * @return пользователь
     */
    @Override
    public User getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    /**
     * Получение списка всех пользователей
     *
     * @return пользователи
     */
    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    @Override
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    @Override
    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    /**
     * Изменение данных текущего пользователя
     *
     * @param userDTO пользователь
     * @return пользователь из базы данных
     */
    @Override
    public User updateMe(UserDTO userDTO) {
// валидировать новые данные на предмет соответствия паттернам
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User userFromDb = this.getByUsername(currentUserName);
        userFromDb.setUsername(userDTO.getUsername());
        userFromDb.setPassword(userDTO.getPassword());
        userFromDb.setEmail(userDTO.getEmail());
        if (isUserAdmin()) {
            userFromDb.setRole(userDTO.getRole());
        }
        repository.save(userFromDb);
        return userFromDb;
    }

    /**
     * Изменение данных пользователя
     *
     * @param userDTO пользователь
     * @return пользователь из базы данных
     */
    @Override
    public User updateUser(UserDTO userDTO) {
// валидировать новые данные на предмет соответствия паттернам
        if (isUserAdmin()) {
            User userFromDb = this.getById(userDTO.getId());
            userFromDb.setUsername(userDTO.getUsername());
            userFromDb.setPassword(userDTO.getPassword());
            userFromDb.setEmail(userDTO.getEmail());
            userFromDb.setRole(userDTO.getRole());
            repository.save(userFromDb);
            return userFromDb;
        }
        return null;
    }

    /**
     * Удаление текущего пользователя
     * <p>
     */
    @Override
    @Transactional
    public void deleteCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        repository.deleteByUsername(username);
    }

    /**
     * Удаление пользователя по id
     * <p>
     */
    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    /**
     * Выдача прав администратора текущему пользователю
     * <p>
     * Нужен для демонстрации
     */
    @Deprecated
    @Override
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        save(user);
    }

    /**
     * Проверка текущего пользователя на наличие роли ADMIN
     * <p>
     * return true если админ
     */
    @Override
    public boolean isUserAdmin() {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    /**
     * Проверка наличия пользователя в базе по id
     *
     * @param id id пользователя
     * @return true если пользователь существует
     */
    @Override
    public boolean exists(Long id) {
        return repository.existsById(id);
    }

    /**
     * Проверка прав доступа к контенту
     *
     * @param author автор редактируемого контента
     * @return true если пользователь является автором или админом
     */
    @Override
    public boolean validateAuthor(User author) {
        User currentUser = getCurrentUser();
        if (!currentUser.equals(author) && !currentUser.getRole().equals(Role.ROLE_ADMIN)) {
            throw new AccessDeniedException("Доступ запрещен");
        }
        return true;
    }
}
