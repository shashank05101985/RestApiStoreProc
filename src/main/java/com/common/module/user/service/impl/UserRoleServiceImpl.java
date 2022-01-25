package com.common.module.user.service.impl;

import com.common.module.user.dao.RoleDAO;
import com.common.module.user.dao.UserRoleDAO;
import com.common.module.user.dto.Role;
import com.common.module.user.dto.UserRole;
import com.common.module.user.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleDAO userRoleDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Override
    public UserRole getById(int id) {
        return userRoleDAO.getById(id);
    }

    @Override
    public Collection<UserRole> getAll() {
        return userRoleDAO.getAll();
    }

    @Override
    public int save(UserRole userRole) {
        int id =  userRoleDAO.save(userRole);
        userRole.setRoleId(id);
        return id;
    }
    @Override
    public Collection<String> getAllRoleAndPermissions() {
        return null;
    }

    @Override
    public Collection<String> getAllRoleByUserId(int userId) {
        Collection<Role> roles= userRoleDAO.getRole(userId);
        Collection<String> roleName = new LinkedList<>();
        if(CollectionUtils.isEmpty(roles)) return roleName;
        roles.forEach(role->{
            roleName.add(role.getName());
        });
        return roleName;
    }


    @Override
    public void saveUserRoles(List<UserRole> userRoles) {
        userRoleDAO.batchSave(userRoles);
    }
}
