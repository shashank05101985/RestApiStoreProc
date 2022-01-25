package com.common.module.data.dao.impl;

import com.common.constants.CommonConstants;
import com.common.module.data.dao.CompanyDAO;
import com.common.module.data.dto.Company;
import com.common.module.data.mapper.CompanyRowMapper;
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
public class CompanyDAOImpl implements CompanyDAO {

    private static final String INSERT_UPDATE = "{call INSERT_UPDATE_COMPANY(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Company getById(int id) {
        return jdbcTemplate.queryForObject(CommonConstants.GENERIC_PROC.GET_BY_ID, new Object[]{getTableName(),id}, new CompanyRowMapper());
    }

    @Override
    public Collection<Company> getAll() {
        return jdbcTemplate.query(CommonConstants.GENERIC_PROC.GET_ALL,new Object[]{getTableName()}, new CompanyRowMapper());
    }

    @Override
    public int save(Company company) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT_UPDATE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, company.getName());
            ps.setString(2, company.getCode());
            ps.setString(3, company.getGstNo());
            ps.setString(4, company.getEmail());
            ps.setString(5, company.getMobile());
            ps.setString(6, company.getPhone());
            ps.setInt(7, company.getCityId());
            ps.setInt(8, company.getPinCode());
            ps.setString(9, company.getAddress());
            ps.setString(10, company.getPanNo());
            ps.setString(11, company.getBankName());
            ps.setString(12, company.getBranchName());
            ps.setString(13, company.getIfscCode());
            ps.setString(14, company.getAccountNo());
            ps.setInt(15, company.getCreateBy());
            ps.setInt(16, company.getUpdateBy());
            ps.setInt(17, company.getId());

            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void update(Company company) throws DataAccessException {
        jdbcTemplate.update(INSERT_UPDATE,
                company.getName(), company.getCode(), company.getGstNo(), company.getEmail(),
                company.getMobile(), company.getPhone(), company.getCityId(),
                company.getPinCode(), company.getAddress(), company.getPanNo(), company.getBankName(),
                company.getBranchName(), company.getIfscCode(), company.getAccountNo(),company.getCreateBy(),
                company.getUpdateBy(), company.getId());
    }


    @Override
    public String getTableName() {
        return "company";
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
