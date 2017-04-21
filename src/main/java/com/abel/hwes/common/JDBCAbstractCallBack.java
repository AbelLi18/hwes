package com.abel.hwes.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class JDBCAbstractCallBack<T> implements JDBCCallBack<T> {

    public T rsToObject(ResultSet rs) throws SQLException {
        return null;
    }

    public void setParams(PreparedStatement stmt) throws SQLException {

    }
}
