package com.abel.hwes.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.abel.hwes.common.JDBCAbstractCallBack;
import com.abel.hwes.common.JDBCTemplate;
import com.abel.hwes.dao.WordCountDao;
import com.abel.hwes.model.PageBean;
import com.abel.hwes.model.WordCount;

public class WordCountDaoImpl implements WordCountDao {

    JDBCTemplate<WordCount> jdbcTemplate;

    public void setJdbcTemplate(JDBCTemplate<WordCount> jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<WordCount> getWordList() {
        String sql = "SELECT keyword, total_count FROM word_count ORDER BY total_count DESC LIMIT 0, 10";
        jdbcTemplate = new JDBCTemplate<WordCount>();
        return jdbcTemplate.query(sql, new JDBCAbstractCallBack<WordCount>() {
            @Override
            public WordCount rsToObject(ResultSet rs) throws SQLException {
                WordCount wordCount = new WordCount();
                wordCount.setKeyword(rs.getString("keyword"));
                wordCount.setTotalCount(rs.getString("total_count"));
                return wordCount;
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                // TODO Auto-generated method stub
                super.setParams(stmt);
            }
        });
    }

    public PageBean<WordCount> getWordList(PageBean<WordCount> pageBean) {
        String sql = "SELECT keyword, total_count FROM word_count ORDER BY total_count DESC LIMIT ?, ?";
        jdbcTemplate = new JDBCTemplate<WordCount>();
        final Object[] params = {pageBean.getCurrentPage() - 1, pageBean.getPageSize()};
        pageBean.setList(jdbcTemplate.query(sql, new JDBCAbstractCallBack<WordCount>() {
            @Override
            public WordCount rsToObject(ResultSet rs) throws SQLException {
                WordCount wordCount = new WordCount();
                wordCount.setKeyword(rs.getString("keyword"));
                wordCount.setTotalCount(rs.getString("total_count"));
                return wordCount;
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                if (params != null) {
                    for (int i = 0; i < params.length; i++) {
                        stmt.setObject(i + 1, params[i]);
                    }
                }
            }
        }));
        return pageBean;
    }

    public int getTotalCount() {
        String sql = "SELECT count(keyword) FROM word_count";
        JDBCTemplate<Integer> jdbc = new JDBCTemplate<Integer>();
        return jdbc.queryOne(sql, new JDBCAbstractCallBack<Integer>() {
            @Override
            public Integer rsToObject(ResultSet rs) throws SQLException {
                return rs.getInt(1);
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                // TODO Auto-generated method stub
                super.setParams(stmt);
            }
        });
    }
}
