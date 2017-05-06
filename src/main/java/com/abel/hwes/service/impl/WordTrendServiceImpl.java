package com.abel.hwes.service.impl;

import com.abel.hwes.dao.WordTrendDao;
import com.abel.hwes.dao.impl.WordTrendDaoImpl;
import com.abel.hwes.model.SearchBox;
import com.abel.hwes.model.WordTrend;
import com.abel.hwes.service.WordTrendService;
import com.abel.hwes.util.WordTrendListFormatUtil;

public class WordTrendServiceImpl implements WordTrendService {

    private WordTrendDao wordTrendDao;

    public void setQuestionDao(WordTrendDao wordTrendDao) {
        this.wordTrendDao = wordTrendDao;
    }

    @Override
    public SearchBox<WordTrend> getWordTrendList(SearchBox<WordTrend> searchBox) {
        wordTrendDao = new WordTrendDaoImpl();
        return WordTrendListFormatUtil.changeListToMap(wordTrendDao.getWordTrendList(searchBox));
    }
}
