package com.pavel_fomchenkov.tasktracker.controller;

import com.pavel_fomchenkov.tasktracker.model.User;
import com.pavel_fomchenkov.tasktracker.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Пользователи")
public class UserController {
    private final UserService service;
//    CREATE - AuthController
//    READ
@Operation(summary = "Получение информации об авторизованном пользователе")
//    ,
//        responses = {
//                @ApiResponse(
//                        responseCode = "200",
//                        description = "OK",
//                        content = @Content(
//                                mediaType = MediaType.APPLICATION_JSON_VALUE,
//                                schema = @Schema(implementation = User.class)
//                        )
//                ),
//                @ApiResponse(
//                        responseCode = "401",
//                        description = "Unauthorized",
//                        content = @Content()
//                )
//        }
//)
@GetMapping("me")
public ResponseEntity<User> getCurrentUser() {
    User user = service.getCurrentUser();
    return ResponseEntity.ok(user);
}

@GetMapping("Ur")
public String Ur() {
    return "Проверка";
}
//    UPDATE

//    DELETE

}
