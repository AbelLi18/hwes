package com.abel.hwes.service;

import java.util.List;

import com.abel.hwes.model.PageBean;
import com.abel.hwes.model.WordCount;

public interface WordCountService {

    public List<WordCount> getWordList();

    public PageBean<WordCount> getWordList(String currentPage);
}
