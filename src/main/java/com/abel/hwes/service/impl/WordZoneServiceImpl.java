package com.abel.hwes.service.impl;

import com.abel.hwes.dao.WordZoneDao;
import com.abel.hwes.dao.impl.WordZoneDaoImpl;
import com.abel.hwes.model.SearchBox;
import com.abel.hwes.model.WordZone;
import com.abel.hwes.service.WordZoneService;

public class WordZoneServiceImpl implements WordZoneService {

    private WordZoneDao wordZoneDao;

    public void setQuestionDao(WordZoneDao wordZoneDao) {
        this.wordZoneDao = wordZoneDao;
    }

    public SearchBox<WordZone> getWordTrendList(SearchBox<WordZone> searchBox) {
        wordZoneDao = new WordZoneDaoImpl();
        return wordZoneDao.getWordZoneList(searchBox);
    }
}
