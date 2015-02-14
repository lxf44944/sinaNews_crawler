package com.lxf.dao.inf;

import java.util.List;

import com.lxf.dao.bean.NewsBean;

public interface NewsDaoInf {
	public void add(NewsBean newsBean);
	public void delete();
	public boolean hasNews(String title);
	public List<NewsBean> query();
	public NewsBean queryOne(String id);
	
}
