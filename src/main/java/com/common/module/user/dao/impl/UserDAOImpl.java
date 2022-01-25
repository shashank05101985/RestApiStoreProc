package com.common.module.user.dao.impl;

import com.common.constants.CommonConstants;
import com.common.module.user.dao.UserDAO;
import com.common.module.user.dto.User;
import com.common.module.user.mapper.UserRowMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class UserDAOImpl implements UserDAO {

    private static final String INSERT = "{call INSERT_USER(?,?,?,?,?,?)}";
    private static final String UPDATE = "{call UPDATE_USER(?,?,?,?)}";
    private static final String GET_USER_BY_EMAIL = "{call GET_USER_BY_EMAIL(?)}";
    private static Logger LOGGER = Logger.getLogger(UserDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public User getByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(GET_USER_BY_EMAIL, new Object[]{email}, new UserRowMapper());
        } catch (Exception e) {
            LOGGER.error(String.format("User not found for email : %s", email));
            return null;
        }
    }

    @Override
    public User getById(int id) {
        try {
            return jdbcTemplate.queryForObject(CommonConstants.GENERIC_PROC.GET_BY_ID, new Object[]{getTableName(), id}, new UserRowMapper());
        } catch (Exception e) {
            LOGGER.error(String.format("User not found for id : %d", id));
            return null;
        }
    }

    @Override
    public Collection<User> getAll() {
        return jdbcTemplate.query(CommonConstants.GENERIC_PROC.GET_ALL, new Object[]{getTableName()}, new UserRowMapper());
    }


    @Override
    public int save(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getEmail());
            ps.setString(2, encoder.encode(user.getPassword()));
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setInt(5, user.getCreateBy());
            ps.setInt(6, user.getUpdateBy());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public String getTableName() {
        return "users";
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(UPDATE, user.getFirstName(), user.getLastName(), user.getUpdateBy(), user.getId());
    }

    @Override
    public void updateStatus(int status, int id) {
        jdbcTemplate.update(CommonConstants.GENERIC_PROC.UPDATE_STATUS, getTableName(), status, id);
    }

    @Override
    public void delete(int id) throws DataAccessException {
        jdbcTemplate.update(CommonConstants.GENERIC_PROC.DELETE, getTableName(), id);
    }
}
