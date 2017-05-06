package com.abel.hwes.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.abel.hwes.util.SwapDateAndStringUtil;

public class SearchBox<T> {

    private String firstKeyword;
    private String secondKeyword;
    private Date startDate;
    private Date endDate;
    private List<T> firstWordList;
    private List<T> secondWordList;
    private Map<Date, T> firstWordMap;
    private Map<Date, T> secondWordMap;

    public Map<Date, T> getFirstWordMap() {
        return firstWordMap;
    }

    public void setFirstWordMap(Map<Date, T> firstWordMap) {
        this.firstWordMap = firstWordMap;
    }

    public Map<Date, T> getSecondWordMap() {
        return secondWordMap;
    }

    public void setSecondWordMap(Map<Date, T> secondWordMap) {
        this.secondWordMap = secondWordMap;
    }

    public void setSecondWordList(List<T> secondWordList) {
        this.secondWordList = secondWordList;
    }

    public List<T> getFirstWordList() {
        return firstWordList;
    }

    public void setFirstWordList(List<T> firstWordList) {
        this.firstWordList = firstWordList;
    }

    public List<T> getSecondWordList() {
        return secondWordList;
    }

    public void setSecondWordTrendList(List<T> secondWordList) {
        this.secondWordList = secondWordList;
    }

    public String getFirstKeyword() {
        return firstKeyword;
    }

    public void setFirstKeyword(String firstKeyword) {
        this.firstKeyword = firstKeyword;
    }

    public String getSecondKeyword() {
        return secondKeyword;
    }

    public void setSecondKeyword(String secondKeyword) {
        this.secondKeyword = secondKeyword;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        Date start_index = SwapDateAndStringUtil.StrToStartDate("2006-08-01");
        if (startDate.getTime() < start_index.getTime()) {
            startDate = start_index;
        }
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        Date end_index = SwapDateAndStringUtil.StrToEndDate("2006-08-31");
        if (endDate.getTime() > end_index.getTime()) {
            endDate = end_index;
        }
        this.endDate = endDate;
    }
}
