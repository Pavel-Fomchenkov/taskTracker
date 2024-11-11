package com.pavel_fomchenkov.tasktracker.dto;

import com.pavel_fomchenkov.tasktracker.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private User author;
    private String text;
}
