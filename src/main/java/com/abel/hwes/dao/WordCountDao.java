package com.abel.hwes.dao;

import java.util.List;

import com.abel.hwes.model.PageBean;
import com.abel.hwes.model.WordCount;

public interface WordCountDao {

    public int getTotalCount();

    public List<WordCount> getWordList();

    public PageBean<WordCount> getWordList(PageBean<WordCount> pageBean);
}
