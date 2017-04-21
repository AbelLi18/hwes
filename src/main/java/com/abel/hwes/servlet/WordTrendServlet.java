package com.abel.hwes.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abel.hwes.Constants;
import com.abel.hwes.model.SearchBox;
import com.abel.hwes.model.WordTrend;
import com.abel.hwes.service.WordTrendService;
import com.abel.hwes.service.impl.WordTrendServiceImpl;
import com.abel.hwes.util.StringUtil;
import com.abel.hwes.util.SwapDateAndStringUtil;

/**
 * Servlet implementation class WordTrendServlet
 */
public class WordTrendServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public WordTrendServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            request.setAttribute("firstWordTrendData", firstWordTrendData.toString());
            request.setAttribute("firstKeyword", searchBox.getFirstKeyword());
        }

        if (secondWordTrendList != null) {
            for (int i = 0; i < secondWordTrendList.size(); i++) {
                if (i == 0) {
                    secondWordTrendData.append(secondWordTrendList.get(i).getTotalCount());
                } else {
                    secondWordTrendData.append(Constants.COMMA + secondWordTrendList.get(i).getTotalCount());
                }
            }
            request.setAttribute("secondWordTrendData", secondWordTrendData.toString());
            request.setAttribute("secondKeyword", searchBox.getSecondKeyword());
        }

        request.setAttribute("xAxisData", xAxisData.toString());
        request.setAttribute("startDate", SwapDateAndStringUtil.DateToStr(searchBox.getStartDate()));
        request.setAttribute("endDate", SwapDateAndStringUtil.DateToStr(searchBox.getEndDate()));
        request.getRequestDispatcher("WEB-INF/jsp/word_trend.jsp").forward(request, response);
    }

}
