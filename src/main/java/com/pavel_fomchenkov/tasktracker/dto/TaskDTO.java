package com.pavel_fomchenkov.tasktracker.dto;

import com.pavel_fomchenkov.tasktracker.model.Priority;
import com.pavel_fomchenkov.tasktracker.model.Status;
import jakarta.persistence.*;
import jdk.jfr.Name;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@Data
public class TaskDTO {

    public TaskDTO(Long id, String authorName, Date startDate, Date finishDate,
                   String description, Status status, Priority priority) {
        this.id = id;
        this.authorName = authorName;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.description = description;
        this.status = status;
        this.priority = priority;
    }

    private Long id;
    private String authorName;
    private Date startDate;
    private Date finishDate;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Priority priority;
}
