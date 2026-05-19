package com.tasktracker.tasks.mappers;

import com.tasktracker.tasks.domain.dto.TaskListDto;
import com.tasktracker.tasks.domain.entities.Task;
import com.tasktracker.tasks.domain.entities.TaskList;
import com.tasktracker.tasks.domain.entities.TaskStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = TaskMapper.class)
public interface TaskListMapper {

    @Mapping(target = "count",
            expression = "java(taskList.getTasks() != null ? taskList.getTasks().size() : 0)")
    @Mapping(target = "progress",
            expression = "java(calculateTaskListProgress(taskList.getTasks()))")
    TaskListDto toDto(TaskList taskList);

    TaskList fromDto(TaskListDto dto);

    default Double calculateTaskListProgress(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            return 0.0;
        }

        long closedTaskCount = tasks.stream()
                .filter(task -> TaskStatus.CLOSED == task.getStatus())
                .count();

        return (double) closedTaskCount / tasks.size();
    }
}