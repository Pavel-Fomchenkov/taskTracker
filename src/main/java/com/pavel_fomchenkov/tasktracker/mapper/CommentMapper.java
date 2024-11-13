package com.pavel_fomchenkov.tasktracker.mapper;

import com.pavel_fomchenkov.tasktracker.dto.CommentDTO;
import com.pavel_fomchenkov.tasktracker.model.Comment;
import com.pavel_fomchenkov.tasktracker.model.User;
import jakarta.persistence.Column;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.Date;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {
    @Named("mapToCommentDTO")
    @Mapping(target = "authorName", source = "author", qualifiedByName = "userToUsername")
    CommentDTO mapToCommentDTO(Comment comment);

    @Named("userToUsername")
    default String userToUsername(User user) {
        return user.getUsername();
    }

}

