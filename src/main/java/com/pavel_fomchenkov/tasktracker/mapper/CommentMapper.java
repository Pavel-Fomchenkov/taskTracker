package com.pavel_fomchenkov.tasktracker.mapper;

import com.pavel_fomchenkov.tasktracker.dto.CommentDTO;
import com.pavel_fomchenkov.tasktracker.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {
    @Named("mapToCommentDTO")
    CommentDTO mapToCommentDTO(Comment comment);
}

