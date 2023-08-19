package com.softserve.itacademy.service.impl;


import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.State;
import com.softserve.itacademy.repository.StateRepository;
import com.softserve.itacademy.service.StateService;
import org.springframework.stereotype.Service;

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
        if(state == null || state.getName() == null || state.getName().trim().isEmpty())
            throw new NullEntityReferenceException("Cannot create empty state object");
        return stateRepository.save(state);
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
        State oldState = readById(state.getId());
        return stateRepository.save(state);
    }

    @Override
    public void delete(long id) {
        State state = readById(id);
            stateRepository.delete(state);
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
