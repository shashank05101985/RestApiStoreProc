package com.common.module.data.dao.impl;

import com.common.constants.CommonConstants;
import com.common.module.data.dao.ClientDAO;
import com.common.module.data.dto.Client;
import com.common.module.data.mapper.ClientRowMapper;
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
public class ClientDAOImpl implements ClientDAO {


    private static final String INSERT_UPDATE = "{call INSERT_UPDATE_CLIENT(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Client getById(int id) {
        return jdbcTemplate.queryForObject(CommonConstants.GENERIC_PROC.GET_BY_ID, new Object[]{getTableName(),id}, new ClientRowMapper());
    }

    @Override
    public Collection<Client> getAll() {
        return jdbcTemplate.query(CommonConstants.GENERIC_PROC.GET_ALL, new Object[]{getTableName()}, new ClientRowMapper());
    }

    @Override
    public int save(Client client) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT_UPDATE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, client.getName());
            ps.setString(2, client.getCode());
            ps.setString(3, client.getGstNo());
            ps.setString(4, client.getEmail());
            ps.setInt(5, client.getCompanyId());
            ps.setString(6, client.getMobile());
            ps.setString(7, client.getPhone());
            ps.setInt(8, client.getGcCode());
            ps.setInt(9, client.getCityId());
            ps.setInt(10, client.getPinCode());
            ps.setString(11, client.getAddress());
            ps.setInt(12, client.getCreateBy());
            ps.setInt(13, client.getUpdateBy());
            ps.setInt(14,client.getId());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public String getTableName() {
        return "client";
    }

    @Override
    public void update(Client client) {
        jdbcTemplate.update(INSERT_UPDATE,
                client.getName(), client.getCode(), client.getGstNo(), client.getEmail(),
                client.getCompanyId(), client.getMobile(), client.getPhone(), client.getGcCode(),
                client.getCityId(), client.getPinCode(), client.getAddress(),client.getCreateBy(), client.getUpdateBy(),
                client.getId());
    }

    @Override
    public void updateStatus(int status, int id) throws DataAccessException {
        jdbcTemplate.update(CommonConstants.GENERIC_PROC.UPDATE_STATUS,status,id);
    }

    @Override
    public void delete(int id) throws DataAccessException {
        jdbcTemplate.update(CommonConstants.GENERIC_PROC.DELETE,id);
    }
}
