package com.tasktracker.tasks.services;

import com.tasktracker.tasks.domain.entities.TaskList;
import com.tasktracker.tasks.repository.TaskListRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskListService {

    private final TaskListRepository taskListRepository;

    public TaskListService(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }


    public List<TaskList> listTaskLists() {
        return taskListRepository.findAll();
    }

    public Optional<TaskList> getTaskList(UUID id){
        return taskListRepository.findById(id);
    }

    public TaskList updateTaskList(UUID taskListId, TaskList taskList){
        if(null == taskList.getId()){
            throw new IllegalArgumentException("Task list must have an ID");
        }

        if(!Objects.equals(taskList.getId(),taskListId)){
            throw new IllegalArgumentException("attempting to change task list id, this is not permitted");
        }

        TaskList existingTaskList = taskListRepository.findById(taskListId).orElseThrow(() ->
                new IllegalArgumentException("task list not found"));

        existingTaskList.setTitle(taskList.getTitle());
        existingTaskList.setDescription(taskList.getDescription());
        existingTaskList.setUpdated(LocalDateTime.now());

        return taskListRepository.save(existingTaskList);
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

    public void deleteTaskList(UUID taskListId){
        taskListRepository.deleteById(taskListId);

    }




}