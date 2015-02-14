package com.lxf.dao.bean;

public class NewsBean {

	private int id;
	private String title;
	private String content;
	private String url;
	private String newsdate;
	private String type;

	public NewsBean() {
		// TODO Auto-generated constructor stub
	}

	public NewsBean(int id, String title, String content, String url,
			String newsdate, String type) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.url = url;
		this.newsdate = newsdate;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNewsdate() {
		return newsdate;
	}

	public void setNewsdate(String newsdate) {
		this.newsdate = newsdate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
