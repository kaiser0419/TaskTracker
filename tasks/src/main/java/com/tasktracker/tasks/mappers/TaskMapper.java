package com.tasktracker.tasks.mappers;

import com.tasktracker.tasks.domain.dto.TaskDto;
import com.tasktracker.tasks.domain.entities.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDto toDto(Task task);

    Task fromDto(TaskDto dto);
}