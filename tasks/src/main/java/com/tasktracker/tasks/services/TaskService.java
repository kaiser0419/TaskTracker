package com.tasktracker.tasks.services;

import com.tasktracker.tasks.domain.entities.Task;
import com.tasktracker.tasks.domain.entities.TaskList;
import com.tasktracker.tasks.domain.entities.TaskPriority;
import com.tasktracker.tasks.domain.entities.TaskStatus;
import com.tasktracker.tasks.repository.TaskListRepository;
import com.tasktracker.tasks.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    public TaskService(TaskRepository taskRepository,
                       TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    public List<Task> listTasks(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }

    public Optional<Task> getTask(UUID taskListId, UUID taskId) {
        return taskRepository.findByTaskListIdAndId(taskListId, taskId);
    }

    public Task createTask(UUID taskListId, Task task) {

        if (null != task.getId()) {
            throw new IllegalArgumentException("Task already has an ID!");
        }

        if (null == task.getTitle() || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task must have a title!");
        }

        TaskPriority taskPriority = Optional.ofNullable(task.getPriority())
                .orElse(TaskPriority.MEDIUM);

        TaskStatus taskStatus = Optional.ofNullable(task.getStatus())
                .orElse(TaskStatus.OPEN);

        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid Task List ID provided!"));

        LocalDateTime now = LocalDateTime.now();

        Task taskToSave = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskStatus,
                taskPriority,
                taskList,
                now,
                now
        );

        return taskRepository.save(taskToSave);
    }

    public Task updateTask(UUID taskListId,
                           UUID taskId,
                           Task task) {

        if (null == task.getId()) {
            throw new IllegalArgumentException("Task must have an ID!");
        }

        if (!Objects.equals(taskId, task.getId())) {
            throw new IllegalArgumentException(
                    "Attempting to change task ID, this is not permitted!"
            );
        }

        Task existingTask = taskRepository
                .findByTaskListIdAndId(taskListId, taskId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Task not found!"));

        TaskPriority taskPriority = Optional.ofNullable(task.getPriority())
                .orElse(existingTask.getPriority());

        TaskStatus taskStatus = Optional.ofNullable(task.getStatus())
                .orElse(existingTask.getStatus());

        Task updatedTask = new Task(
                existingTask.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskStatus,
                taskPriority,
                existingTask.getTaskList(),
                existingTask.getCreated(),
                LocalDateTime.now()
        );

        return taskRepository.save(updatedTask);
    }

    public void deleteTask(UUID taskListId, UUID taskId) {

        Task existingTask = taskRepository
                .findByTaskListIdAndId(taskListId, taskId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Task not found!"));

        taskRepository.delete(existingTask);
    }
}