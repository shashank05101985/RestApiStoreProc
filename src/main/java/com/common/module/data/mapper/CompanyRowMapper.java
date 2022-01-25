package com.common.module.data.mapper;

import com.common.base.BaseUtil;
import com.common.module.data.dto.Company;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyRowMapper implements RowMapper<Company> {
    @Override
    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
        Company company = new Company();
        BaseUtil.setBaseFieldFromResultSet(company,rs);
        company.setName(rs.getString("name"));
        company.setCode(rs.getString("code"));
        company.setGstNo(rs.getString("gst_no"));
        company.setEmail(rs.getString("email"));
        company.setMobile(rs.getString("mobile"));
        company.setPhone(rs.getString("phone"));
        company.setCityId(rs.getInt("city_id"));
        company.setPinCode(rs.getInt("pin_code"));
        company.setAddress(rs.getString("address"));
        company.setPanNo(rs.getString("pan_no"));
        company.setBankName(rs.getString("bank_name"));
        company.setBranchName(rs.getString("branch_name"));
        company.setIfscCode(rs.getString("ifsc_code"));
        company.setAccountNo(rs.getString("account_no"));

        return company;
    }
}
