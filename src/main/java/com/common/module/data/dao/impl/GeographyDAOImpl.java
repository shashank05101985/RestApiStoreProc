package com.common.module.data.dao.impl;

import com.common.constants.CommonConstants;
import com.common.module.data.dao.GeographyDAO;
import com.common.module.data.dto.City;
import com.common.module.data.dto.State;
import com.common.module.data.mapper.CityRowMapper;
import com.common.module.data.mapper.SateRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;

@Repository
public class GeographyDAOImpl implements GeographyDAO {
    static final String INSERT_UPDATE_CITY = "cal INSERT_UPDATE_CITY(?,?,?,?,?,?)";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public int addCity(City city) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT_UPDATE_CITY, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, city.getName());
            ps.setString(2, city.getCode());
            ps.setInt(3, city.getParentId());
            ps.setInt(4, city.getCreateBy());
            ps.setInt(5, city.getUpdateBy());
            ps.setInt(6, city.getId());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void updateCity(City city) {
        jdbcTemplate.update(INSERT_UPDATE_CITY,
                city.getName(), city.getCode(), city.getParentId(),city.getCreateBy(),city.getUpdateBy(),city.getId());
    }

    @Override
    public Collection<City> getAllCity() {
        return jdbcTemplate.query(CommonConstants.GENERIC_PROC.GET_ALL,new Object[]{"city"},new CityRowMapper());
    }

    @Override
    public Collection<State> getAllState() {
        return jdbcTemplate.query(CommonConstants.GENERIC_PROC.GET_ALL,new Object[]{"state"},new SateRowMapper());
    }
}
