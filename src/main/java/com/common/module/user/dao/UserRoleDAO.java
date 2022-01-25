package com.common.module.user.dao;

import com.common.base.dao.BaseDAO;
import com.common.module.user.dto.Role;
import com.common.module.user.dto.UserRole;

import java.util.Collection;

public interface UserRoleDAO extends BaseDAO<UserRole> {

    Collection<Integer> getRoleIds(int userId);
    Collection<Role> getRole(int userId);
}
