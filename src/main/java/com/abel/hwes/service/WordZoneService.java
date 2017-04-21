package com.abel.hwes.service;

import com.abel.hwes.model.SearchBox;
import com.abel.hwes.model.WordZone;

public interface WordZoneService {

    public SearchBox<WordZone> getWordTrendList(SearchBox<WordZone> searchBox);
}
