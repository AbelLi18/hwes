package com.abel.hwes.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.abel.hwes.common.JDBCAbstractCallBack;
import com.abel.hwes.common.JDBCTemplate;
import com.abel.hwes.dao.WordPropertyDao;
import com.abel.hwes.util.StringUtil;

public class WordPropertyDaoImpl implements WordPropertyDao {

    JDBCTemplate<String> jdbcTemplate;

    public void setJdbcTemplate(JDBCTemplate<String> jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<String> getWordContextList(String keyword) {
        String sql = "SELECT context FROM word_context where keyword = '" + keyword +
                     "' LIMIT 0,10";
        jdbcTemplate = new JDBCTemplate<String>();
        return jdbcTemplate.query(sql, new JDBCAbstractCallBack<String>() {
            @Override
            public String rsToObject(ResultSet rs) throws SQLException {
                return StringUtil.trimString(StringUtil.moveSpecialChar(rs.getString("context")));
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                super.setParams(stmt);
            }
        });
    }
}
