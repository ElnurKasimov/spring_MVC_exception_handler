package com.softserve.itacademy.service.impl;


import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.State;
import com.softserve.itacademy.repository.StateRepository;
import com.softserve.itacademy.service.StateService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StateServiceImpl implements StateService {
    private StateRepository stateRepository;

    public StateServiceImpl(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public State create(State state) {
        if(state == null)
            throw new NullEntityReferenceException("Cannot create empty state object");
        try {
            return stateRepository.save(state);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public State readById(long id) {
        Optional<State> optional = stateRepository.findById(id);
        if(optional.isEmpty())
            throw new EntityNotFoundException("User with id: " + id + " does not exist");
        return optional.get();
    }

    @Override
    public State update(State state) {
        if(state == null)
            throw new NullEntityReferenceException("Cannot create empty state object");
        try {
            State oldState = readById(state.getId());
            return stateRepository.save(state);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(long id) {
        try {
            State state = readById(id);
            stateRepository.delete(state);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public State getByName(String name) {
        Optional<State> optional = Optional.ofNullable(stateRepository.getByName(name));
            return optional.get();
    }

    @Override
    public List<State> getAll() {
        List<State> states = stateRepository.getAll();
        return states.isEmpty() ? new ArrayList<>() : states;
    }
}
