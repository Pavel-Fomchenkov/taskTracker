package com.pavel_fomchenkov.tasktracker.dto;

import com.pavel_fomchenkov.tasktracker.model.Priority;
import com.pavel_fomchenkov.tasktracker.model.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

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
    private Collection<UserDTOMin> performers;
    private Collection<CommentDTO> comments;

    public TaskDTOWithComments(Long id, String authorName, Date startDate, Date finishDate, String description, Status status,
                               Priority priority, Collection<UserDTOMin> performers, Collection<CommentDTO> comments) {
        this.id = id;
        this.authorName = authorName;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.performers = performers;
        this.comments = comments;
    }
}
