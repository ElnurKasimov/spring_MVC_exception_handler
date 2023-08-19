package com.softserve.itacademy.service.impl;


import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.repository.RoleRepository;
import com.softserve.itacademy.service.RoleService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role create(Role role) {
        if(role == null || role.getName() == null || role.getName().trim().isEmpty())
            throw new NullEntityReferenceException("Cannot create empty state object");
        return roleRepository.save(role);
    }

    @Override
    public Role readById(long id) {
        Optional<Role> optional = roleRepository.findById(id);
        if(optional.isEmpty())
            throw new EntityNotFoundException("Task with id: " + id + " does not exist");
        return optional.get();
    }

    @Override
    public Role update(Role role) {
        if(role == null)
            throw new NullEntityReferenceException("Cannot update empty state object");
        Role oldRole = readById(role.getId());
        return roleRepository.save(role);
    }

    @Override
    public void delete(long id) {
        Role role = readById(id);
            roleRepository.delete(role);
    }

    @Override
    public List<Role> getAll() {
        List<Role> roles = roleRepository.findAll();
        return roles.isEmpty() ? new ArrayList<>() : roles;
    }
}
