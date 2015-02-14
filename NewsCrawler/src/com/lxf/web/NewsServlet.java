package com.lxf.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lxf.dao.bean.NewsBean;
import com.lxf.dao.imp.NewsDao;
import com.lxf.dao.inf.NewsDaoInf;

public class NewsServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("id");
		NewsDaoInf ni = new NewsDao();
		NewsBean bean = ni.queryOne(id);
		request.setAttribute("news", bean);
		request.getRequestDispatcher("news.jsp").forward(request, response);
	}

}
