package com.abel.hwes.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.abel.hwes.Constants;
import com.abel.hwes.model.PageBean;
import com.abel.hwes.model.SearchBox;
import com.abel.hwes.model.WordCount;
import com.abel.hwes.model.WordTrend;
import com.abel.hwes.model.WordZone;
import com.abel.hwes.service.WordCountService;
import com.abel.hwes.service.WordPropertyService;
import com.abel.hwes.service.WordTrendService;
import com.abel.hwes.service.WordZoneService;
import com.abel.hwes.service.impl.WordCountServiceImpl;
import com.abel.hwes.service.impl.WordPropertyServiceImpl;
import com.abel.hwes.service.impl.WordTrendServiceImpl;
import com.abel.hwes.service.impl.WordZoneServiceImpl;
import com.abel.hwes.util.StringUtil;
import com.abel.hwes.util.SwapDateAndStringUtil;

@Controller
@SessionAttributes("defaultKeyword")
public class WordController {

    @RequestMapping(value="wordCount", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView wordCount(){
        ModelAndView mv = new ModelAndView();

        WordCountService wordCountService = new WordCountServiceImpl();
        List<WordCount> wordCount = wordCountService.getWordList();

        mv.addObject("wordCountList", wordCount);
        mv.addObject("defaultKeyword", wordCount.get((int)(Math.random() * wordCount.size())).getKeyword());
        mv.setViewName("/welcome");
        return mv;
    }

    @RequestMapping(value="wordProperty", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView wordProperty(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();

        WordPropertyService wordPropertyService = new WordPropertyServiceImpl();
        String searchKeyword = request.getParameter("searchKeyword");
        String firstKeyword = request.getParameter("firstKeyword");
        String secondKeyword = request.getParameter("secondKeyword");
        if (!StringUtil.isEmpty(searchKeyword)) {
            firstKeyword = searchKeyword;
        } else if (StringUtil.isEmpty(firstKeyword) && StringUtil.isEmpty(secondKeyword)) {
            firstKeyword = (String) request.getSession().getAttribute("defaultKeyword");
        }

        List<String> firstWordContext;
        if (!StringUtil.isEmpty(firstKeyword)) {
            firstWordContext = wordPropertyService.getWordContextList(firstKeyword);
            mv.addObject("firstWordContext", firstWordContext);
            mv.addObject("firstKeyword", firstKeyword);
        }

        List<String> secondWordContext;
        if (!StringUtil.isEmpty(secondKeyword)) {
            secondWordContext = wordPropertyService.getWordContextList(secondKeyword);
            mv.addObject("secondWordContext", secondWordContext);
            mv.addObject("secondKeyword", secondKeyword);
        }

        mv.setViewName("/word_property");
        return mv;
    }

    @RequestMapping(value="wordRanking", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView wordRanking(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();

        WordCountService wordCountService = new WordCountServiceImpl();
        PageBean<WordCount> pageBean = wordCountService.getWordList(request.getParameter("currentPage"));
        List<WordCount> wordRanking = pageBean.getList();
        mv.addObject("wordRankingList", wordRanking);

        StringBuffer wordRankingData = new StringBuffer();
        if (wordRanking != null) {
            for (int i = 0; i < wordRanking.size(); i++) {
                if (i == 0) {
                    wordRankingData.append("{name: '" + wordRanking.get(i).getKeyword() + "',value: " + wordRanking.get(i).getTotalCount() + ",itemStyle: createRandomItemStyle()}");
                } else {
                    wordRankingData.append(Constants.COMMA + "{name: '" + wordRanking.get(i).getKeyword() + "',value: " + wordRanking.get(i).getTotalCount() + ",itemStyle: createRandomItemStyle()}");
                }
            }
            mv.addObject("wordRankingData", wordRankingData.toString());
        }

        mv.addObject("pageBean", pageBean);
        mv.setViewName("/word_ranking");
        return mv;
    }

    @RequestMapping(value="wordTrend", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView wordTrend(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();

        WordTrendService wordTrendService = new WordTrendServiceImpl();
        SearchBox<WordTrend> searchBox = new SearchBox<WordTrend>();
        // 处理搜索的热词
        String searchKeyword = request.getParameter("searchKeyword");
        String firstKeyword = request.getParameter("firstKeyword");
        String secondKeyword = request.getParameter("secondKeyword");
        if (!StringUtil.isEmpty(firstKeyword) || !StringUtil.isEmpty(secondKeyword)) {
            if (!StringUtil.isEmpty(secondKeyword) && !firstKeyword.equals(secondKeyword)) {
                searchBox.setFirstKeyword(firstKeyword);
                searchBox.setSecondKeyword(secondKeyword);
            } else {
                searchBox.setFirstKeyword(firstKeyword);
            }
        } else if (!StringUtil.isEmpty(searchKeyword)) {
            searchBox.setFirstKeyword(searchKeyword);
        } else {
            searchBox.setFirstKeyword((String) request.getSession().getAttribute("defaultKeyword"));
        }

        // 处理搜索的日期
        searchBox.setStartDate(SwapDateAndStringUtil.StrToStartDate(request.getParameter("startDate")));
        searchBox.setEndDate(SwapDateAndStringUtil.StrToEndDate(request.getParameter("endDate")));

        searchBox = wordTrendService.getWordTrendList(searchBox);

        List<WordTrend> firstWordTrendList = searchBox.getFirstWordList();
        List<WordTrend> secondWordTrendList = searchBox.getSecondWordList();
        StringBuffer xAxisData = new StringBuffer();
        StringBuffer firstWordTrendData = new StringBuffer();
        StringBuffer secondWordTrendData = new StringBuffer();
        if (firstWordTrendList != null) {
            for (int i = 0; i < firstWordTrendList.size(); i++) {
                if (i == 0) {
                    xAxisData.append(firstWordTrendList.get(i).getSearchDate());
                    firstWordTrendData.append(firstWordTrendList.get(i).getTotalCount());
                } else {
                    xAxisData.append(Constants.COMMA + firstWordTrendList.get(i).getSearchDate());
                    firstWordTrendData.append(Constants.COMMA + firstWordTrendList.get(i).getTotalCount());
                }
            }
            mv.addObject("firstWordTrendData", firstWordTrendData.toString());
            mv.addObject("firstKeyword", searchBox.getFirstKeyword());
        }

        if (secondWordTrendList != null) {
            for (int i = 0; i < secondWordTrendList.size(); i++) {
                if (i == 0) {
                    secondWordTrendData.append(secondWordTrendList.get(i).getTotalCount());
                } else {
                    secondWordTrendData.append(Constants.COMMA + secondWordTrendList.get(i).getTotalCount());
                }
            }
            mv.addObject("secondWordTrendData", secondWordTrendData.toString());
            mv.addObject("secondKeyword", searchBox.getSecondKeyword());
        }

        mv.addObject("xAxisData", xAxisData.toString());
        mv.addObject("startDate", SwapDateAndStringUtil.DateToStr(searchBox.getStartDate()));
        mv.addObject("endDate", SwapDateAndStringUtil.DateToStr(searchBox.getEndDate()));
        mv.setViewName("/word_trend");
        return mv;
    }

    @RequestMapping(value="wordZone", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView wordZone(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();

        WordZoneService wordZoneService = new WordZoneServiceImpl();
        SearchBox<WordZone> searchBox = new SearchBox<WordZone>();
        // 处理搜索的热词
        String searchKeyword = request.getParameter("searchKeyword");
        String firstKeyword = request.getParameter("firstKeyword");
        String secondKeyword = request.getParameter("secondKeyword");
        if (!StringUtil.isEmpty(firstKeyword) || !StringUtil.isEmpty(secondKeyword)) {
            if (!StringUtil.isEmpty(secondKeyword) && !firstKeyword.equals(secondKeyword)) {
                searchBox.setFirstKeyword(firstKeyword);
                searchBox.setSecondKeyword(secondKeyword);
            } else {
                searchBox.setFirstKeyword(firstKeyword);
            }
        } else if (!StringUtil.isEmpty(searchKeyword)) {
            searchBox.setFirstKeyword(searchKeyword);
        } else {
            searchBox.setFirstKeyword((String) request.getSession().getAttribute("defaultKeyword"));
        }

        // 处理搜索的日期
        searchBox.setStartDate(SwapDateAndStringUtil.StrToStartDate(request.getParameter("startDate")));
        searchBox.setEndDate(SwapDateAndStringUtil.StrToEndDate(request.getParameter("endDate")));

        searchBox = wordZoneService.getWordTrendList(searchBox);

        List<WordZone> firstWordZoneList = searchBox.getFirstWordList();
        List<WordZone> secondWordZoneList = searchBox.getSecondWordList();
        StringBuffer firstWordZoneData = new StringBuffer();
        StringBuffer secondWordZoneData = new StringBuffer();
        if (firstWordZoneList != null) {
            for (int i = 0; i < firstWordZoneList.size(); i++) {
                if (i == 0) {
                    firstWordZoneData.append("{name: '" + firstWordZoneList.get(i).getProvince() + "',value: " + firstWordZoneList.get(i).getTotalCount() + "}");
                } else {
                    firstWordZoneData.append(Constants.COMMA + "{name: '" + firstWordZoneList.get(i).getProvince() + "',value: " + firstWordZoneList.get(i).getTotalCount() + "}");
                }
            }
            mv.addObject("firstWordZoneData", firstWordZoneData.toString());
            mv.addObject("firstKeyword", searchBox.getFirstKeyword());
        }

        if (secondWordZoneList != null) {
            for (int i = 0; i < secondWordZoneList.size(); i++) {
                if (i == 0) {
                    secondWordZoneData.append("{name: '" + secondWordZoneList.get(i).getProvince() + "',value: " + secondWordZoneList.get(i).getTotalCount() + "}");
                } else {
                    secondWordZoneData.append(Constants.COMMA + "{name: '" + secondWordZoneList.get(i).getProvince() + "',value: " + secondWordZoneList.get(i).getTotalCount() + "}");
                }
            }
            mv.addObject("secondWordZoneData", secondWordZoneData.toString());
            mv.addObject("secondKeyword", searchBox.getSecondKeyword());
        }

        mv.addObject("startDate", SwapDateAndStringUtil.DateToStr(searchBox.getStartDate()));
        mv.addObject("endDate", SwapDateAndStringUtil.DateToStr(searchBox.getEndDate()));
        mv.setViewName("/word_zone");
        return mv;
    }
}