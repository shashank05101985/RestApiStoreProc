package com.common.module.data.dao.impl;

import com.common.constants.CommonConstants;
import com.common.module.data.dao.ModelDAO;
import com.common.module.data.dto.Model;
import com.common.module.data.mapper.ModelRowMapper;
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
public class ModelDAOImpl implements ModelDAO {

    private static final String INSERT_UPDATE = "{call INSERT_UPDATE_MODEL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Model getById(int id) throws DataAccessException {
        return jdbcTemplate.queryForObject(CommonConstants.GENERIC_PROC.GET_BY_ID,new Object[]{id},new ModelRowMapper());
    }

    @Override
    public Collection<Model> getAll() throws DataAccessException {
        return jdbcTemplate.query(CommonConstants.GENERIC_PROC.GET_ALL,new ModelRowMapper());
    }

    @Override
    public int save(Model model) throws DataAccessException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT_UPDATE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, model.getName());
            ps.setString(2, model.getCode());
            ps.setString(3, model.getTitle1());
            ps.setString(4, model.getTitle2());
            ps.setString(5, model.getTitle3());
            ps.setString(6, model.getTitle4());
            ps.setString(7, model.getTitle5());
            ps.setString(8, model.getTitle6());
            ps.setString(9, model.getTitle7());
            ps.setString(10, model.getTitle8());
            ps.setString(11, model.getTitle9());
            ps.setString(12, model.getTitle10());
            ps.setInt(13, model.getCreateBy());
            ps.setInt(14, model.getUpdateBy());
            ps.setInt(15, model.getId());

            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public String getTableName() {
        return "model";
    }

    @Override
    public void update(Model model) throws DataAccessException {
        jdbcTemplate.update(INSERT_UPDATE,model.getName(),model.getCode(),model.getTitle1(),model.getTitle2(),
                model.getTitle3(),model.getTitle4(),model.getTitle5(),model.getTitle6(),
                model.getTitle7(),model.getTitle8(),model.getTitle9(),model.getTitle10(),model.getCreateBy(),
                model.getUpdateBy(),model.getId());
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
