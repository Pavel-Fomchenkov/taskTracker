package com.pavel_fomchenkov.tasktracker.mapper;

import com.pavel_fomchenkov.tasktracker.dto.TaskDTO;
import com.pavel_fomchenkov.tasktracker.model.Task;
import com.pavel_fomchenkov.tasktracker.model.User;
import com.pavel_fomchenkov.tasktracker.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class TaskMapper {
    @Autowired
    UserService userService;

    @Named("mapToTaskDTO")
    @Mapping(target = "authorName", source = "author", qualifiedByName = "userToUsername")
    public abstract TaskDTO mapToTaskDTO(Task task);

    @Named("mapToTask")
    @Mapping(target = "author", source = "authorName", qualifiedByName = "authorNameToUser")
    public abstract Task mapToTask(TaskDTO taskDTO);

    @Named("userToUsername")
    String userToUsername(User user) {
        return user.getUsername();
    }

    @Named("authorNameToUser")
    User authorNameToUser(String authorName) {
        return userService.getByUsername(authorName);
    }
}
