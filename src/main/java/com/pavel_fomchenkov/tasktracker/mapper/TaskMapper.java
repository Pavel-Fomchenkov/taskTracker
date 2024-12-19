package com.pavel_fomchenkov.tasktracker.mapper;

import com.pavel_fomchenkov.tasktracker.dto.*;
import com.pavel_fomchenkov.tasktracker.model.Comment;
import com.pavel_fomchenkov.tasktracker.model.Task;
import com.pavel_fomchenkov.tasktracker.model.User;
import com.pavel_fomchenkov.tasktracker.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class TaskMapper {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentMapper commentMapper;

    @Named("mapToTaskDTO")
    @Mapping(target = "authorName", source = "author", qualifiedByName = "userToUsername")
    public abstract TaskDTO mapToTaskDTO(Task task);

    @Named("mapToTaskDTOWithComments")
    @Mapping(target = "authorName", source = "author", qualifiedByName = "userToUsername")
    @Mapping(target = "performers", source = "performers", qualifiedByName = "mapPerformers")
    @Mapping(target = "comments", source = "comments", qualifiedByName = "mapComments")
    public abstract TaskDTOWithComments mapToTaskDTOWithComments(Task task);

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

    @Named("mapPerformers")
    Collection<UserDTOMin> mapPerformers(Collection<User> performers) {
        return performers.stream().map(p -> userMapper.mapToUserDTOMin(p)).collect(Collectors.toSet());
    }

    @Named("mapComments")
    Collection<CommentDTO> mapComments(Collection<Comment> comments) {
        return comments.stream().map(c -> commentMapper.mapToCommentDTO(c)).collect(Collectors.toList());
    }


}
