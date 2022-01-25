package com.common.module.user.dto;

import com.common.base.dto.BaseDTO;

public class UserRole extends BaseDTO {

    private int userId;

    private int roleId;
    public UserRole(int userId, int roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
    public UserRole() {
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
