package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.exception.EntityNotFoundException;
import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.UserRepository;
import com.softserve.itacademy.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        if(user == null || user.getEmail() == null || user.getEmail().trim().isEmpty() || userRepository.getUserByEmail(user.getEmail()) == null)
            throw new NullEntityReferenceException("Cannot create empty user object");
        return userRepository.save(user);
    }

    @Override
    public User readById(long id) {
        Optional<User> optional = userRepository.findById(id);
        if(optional.isEmpty())
            throw new EntityNotFoundException("User with id: " + id + " does not exist");
        return optional.get();
    }

    @Override
    public User update(User user) {
        if(user == null)
            throw new NullEntityReferenceException("Cannot create empty user object");
        User oldUser = readById(user.getId());
        return userRepository.save(user);
    }

    @Override
    public void delete(long id) {
        User user = readById(id);
            userRepository.delete(user);
    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        return users.isEmpty() ? new ArrayList<>() : users;
    }

}
