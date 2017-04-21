package com.abel.hwes.dao;

import com.abel.hwes.model.SearchBox;
import com.abel.hwes.model.WordTrend;

public interface WordTrendDao {

    public SearchBox<WordTrend> getWordTrendList(SearchBox<WordTrend> searchBox);
}
