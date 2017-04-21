package com.abel.hwes.service;

import com.abel.hwes.model.SearchBox;
import com.abel.hwes.model.WordTrend;

public interface WordTrendService {

    public SearchBox<WordTrend> getWordTrendList(SearchBox<WordTrend> searchBox);
}
