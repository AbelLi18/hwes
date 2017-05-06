package com.abel.hwes.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abel.hwes.model.SearchBox;
import com.abel.hwes.model.WordTrend;

/**
 * Change WordTrends from list to map
 * @author Abel.li
 */
public class WordTrendListFormatUtil {

    public static SearchBox<WordTrend> changeListToMap(SearchBox<WordTrend> searchBox) {
        Map<Date, WordTrend> defaultWordMap = loopGetDefaultWordMap(searchBox.getStartDate(), searchBox.getEndDate());

        if (!StringUtil.isEmpty(searchBox.getFirstKeyword())) {
            List<WordTrend> firstWordList = searchBox.getFirstWordList();
            searchBox.setFirstWordMap(traverseListToMap(firstWordList, defaultWordMap));
        }

        defaultWordMap = loopGetDefaultWordMap(searchBox.getStartDate(), searchBox.getEndDate());
        if (!StringUtil.isEmpty(searchBox.getSecondKeyword())) {
            List<WordTrend> secondWordList = searchBox.getSecondWordList();
            searchBox.setSecondWordMap(traverseListToMap(secondWordList, defaultWordMap));
        }
        return searchBox;
    }

    public static Map<Date, WordTrend> loopGetDefaultWordMap(Date startDate, Date endDate) {
        Map<Date, WordTrend> defaultWordMap = new HashMap<Date, WordTrend>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);
        Date indexDate = calendar.getTime();
        while(indexDate.getTime() <= endDate.getTime()) {
            WordTrend wordTrend = new WordTrend();
            wordTrend.setKeyword("");
            wordTrend.setSearchDate(indexDate);
            wordTrend.setTotalCount("0");

            defaultWordMap.put(indexDate, wordTrend);

            calendar.add(calendar.DATE, 1);
            indexDate = calendar.getTime();
        }

        return defaultWordMap;
    }

    private static Map<Date, WordTrend> traverseListToMap(List<WordTrend> wordList, Map<Date, WordTrend> defaultWordMap) {
        for (int i = 0; i < wordList.size(); i++) {
            WordTrend wordTrend = wordList.get(i);
            WordTrend resultWordTrend = defaultWordMap.get(wordTrend.getSearchDate());
            resultWordTrend.setKeyword(wordTrend.getKeyword());
            resultWordTrend.setTotalCount(wordTrend.getTotalCount());
            defaultWordMap.put(wordTrend.getSearchDate(), resultWordTrend);
        }
        return defaultWordMap;
    }
}
