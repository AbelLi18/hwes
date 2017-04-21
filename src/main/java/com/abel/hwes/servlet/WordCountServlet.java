package com.abel.hwes.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abel.hwes.model.WordCount;
import com.abel.hwes.service.WordCountService;
import com.abel.hwes.service.impl.WordCountServiceImpl;

/**
 * Servlet implementation class WordCountServlet
 */
public class WordCountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public WordCountServlet() {
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
        List<WordCount> wordCount = wordCountService.getWordList();
        request.setAttribute("wordCountList", wordCount);
        request.getSession().setAttribute("defaultKeyword", wordCount.get((int)(Math.random() * wordCount.size())).getKeyword());
        request.getRequestDispatcher("WEB-INF/jsp/welcome.jsp").forward(request, response);
    }
}
