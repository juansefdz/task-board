package com.TaskBoard.TaskBoard.repository;

import com.TaskBoard.TaskBoard.entity.Task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasksRepository extends JpaRepository<Task, Long> {
    Page<Task> findByTitle(String title, Pageable pageable);

    Page<Task> findByState(String state, Pageable pageable);
}
