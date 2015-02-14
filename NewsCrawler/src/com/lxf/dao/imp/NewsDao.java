package com.lxf.dao.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.lxf.dao.bean.NewsBean;
import com.lxf.dao.db.DbConnection;
import com.lxf.dao.inf.NewsDaoInf;

public class NewsDao implements NewsDaoInf {

	Connection conn = null;
	PreparedStatement st = null;
	ResultSet rs = null;

	public NewsDao() {
		// TODO Auto-generated constructor stub
	}

	public NewsDao(Connection conn, PreparedStatement st, ResultSet rs) {
		super();
		this.conn = conn;
		this.st = st;
		this.rs = rs;
	}

	@Override
	public void add(NewsBean newsBean) {
		// TODO Auto-generated method stub
		String sql = "insert into sinanews(title,content,url,newsdate,type) values(?,?,?,?,?);";
		try {
			// 加载驱动，建立连接
			conn = DbConnection.getConn();
			// 创建sql,获得statement
			st = conn.prepareStatement(sql);
			st.setString(1, newsBean.getTitle());

			// st.setCharacterStream(2, newsBean.getContent(),
			// newsBean.getContent().length());
			// st.get
			st.setString(2, newsBean.getContent());
			st.setString(3, newsBean.getUrl());
			st.setString(4, newsBean.getNewsdate());
			st.setString(5, newsBean.getType());
			// 执行sql,返回结果集
			int isSuccess = st.executeUpdate();
			if (isSuccess > 0) {
				System.out.println("插入成功");
			} else {
				System.out.println("【插入失败】");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbConnection.close(conn, st, rs);
		}
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hasNews(String title) {
		boolean has = false;
		String sql = "select id from sinanews where title = '" + title + "' ";
		// System.out.println(sql);
		try {
			// 加载驱动，建立连接
			conn = DbConnection.getConn();
			// 创建sql,获得statement
			st = conn.prepareStatement(sql);
			// st.setString(1, title);
			// 执行sql,返回结果集
			rs = st.executeQuery(sql);
			if (rs.next()) {
				has = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			has = false;
			e.printStackTrace();
		} finally {
			DbConnection.close(conn, st, rs);
		}
		return has;
	}

	@Override
	public List<NewsBean> query() {
		// TODO Auto-generated method stub
		List<NewsBean> list = new ArrayList<NewsBean>();
		String sql = "select id,title,newsdate,type from sinanews order by newsdate desc";
		try {
			// 加载驱动，建立连接
			conn = DbConnection.getConn();
			// 创建sql,获得statement
			st = conn.prepareStatement(sql);
			// 执行sql,返回结果集
			rs = st.executeQuery(sql);
			while (rs.next()) {
				NewsBean bean = new NewsBean();
				bean.setId(rs.getInt("id"));
				bean.setTitle(rs.getString("title"));
				// bean.setContent(rs.getString("content"));
				bean.setNewsdate(rs.getString("newsdate"));
				bean.setType(rs.getString("type"));
				// bean.setUrl(rs.getString("url"));
				list.add(bean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbConnection.close(conn, st, rs);
		}
		return list;
	}

	@Override
	public NewsBean queryOne(String id) {
		// TODO Auto-generated method stub
		NewsBean bean = new NewsBean();
		String sql = "select * from sinanews where id=" + id;
		try {
			// 加载驱动，建立连接
			conn = DbConnection.getConn();
			// 创建sql,获得statement
			st = conn.prepareStatement(sql);
			// 执行sql,返回结果集
			rs = st.executeQuery(sql);
			if (rs.next()) {

				bean.setId(rs.getInt("id"));
				bean.setTitle(rs.getString("title"));
				bean.setContent(rs.getString("content"));
				bean.setNewsdate(rs.getString("newsdate"));
				bean.setType(rs.getString("type"));
				bean.setUrl(rs.getString("url"));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbConnection.close(conn, st, rs);
		}
		return bean;
	}

}
