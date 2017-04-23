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

        if (!StringUtil.isEmpty(searchBox.getSecondKeyword())) {
            List<WordTrend> secondWordList = searchBox.getSecondWordList();
            searchBox.setFirstWordMap(traverseListToMap(secondWordList, defaultWordMap));
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

        // 2006-08-25 数据缺失，此处将取24日和26日的平均值作为25的搜索量
        // TODO
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            System.out.println(indexDate);
//            System.out.println();
//            System.out.println(":" + defaultWordMap.get(indexDate));
//            System.out.println();
//            System.out.println(indexDate);
//            int totalCount = Integer.parseInt(defaultWordMap.get(sdf.parse("2006-08-24")).getTotalCount())
//                + Integer.parseInt(defaultWordMap.get(sdf.parse("2006-08-26")).getTotalCount());
//            System.out.println(totalCount);
//            defaultWordMap.get(sdf.parse("2006-08-25")).setTotalCount("" + totalCount / 2);
//        } catch (Exception e) {
//            return defaultWordMap;
//        }
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
