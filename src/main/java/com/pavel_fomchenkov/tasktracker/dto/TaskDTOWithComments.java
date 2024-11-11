package com.pavel_fomchenkov.tasktracker.dto;

import com.pavel_fomchenkov.tasktracker.model.Priority;
import com.pavel_fomchenkov.tasktracker.model.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.Date;
import java.util.List;
// TODO Implement or remove this class

@Data
public class TaskDTOWithComments {
    private Long id;
    private String authorName;
    private Date startDate;
    private Date finishDate;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    private List<CommentDTO> comments;

    public TaskDTOWithComments(Long id, String authorName, Date startDate, Date finishDate,
                               String description, Status status, Priority priority, List<CommentDTO> comments) {
        this.id = id;
        this.authorName = authorName;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.comments = comments;
    }
}
