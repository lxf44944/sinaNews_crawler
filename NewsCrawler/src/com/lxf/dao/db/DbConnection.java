package com.lxf.dao.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库连接类
 * 
 * @author 刘向峰
 * 
 */
public class DbConnection {
	

	//oracle连接写法
	private static String diver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/news?user=lxf&password=lxf";
	private static String username = "lxf";
	private static String password = "lxf";

	static {
		try {
			//加载驱动
			Class.forName(diver);
		} catch (ClassNotFoundException e) {
			System.out.println("加载不到驱动！");
			e.printStackTrace();
		}
	}

	/**
	 * 获取连接
	 * 
	 * @return connection
	 */
	public static Connection getConn() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (Exception e) {
			System.out.println("连接错误，请检查您的URL、用户名和密码！");
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭资源
	 * 
	 * @param connection
	 * @param statement
	 * @param resultSet
	 */
	public static void close(Connection connection, Statement statement,
			ResultSet resultSet) {
		try {
			if (connection != null) {
				connection.close();
				connection = null;
			}
			if (statement != null) {
				statement.close();
				statement = null;
			}
			if (resultSet != null) {
				resultSet.close();
				resultSet = null;
			}
		} catch (SQLException e) {
			System.out.println("关闭资源时出现错误！");
			e.printStackTrace();
		}
	}
}
