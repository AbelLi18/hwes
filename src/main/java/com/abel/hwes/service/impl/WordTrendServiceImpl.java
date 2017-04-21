package com.abel.hwes.service.impl;

import java.util.List;

import com.abel.hwes.dao.WordTrendDao;
import com.abel.hwes.dao.impl.WordTrendDaoImpl;
import com.abel.hwes.model.SearchBox;
import com.abel.hwes.model.WordTrend;
import com.abel.hwes.service.WordTrendService;
import com.abel.hwes.util.StringUtil;

public class WordTrendServiceImpl implements WordTrendService {

    private WordTrendDao wordTrendDao;

    public void setQuestionDao(WordTrendDao wordTrendDao) {
        this.wordTrendDao = wordTrendDao;
    }

    public SearchBox<WordTrend> getWordTrendList(SearchBox<WordTrend> searchBox) {
        wordTrendDao = new WordTrendDaoImpl();
        searchBox = wordTrendDao.getWordTrendList(searchBox);


        if (!StringUtil.isEmpty(searchBox.getFirstKeyword())) {
            List<WordTrend> firstWordList = searchBox.getFirstWordList();
            for (int i = 0; i < firstWordList.size(); i++) {
                
            }
        }

        if (!StringUtil.isEmpty(searchBox.getSecondKeyword())) {
            List<WordTrend> secondWordList = searchBox.getSecondWordList();
        }
        return null;
    }
}
