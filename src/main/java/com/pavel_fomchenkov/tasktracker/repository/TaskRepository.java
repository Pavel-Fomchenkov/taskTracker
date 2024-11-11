package com.pavel_fomchenkov.tasktracker.repository;

import com.pavel_fomchenkov.tasktracker.dto.TaskDTO;
import com.pavel_fomchenkov.tasktracker.dto.TaskDTOWithComments;
import com.pavel_fomchenkov.tasktracker.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "SELECT new com.pavel_fomchenkov.tasktracker.dto.TaskDTO(t.id, u.username, " +
            "t.startDate, t.finishDate, t.description, t.status, t.priority)" +
            " FROM Task t LEFT JOIN t.author u")
    List<TaskDTO> findAllDTO();

    @Query(value = "SELECT new com.pavel_fomchenkov.tasktracker.dto.TaskDTO(t.id, u.username, " +
            "t.startDate, t.finishDate, t.description, t.status, t.priority)" +
            " FROM Task t LEFT JOIN t.author u WHERE t.id = :id ")
    Optional<TaskDTOWithComments> findTaskDTOWithCommentsById(Long id);
}
