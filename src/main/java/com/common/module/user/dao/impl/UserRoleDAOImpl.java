package com.common.module.user.dao.impl;

import com.common.constants.CommonConstants;
import com.common.module.user.dao.UserRoleDAO;
import com.common.module.user.dto.Role;
import com.common.module.user.dto.UserRole;
import com.common.module.user.mapper.RoleRowMapper;
import com.common.module.user.mapper.UserRoleRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

@Repository
public class UserRoleDAOImpl implements UserRoleDAO {

    private final String INSERT = "{call INSERT_USER_ROLE(?,?,?,?)}";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserRole getById(int id) {
        return jdbcTemplate.queryForObject(CommonConstants.GENERIC_PROC.GET_BY_ID,new Object[]{getTableName(),id},new UserRoleRowMapper());
    }

    @Override
    public Collection<UserRole> getAll() {
        return jdbcTemplate.query(CommonConstants.GENERIC_PROC.GET_ALL, new UserRoleRowMapper());
    }
    @Override
    public int save(UserRole userRole) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            addData(ps,userRole);
            return ps;
        }, keyHolder);
        return  keyHolder.getKey().intValue();
    }

    @Override
    public String getTableName() {
        return "user_role";
    }

    @Override
    public void batchSave(List<UserRole> userRoles) {
        jdbcTemplate.batchUpdate(INSERT, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                UserRole userRole = userRoles.get(i);
                addData(ps,userRole);
            }
            @Override
            public int getBatchSize() {
                return userRoles.size();
            }
        });
    }
    private void addData(PreparedStatement ps, UserRole userRole) throws SQLException {
        ps.setInt(1, userRole.getUserId());
        ps.setInt(2, userRole.getRoleId());
        ps.setInt(3,userRole.getCreateBy());
        ps.setInt(4,userRole.getUpdateBy());
    }
    @Override
    public void updateStatus(int status, int id) {
        jdbcTemplate.update(CommonConstants.GENERIC_PROC.UPDATE_STATUS,status,id);
    }

    @Override
    public Collection<Integer> getRoleIds(int userId) {
        return jdbcTemplate.queryForList("{call GET_USER_ROLE_ID_BY_ID(?)}", new Object[]{userId}, Integer.class);
    }
    @Override
    public Collection<Role> getRole(int userId) {
       return jdbcTemplate.query("{call GET_USER_ROLE_BY_ID(?)}", new Object[]{userId}, new RoleRowMapper());
    }

}
