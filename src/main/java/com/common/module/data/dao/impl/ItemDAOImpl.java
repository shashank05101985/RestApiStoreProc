package com.common.module.data.dao.impl;

import com.common.constants.CommonConstants;
import com.common.module.data.dao.ItemDAO;
import com.common.module.data.dto.Item;
import com.common.module.data.mapper.ItemRowMapper;
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
public class ItemDAOImpl implements ItemDAO {

    private static final String INSERT_UPDATE = "{call INSERT_UPDATE_ITEM(?,?,?,?,?,?,?,?,?,?)}";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Item getById(int id) {

        return jdbcTemplate.queryForObject(CommonConstants.GENERIC_PROC.GET_BY_ID, new Object[]{getTableName(),id}, new ItemRowMapper());
    }

    @Override
    public Collection<Item> getAll() {
        return jdbcTemplate.query(CommonConstants.GENERIC_PROC.GET_ALL, new Object[]{getTableName()}, new ItemRowMapper());
    }

    @Override
    public int save(Item item) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT_UPDATE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, item.getName());

            ps.setString(2, item.getLongName());
            ps.setString(3, item.getCode());
            ps.setString(4, item.getType());
            ps.setString(5, item.getUnitMeasure());
            ps.setInt(6, item.getTaxable());
            ps.setDouble(7, item.getTaxPercent());
            ps.setInt(8, item.getCreateBy());
            ps.setInt(9, item.getUpdateBy());
            ps.setInt(10, item.getId());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void update(Item item) {
        jdbcTemplate.update(INSERT_UPDATE,
                item.getName(), item.getLongName(), item.getCode(), item.getType(),
                item.getUnitMeasure(), item.getTaxable(),item.getTaxPercent(),item.getCreateBy(),item.getUpdateBy(), item.getId());
    }



    @Override
    public String getTableName() {
        return "item";
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
