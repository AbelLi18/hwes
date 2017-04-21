package com.abel.hwes.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abel.hwes.service.WordPropertyService;
import com.abel.hwes.service.impl.WordPropertyServiceImpl;
import com.abel.hwes.util.StringUtil;

/**
 * Servlet implementation class WordPropertyServlet
 */
public class WordPropertyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public WordPropertyServlet() {
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
            request.setAttribute("firstWordContext", firstWordContext);
            request.setAttribute("firstKeyword", firstKeyword);
        }

        List<String> secondWordContext;
        if (!StringUtil.isEmpty(secondKeyword)) {
            secondWordContext = wordPropertyService.getWordContextList(secondKeyword);
            request.setAttribute("secondWordContext", secondWordContext);
            request.setAttribute("secondKeyword", secondKeyword);
        }

        request.getRequestDispatcher("WEB-INF/jsp/word_property.jsp").forward(request, response);
    }
}
