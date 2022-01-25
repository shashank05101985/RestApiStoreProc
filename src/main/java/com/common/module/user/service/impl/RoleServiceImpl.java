package com.common.module.user.service.impl;

import com.common.module.user.dao.RoleDAO;
import com.common.module.user.dto.Role;
import com.common.module.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDAO roleDAO;

    @Override
    public Role getById(int id) {
        return roleDAO.getById(id);
    }

    @Override
    public Collection<Role> getAll() {
        return roleDAO.getAll();
    }

    @Override
    public int save(Role role) {
        int id = roleDAO.save(role);
        role.setId(id);
        return id;
    }

    @Override
    public Role getDefaultRole() {
        return roleDAO.getDefaultRole();
    }
}
