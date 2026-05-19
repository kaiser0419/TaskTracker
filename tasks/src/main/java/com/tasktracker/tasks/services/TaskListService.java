package com.tasktracker.tasks.services;

import com.tasktracker.tasks.domain.entities.TaskList;
import com.tasktracker.tasks.repository.TaskListRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskListService {

    private final TaskListRepository taskListRepository;

    public TaskListService(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    public List<TaskList> listTaskLists() {
        return taskListRepository.findAll();
    }

    public TaskList createTaskList(TaskList taskList){
        if(null != taskList.getId()){
            throw new IllegalArgumentException("Task list already has an ID");
        }

        if(null == taskList.getTitle() || taskList.getTitle().isBlank()){
            throw new IllegalArgumentException("Task list must have title");
        }

        LocalDateTime now = LocalDateTime.now();
        return taskListRepository.save(new TaskList(
                null, taskList.getTitle(), taskList.getDescription(),null,now,now
        ));

    }
}