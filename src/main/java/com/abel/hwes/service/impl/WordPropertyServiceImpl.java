package com.abel.hwes.service.impl;

import java.util.List;

import com.abel.hwes.dao.WordPropertyDao;
import com.abel.hwes.dao.impl.WordPropertyDaoImpl;
import com.abel.hwes.service.WordPropertyService;

public class WordPropertyServiceImpl implements WordPropertyService {

    private WordPropertyDao wordPropertyDao;

    public void setQuestionDao(WordPropertyDao wordPropertyDao) {
        this.wordPropertyDao = wordPropertyDao;
    }

    public List<String> getWordContextList(String keyword) {
        wordPropertyDao = new WordPropertyDaoImpl();
        return wordPropertyDao.getWordContextList(keyword);
    }
}
