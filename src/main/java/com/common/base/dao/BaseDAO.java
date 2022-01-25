package com.common.base.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collection;
import java.util.List;

public interface BaseDAO<K> {


    K getById(int id) throws DataAccessException;

    Collection<K> getAll() throws DataAccessException;

    abstract int save(K k) throws DataAccessException;

    abstract String getTableName();

    default void batchSave(List<K> k) throws DataAccessException {

    }

    default void update(K k) throws DataAccessException {

    }

    default void updateStatus(int status, int id) throws DataAccessException {

    }

    default void delete( int id) throws DataAccessException {

    }
}
