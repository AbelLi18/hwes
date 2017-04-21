package com.abel.hwes.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.abel.hwes.Constants;
import com.abel.hwes.common.JDBCAbstractCallBack;
import com.abel.hwes.common.JDBCTemplate;
import com.abel.hwes.dao.WordTrendDao;
import com.abel.hwes.model.SearchBox;
import com.abel.hwes.model.WordTrend;
import com.abel.hwes.util.StringUtil;
import com.abel.hwes.util.SwapDateAndStringUtil;

public class WordTrendDaoImpl implements WordTrendDao {

    JDBCTemplate<WordTrend> jdbcTemplate;

    public void setJdbcTemplate(JDBCTemplate<WordTrend> jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public SearchBox<WordTrend> getWordTrendList(SearchBox<WordTrend> searchBox) {
        String sql = "SELECT keyword, search_date, total_count " +
                     "FROM word_trend " +
                     "WHERE keyword = ? " +
                     "AND search_date >= ? " +
                     "AND search_date <= ?";
        jdbcTemplate = new JDBCTemplate<WordTrend>();
        if (searchBox != null) {
            if (searchBox.getStartDate() == null) {
                searchBox.setStartDate(SwapDateAndStringUtil.StrToStartDate(Constants.START_DATE));
            }

            if (searchBox.getEndDate() == null) {
                searchBox.setEndDate(SwapDateAndStringUtil.StrToEndDate(Constants.END_DATE));
            }

            if (!StringUtil.isEmpty(searchBox.getFirstKeyword())) {
                Object[] params = {searchBox.getFirstKeyword(), searchBox.getStartDate(), searchBox.getEndDate()};
                searchBox.setFirstWordList(getWordTrendList(sql, params));
            }

            if (!StringUtil.isEmpty(searchBox.getSecondKeyword())) {
                Object[] params = {searchBox.getSecondKeyword(), searchBox.getStartDate(), searchBox.getEndDate()};
                searchBox.setSecondWordList(getWordTrendList(sql, params));
            }
        }

        return searchBox;
    }

    private List<WordTrend> getWordTrendList(String sql, final Object[] params) {
        return jdbcTemplate.query(sql, new JDBCAbstractCallBack<WordTrend>() {
            @Override
            public WordTrend rsToObject(ResultSet rs) throws SQLException {
                WordTrend wordTrend = new WordTrend();
                wordTrend.setKeyword(rs.getString("keyword"));
                wordTrend.setSearchDate(rs.getDate("search_date"));
                wordTrend.setTotalCount(rs.getString("total_count"));
                return wordTrend;
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                if (params != null) {
                    for (int i = 0; i < params.length; i++) {
                        stmt.setObject(i + 1, params[i]);
                    }
                }
            }
        });
    }
}
