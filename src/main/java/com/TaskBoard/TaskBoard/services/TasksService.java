package com.TaskBoard.TaskBoard.services;

import com.TaskBoard.TaskBoard.entity.Task;
import com.TaskBoard.TaskBoard.repository.TasksRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TasksService {

    @Autowired
    private TasksRepository objTasksRepository;

    public List<Task> findAll() {
        return this.objTasksRepository.findAll();
    }

    public Page<Task> findPaginated(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page, size);
        if (name != null && !name.isEmpty()) {
            return objTasksRepository.findByTitle(name, pageable);
        } else {
            return objTasksRepository.findAll(pageable);
        }
    }

    public Page<Task> findPaginatedByState(int page, int size, String state) {
        Pageable pageable = PageRequest.of(page, size);
        return objTasksRepository.findByState(state, pageable);
    }

    public Task insert(Task objTask) {
        return this.objTasksRepository.save(objTask);
    }

    public void delete(Long id) {
        this.objTasksRepository.deleteById(id);
    }

    public Task update(Long id, Task objTask) {
        Task objTaskDB = this.findByid(id);
        if (objTaskDB == null) return null;
        objTaskDB.setTitle(objTask.getTitle());
        objTaskDB.setDescription(objTask.getDescription());
        objTaskDB.setDate(objTask.getDate());
        objTaskDB.setTime(objTask.getTime());
        objTaskDB.setState(objTask.getState());
        return this.objTasksRepository.save(objTaskDB);
    }

    public Task findByid(Long id) {
        return this.objTasksRepository.findById(id).orElse(null);
    }

    public void updateTaskState(Long id, String state) {
        Task task = objTasksRepository.findById(id).orElse(null);
        if (task != null) {
            task.setState(state);
            objTasksRepository.save(task);
        }
    }
}




