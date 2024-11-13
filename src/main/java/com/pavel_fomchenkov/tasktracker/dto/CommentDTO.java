package com.pavel_fomchenkov.tasktracker.dto;

import com.pavel_fomchenkov.tasktracker.model.User;
import lombok.Data;

import java.util.Date;

@Data
public class CommentDTO {
    private Long id;
    private String authorName;
    private String text;
    private Date date;
}
