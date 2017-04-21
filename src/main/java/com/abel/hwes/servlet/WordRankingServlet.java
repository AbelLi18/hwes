package com.abel.hwes.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abel.hwes.Constants;
import com.abel.hwes.model.PageBean;
import com.abel.hwes.model.WordCount;
import com.abel.hwes.service.WordCountService;
import com.abel.hwes.service.impl.WordCountServiceImpl;

/**
 * Servlet implementation class WordRankingServlet
 */
public class WordRankingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public WordRankingServlet() {
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
        WordCountService wordCountService = new WordCountServiceImpl();
        PageBean<WordCount> pageBean = wordCountService.getWordList(request.getParameter("currentPage"));
        List<WordCount> wordRanking = pageBean.getList();
        request.setAttribute("wordRankingList", wordRanking);

        StringBuffer wordRankingData = new StringBuffer();
        if (wordRanking != null) {
            for (int i = 0; i < wordRanking.size(); i++) {
                if (i == 0) {
                    wordRankingData.append("{name: '" + wordRanking.get(i).getKeyword() + "',value: " + wordRanking.get(i).getTotalCount() + ",itemStyle: createRandomItemStyle()}");
                } else {
                    wordRankingData.append(Constants.COMMA + "{name: '" + wordRanking.get(i).getKeyword() + "',value: " + wordRanking.get(i).getTotalCount() + ",itemStyle: createRandomItemStyle()}");
                }
            }
            request.setAttribute("wordRankingData", wordRankingData.toString());
        }

        request.setAttribute("pageBean", pageBean);
        request.getRequestDispatcher("WEB-INF/jsp/word_ranking.jsp").forward(request, response);
    }

}
