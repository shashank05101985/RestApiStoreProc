package com.common.module.user.dao.impl;

import com.common.constants.CommonConstants;
import com.common.module.user.dao.RoleDAO;
import com.common.module.user.dto.Role;
import com.common.module.user.mapper.RoleRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;

@Repository
public class RoleDAOImpl implements RoleDAO {

    private static final String INSERT = "{call INSERT_ROLE(?,?,?)}";
    private static final String UPDATE = "{call UPDATE_ROLE(?,?)}";
    private static final String DEFAULT_ROLE = "{call GET_ROLE_BY_NAME(?)}";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Role getById(int id) {
        return jdbcTemplate.queryForObject(CommonConstants.GENERIC_PROC.GET_BY_ID, new Object[]{getTableName(), id}, new RoleRowMapper());
    }

    @Override
    public Collection<Role> getAll() {
        return jdbcTemplate.query(CommonConstants.GENERIC_PROC.GET_ALL, new Object[]{getTableName()}, new RoleRowMapper());
    }

    @Override
    public int save(Role role) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, role.getName());
            ps.setInt(2, role.getCreateBy());
            ps.setInt(3, role.getCreateBy());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void update(Role role) {
        jdbcTemplate.update(UPDATE, role.getName(), role.getId());
    }

    @Override
    public void updateStatus(int status, int id) {
        jdbcTemplate.update(CommonConstants.GENERIC_PROC.UPDATE_STATUS, status, id);
    }

    @Override
    public Role getDefaultRole() {
        return jdbcTemplate.queryForObject(DEFAULT_ROLE, new Object[]{"ROLE_USER"}, new RoleRowMapper());
    }

    @Override
    public String getTableName() {
        return "role";
    }

    @Override
    public void delete(int id) throws DataAccessException {
        jdbcTemplate.update(CommonConstants.GENERIC_PROC.DELETE, getTableName(), id);
    }


}
