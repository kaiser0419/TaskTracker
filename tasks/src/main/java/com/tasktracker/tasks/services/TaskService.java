package com.tasktracker.tasks.services;

import com.tasktracker.tasks.domain.dto.TaskDto;
import com.tasktracker.tasks.domain.entities.Task;
import com.tasktracker.tasks.mappers.TaskMapper;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskMapper taskMapper;

    public TaskService(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    public TaskDto getTask(Task task) {
        return taskMapper.toDto(task);
    }
}