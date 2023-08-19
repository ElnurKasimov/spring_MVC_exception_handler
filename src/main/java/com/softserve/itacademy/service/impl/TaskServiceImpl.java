package com.softserve.itacademy.service.impl;


import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.repository.TaskRepository;
import com.softserve.itacademy.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task create(Task user) {
        if(user == null)
            throw new NullEntityReferenceException("Cannot create empty task object");
        try {
            return taskRepository.save(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Task readById(long id) {
        Optional<Task> optional = taskRepository.findById(id);
        if(optional.isEmpty())
            throw new EntityNotFoundException("Task with id: " + id + " does not exist");
        return optional.get();
    }

    @Override
    public Task update(Task task) {
        if(task == null)
            throw new NullEntityReferenceException("Cannot create empty task object");
        try {
            Task oldTask = readById(task.getId());
            return taskRepository.save(task);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(long id) {
        try {
            Task task = readById(id);
            taskRepository.delete(task);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Task> getAll() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.isEmpty() ? new ArrayList<>() : tasks;
    }

    @Override
    public List<Task> getByTodoId(long todoId) {
        List<Task> tasks = taskRepository.getByTodoId(todoId);
        return tasks.isEmpty() ? new ArrayList<>() : tasks;
    }
}
