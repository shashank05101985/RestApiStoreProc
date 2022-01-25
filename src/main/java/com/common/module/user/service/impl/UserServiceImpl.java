package com.common.module.user.service.impl;

import com.common.auth.dto.SignUpRequest;
import com.common.auth.service.AuthUser;
import com.common.module.user.dao.UserDAO;
import com.common.module.user.dto.Role;
import com.common.module.user.dto.User;
import com.common.module.user.dto.UserRole;
import com.common.module.user.service.RoleService;
import com.common.module.user.service.UserRoleService;
import com.common.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;

    Map<String, AuthUser> authMap = new HashMap<>();

    @Override
    public User getById(int id) {
        return userDAO.getById(id);
    }

    @Override
    public Collection<User> getAll() {
        return userDAO.getAll();
    }

    @Override
    public int save(User user) {
        int id = userDAO.save(user);
        user.setId(id);
        return id;
    }
    @Override
    public User getByEmail(String email) {
        return userDAO.getByEmail(email);
    }

    @Override
    @Transactional
    public int saveUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        List<Integer> roles = signUpRequest.getRoles();
        if (roles == null) {
            roles = new LinkedList<>();
            Role role = roleService.getDefaultRole();
            if(role!=null) {
                roles.add(role.getId());
            }
        }
        int userId = userDAO.save(user);
        List<UserRole> userRoles = new LinkedList<>();
        roles.forEach(id -> {
            UserRole userRole = new UserRole(userId, id);
            userRole.setCreateBy(1);
            userRole.setUpdateBy(1);
            userRoles.add(userRole);
        });
        userRoleService.saveUserRoles(userRoles);
        return userId;
    }

}
