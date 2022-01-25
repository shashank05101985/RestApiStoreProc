package com.common.module.data.dao.impl;

import com.common.constants.CommonConstants;
import com.common.module.data.dao.ContractDAO;
import com.common.module.data.dto.Contract;
import com.common.module.data.mapper.ContractRowMapper;
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
public class ContractDAOImpl implements ContractDAO {
    private static final String INSERT_UPDATE = "{call INSERT_UPDATE_CONTRACT(?,?,?,?,?,?,?,?,?)}";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Contract getById(int id) throws DataAccessException {
        return jdbcTemplate.queryForObject(CommonConstants.GENERIC_PROC.GET_BY_ID,new Object[]{getTableName(),id},new ContractRowMapper());
    }

    @Override
    public Collection<Contract> getAll() throws DataAccessException {
        return jdbcTemplate.query(CommonConstants.GENERIC_PROC.GET_ALL,new Object[]{getTableName()},new ContractRowMapper());
    }

    @Override
    public int save(Contract contract) throws DataAccessException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT_UPDATE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, contract.getReferenceNo());
            ps.setInt(2, contract.getSerialNo());
            ps.setInt(3, contract.getCompanyId());
            ps.setInt(4, contract.getId());
            ps.setInt(5, contract.getBuyerId());
            ps.setInt(6, contract.getSellerId());
            ps.setInt(7, contract.getModelId());
            ps.setDouble(8, contract.getRate());
            ps.setDouble(9, contract.getMinQuantity());
            ps.setDouble(10, contract.getMaxQuantity());
            ps.setDouble(11, contract.getDispatchQuantity());
            ps.setDouble(12, contract.getBuyerBrokerageRate());
            ps.setDouble(13, contract.getSellerBrokerageRate());
            ps.setDate(14,new java.sql.Date(contract.getContractDate().getTime()));
            ps.setString(15, contract.getContractReferral());
            ps.setInt(16, contract.getCreateBy());
            ps.setInt(17, contract.getUpdateBy());
            ps.setInt(18, contract.getId());

            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public String getTableName() {
        return "contract";
    }

    @Override
    public void update(Contract contract) throws DataAccessException {
        jdbcTemplate.update(INSERT_UPDATE,contract.getReferenceNo(),contract.getSerialNo(),contract.getCompanyId(),
               contract.getItemId(),contract.getBuyerId(),contract.getSellerId(),contract.getModelId(),contract.getRate(),
               contract.getMinQuantity(),contract.getMaxQuantity(),contract.getDispatchQuantity(),contract.getBuyerBrokerageRate(),contract.getSellerBrokerageRate(),
               contract.getContractDate(),contract.getContractReferral(),contract.getCreateBy(),contract.getUpdateBy(), contract.getId());
    }

    @Override
    public void updateStatus(int status, int id) throws DataAccessException {
       jdbcTemplate.update(CommonConstants.GENERIC_PROC.UPDATE_STATUS,getTableName(),status,id);
    }

    @Override
    public void delete(int id) throws DataAccessException {
        jdbcTemplate.update(CommonConstants.GENERIC_PROC.DELETE,getTableName(),id);
    }
}
