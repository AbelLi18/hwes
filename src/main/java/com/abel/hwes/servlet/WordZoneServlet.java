package com.abel.hwes.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abel.hwes.Constants;
import com.abel.hwes.model.SearchBox;
import com.abel.hwes.model.WordZone;
import com.abel.hwes.service.WordZoneService;
import com.abel.hwes.service.impl.WordZoneServiceImpl;
import com.abel.hwes.util.StringUtil;
import com.abel.hwes.util.SwapDateAndStringUtil;

/**
 * Servlet implementation class WordZoneServlet
 */
public class WordZoneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public WordZoneServlet() {
        super();
        // TODO Auto-generated constructor stub
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
            request.setAttribute("firstWordZoneData", firstWordZoneData.toString());
            request.setAttribute("firstKeyword", searchBox.getFirstKeyword());
        }

        if (secondWordZoneList != null) {
            for (int i = 0; i < secondWordZoneList.size(); i++) {
                if (i == 0) {
                    secondWordZoneData.append("{name: '" + secondWordZoneList.get(i).getProvince() + "',value: " + secondWordZoneList.get(i).getTotalCount() + "}");
                } else {
                    secondWordZoneData.append(Constants.COMMA + "{name: '" + secondWordZoneList.get(i).getProvince() + "',value: " + secondWordZoneList.get(i).getTotalCount() + "}");
                }
            }
            request.setAttribute("secondWordZoneData", secondWordZoneData.toString());
            request.setAttribute("secondKeyword", searchBox.getSecondKeyword());
        }

        request.setAttribute("startDate", SwapDateAndStringUtil.DateToStr(searchBox.getStartDate()));
        request.setAttribute("endDate", SwapDateAndStringUtil.DateToStr(searchBox.getEndDate()));
        request.getRequestDispatcher("WEB-INF/jsp/word_zone.jsp").forward(request, response);
    }

}
