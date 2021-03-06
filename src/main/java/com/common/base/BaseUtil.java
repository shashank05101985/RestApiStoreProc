package com.common.base;

import com.common.base.dto.BaseDTO;
import org.springframework.beans.factory.annotation.Value;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class BaseUtil {

    public static void setBaseFieldFromResultSet(BaseDTO obj , ResultSet rs) throws SQLException {
        obj.setId(rs.getInt("id"));
        obj.setStatus(rs.getInt("status"));
        obj.setCreateBy(rs.getInt("create_by"));
        obj.setUpdateBy(rs.getInt("update_by"));
        obj.setCreateTime(rs.getTimestamp("create_time"));
        obj.setUpdateTime(rs.getTimestamp("update_time"));
    }
}
