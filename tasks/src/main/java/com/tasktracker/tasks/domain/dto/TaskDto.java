package com.tasktracker.tasks.domain.dto;

import com.tasktracker.tasks.domain.entities.TaskPriority;
import com.tasktracker.tasks.domain.entities.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDto(    UUID id,
                          String title,
                          String description,
                          LocalDateTime dueDate,
                          TaskPriority priority,
                          TaskStatus status) {



}
