package com.abel.hwes.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.abel.hwes.Constants;
import com.abel.hwes.common.JDBCAbstractCallBack;
import com.abel.hwes.common.JDBCTemplate;
import com.abel.hwes.dao.WordZoneDao;
import com.abel.hwes.model.SearchBox;
import com.abel.hwes.model.WordZone;
import com.abel.hwes.util.StringUtil;
import com.abel.hwes.util.SwapDateAndStringUtil;

public class WordZoneDaoImpl implements WordZoneDao {

    JDBCTemplate<WordZone> jdbcTemplate;

    public void setJdbcTemplate(JDBCTemplate<WordZone> jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public SearchBox<WordZone> getWordZoneList(SearchBox<WordZone> searchBox) {
        String sql = "SELECT keyword, province, sum(count) as total_count " +
                     "FROM word_zone " +
                     "WHERE keyword = ? " +
                     "AND search_date >= ? " +
                     "AND search_date <= ? " +
                     "GROUP BY province";
        jdbcTemplate = new JDBCTemplate<WordZone>();
        if (searchBox != null) {
            if (searchBox.getStartDate() == null) {
                searchBox.setStartDate(SwapDateAndStringUtil.StrToDate(Constants.START_DATE));
            }

            if (searchBox.getEndDate() == null) {
                searchBox.setEndDate(SwapDateAndStringUtil.StrToDate(Constants.END_DATE));
            }

            if (!StringUtil.isEmpty(searchBox.getFirstKeyword())) {
                Object[] params = {searchBox.getFirstKeyword(), searchBox.getStartDate(), searchBox.getEndDate()};
                searchBox.setFirstWordList(getWordZoneList(sql, params));
            }

            if (!StringUtil.isEmpty(searchBox.getSecondKeyword())) {
                Object[] params = {searchBox.getSecondKeyword(), searchBox.getStartDate(), searchBox.getEndDate()};
                searchBox.setSecondWordList(getWordZoneList(sql, params));
            }
        }

        return searchBox;
    }

    private List<WordZone> getWordZoneList(String sql, final Object[] params) {
        return jdbcTemplate.query(sql, new JDBCAbstractCallBack<WordZone>() {
            @Override
            public WordZone rsToObject(ResultSet rs) throws SQLException {
                WordZone wordZone = new WordZone();
                wordZone.setKeyword(rs.getString("keyword"));
                wordZone.setProvince(rs.getString("province"));
                wordZone.setTotalCount(rs.getString("total_count"));
                return wordZone;
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
