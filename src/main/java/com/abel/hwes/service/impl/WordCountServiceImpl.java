package com.abel.hwes.service.impl;

import java.util.List;

import com.abel.hwes.dao.WordCountDao;
import com.abel.hwes.dao.impl.WordCountDaoImpl;
import com.abel.hwes.model.PageBean;
import com.abel.hwes.model.WordCount;
import com.abel.hwes.service.WordCountService;

public class WordCountServiceImpl implements WordCountService {

    private WordCountDao wordCountDao;

    public void setQuestionDao(WordCountDao wordCountDao) {
        this.wordCountDao = wordCountDao;
    }

    public List<WordCount> getWordList() {
        wordCountDao = new WordCountDaoImpl();
        return wordCountDao.getWordList();
    }

    public PageBean<WordCount> getWordList(String currentPage) {
        wordCountDao = new WordCountDaoImpl();
        int crtPage;
        try {
            crtPage = Integer.parseInt(currentPage);
        } catch (Exception e) {
            crtPage = 1;
        }

        PageBean<WordCount> pageBean = new PageBean<WordCount>(crtPage, wordCountDao.getTotalCount(), 10);
        return wordCountDao.getWordList(pageBean);
    }
}
