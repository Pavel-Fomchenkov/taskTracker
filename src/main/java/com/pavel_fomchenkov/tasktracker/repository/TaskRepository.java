package com.pavel_fomchenkov.tasktracker.repository;

import com.pavel_fomchenkov.tasktracker.dto.CommentDTO;
import com.pavel_fomchenkov.tasktracker.dto.TaskDTO;
import com.pavel_fomchenkov.tasktracker.dto.TaskDTOWithComments;
import com.pavel_fomchenkov.tasktracker.dto.UserDTO;
import com.pavel_fomchenkov.tasktracker.model.Priority;
import com.pavel_fomchenkov.tasktracker.model.Status;
import com.pavel_fomchenkov.tasktracker.model.Task;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "SELECT new com.pavel_fomchenkov.tasktracker.dto.TaskDTO(t.id, u.username, " +
            "t.startDate, t.finishDate, t.description, t.status, t.priority)" +
            " FROM Task t LEFT JOIN t.author u")
    List<TaskDTO> findAllDTO();

    List<Task> findByAuthorIdAndStatus(Long authorId, Status status);

    List<Task> findByAuthorId(Long authorId);

    @Query("SELECT t FROM Task t JOIN t.performers p WHERE p.id = :performerId")
    List<Task> findByPerformerId(Long performerId);

    @Query("SELECT t FROM Task t JOIN t.performers p WHERE p.id = :performerId AND t.status = :status")
    List<Task> findByPerformerIdAndStatus(Long performerId, Status status);

//    @Query(value = "SELECT new com.pavel_fomchenkov.tasktracker.dto.TaskDTOWithComments(t.id, u.username, " +
//            "t.startDate, t.finishDate, t.description, t.status, t.priority)" +
//            " FROM Task t LEFT JOIN t.author u WHERE t.id = :id ")
//    Optional<TaskDTOWithComments> findTaskDTOWithCommentsById(Long id);
}

