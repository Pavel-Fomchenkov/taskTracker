package com.pavel_fomchenkov.tasktracker.controller;

import com.pavel_fomchenkov.tasktracker.dto.UserDTO;
import com.pavel_fomchenkov.tasktracker.mapper.UserMapper;
import com.pavel_fomchenkov.tasktracker.model.User;
import com.pavel_fomchenkov.tasktracker.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Пользователи")
public class UserController {
    private final UserService service;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    //    CREATE - AuthController
//    READ
    @GetMapping("all")
    @Operation(summary = "Получение информации о всех пользователях")
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserDTO> users = service.getAll().stream().map(mapper::mapToUserDTO).collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("me")
    @Operation(summary = "Получение информации об авторизованном пользователе")
    public ResponseEntity<UserDTO> getCurrent() {
        UserDTO user = mapper.mapToUserDTO(service.getCurrentUser());
        return ResponseEntity.ok(user);
    }

    @GetMapping("select")
    @Operation(summary = "Получение информации о пользователе по его id")
    public ResponseEntity<UserDTO> getById(@RequestParam Long id) {
        UserDTO user = mapper.mapToUserDTO(service.getById(id));
        return ResponseEntity.ok(user);
    }

    //    UPDATE
    @PatchMapping("me")
    @Operation(summary = "Обновление данных пользователя")
    public ResponseEntity<UserDTO> updateMe(@RequestBody UserDTO user) {
        user.setPassword(encoder.encode(user.getPassword()));
        UserDTO updatedUser = mapper.mapToUserDTO(service.updateMe(user));
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping
    @Operation(summary = "Обновление данных пользователя")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO user) {
        user.setPassword(encoder.encode(user.getPassword()));
        UserDTO updatedUser = mapper.mapToUserDTO(service.updateUser(user));
        return ResponseEntity.ok(updatedUser);
    }

    //    DELETE
    @DeleteMapping("me")
    @Operation(summary = "Удаление информации о текущем авторизованном пользователе")
    public ResponseEntity<String> delete() {
        service.deleteCurrentUser();
        return ResponseEntity.ok("Пользователь успешно удален");
    }

    @DeleteMapping()
    @Operation(summary = "Удаление информации о любом пользователе")
    public ResponseEntity<String> delete(@RequestParam Long id) {
        service.deleteUser(id);
        return ResponseEntity.ok("Пользователь id " + id + " отсутствует в базе данных");
    }
}
