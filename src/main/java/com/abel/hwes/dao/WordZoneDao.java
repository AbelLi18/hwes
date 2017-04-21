package com.abel.hwes.dao;

import com.abel.hwes.model.SearchBox;
import com.abel.hwes.model.WordZone;

public interface WordZoneDao {

    public SearchBox<WordZone> getWordZoneList(SearchBox<WordZone> searchBox);
}
