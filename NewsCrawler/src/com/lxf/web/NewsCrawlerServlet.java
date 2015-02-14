package com.lxf.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lxf.crawler.NewsThread;
/**
 * 请求启用/停止爬虫线程
 * @author 刘向峰
 *
 */
public class NewsCrawlerServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = request.getParameter("op");
		NewsThread newsThread = NewsThread.getInstance();// 获取线程单例
		Thread thread = new Thread(newsThread);// 创建线程
		if ("start".equals(op)) {
			NewsThread.changeStatus(true);// 先设置状态变量
			thread.start();
			op = "stop";
			System.out.println("线程开始！！！");
		} else if ("stop".equals(op)) {

			NewsThread.changeStatus(false);// 先设置状态变量
			thread.interrupt();
			op = "start";
			System.out.println("线程中断！！！");
		}
		thread = null;
		request.getSession().setAttribute("op", op);
		response.sendRedirect("index.jsp");
	}

}
