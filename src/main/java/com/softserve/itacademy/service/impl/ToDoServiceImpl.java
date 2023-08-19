package com.softserve.itacademy.service.impl;


import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.repository.ToDoRepository;
import com.softserve.itacademy.service.ToDoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ToDoServiceImpl implements ToDoService {

    private ToDoRepository todoRepository;

    public ToDoServiceImpl(ToDoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public ToDo create(ToDo todo) {
        if(todo == null)
            throw new NullEntityReferenceException("Cannot create empty todo object");
        try{
            return todoRepository.save(todo);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ToDo readById(long id) {
        Optional<ToDo> optional = todoRepository.findById(id);
        if(optional.isEmpty())
            throw new EntityNotFoundException("Todo with id " + id + " does not exist");
        return optional.get();
    }

    @Override
    public ToDo update(ToDo todo) {
        if(todo == null)
            throw new NullEntityReferenceException("Cannot create empty todo object");
        try {
            ToDo oldTodo = readById(todo.getId());
            return todoRepository.save(todo);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(long id) {
        try{
            ToDo todo = readById(id);
            todoRepository.delete(todo);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ToDo> getAll() {
        List<ToDo> todos = todoRepository.findAll();
        return todos.isEmpty() ? new ArrayList<>() : todos;
    }

    @Override
    public List<ToDo> getByUserId(long userId) {
        List<ToDo> todos = todoRepository.getByUserId(userId);
        return todos.isEmpty() ? new ArrayList<>() : todos;
    }
}
