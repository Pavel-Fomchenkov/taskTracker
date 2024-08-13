package com.pavel_fomchenkov.tasktracker.service;

import com.pavel_fomchenkov.tasktracker.model.Task;

import java.util.List;

public interface TaskService {
    List<Task> getAll();
}
