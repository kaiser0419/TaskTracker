package com.tasktracker.tasks.repository;

import com.tasktracker.tasks.domain.entities.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskListRepository extends JpaRepository<TaskList, UUID> {
}