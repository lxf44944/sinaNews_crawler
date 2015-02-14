package com.lxf.crawler;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.lxf.dao.bean.NewsBean;
import com.lxf.dao.imp.NewsDao;
import com.lxf.dao.inf.NewsDaoInf;

/**
 * <爬虫程序> 从新浪新闻中爬取新闻分类、标题及内容 (htmlparser.jar)
 * 
 * @author 刘向峰
 */
public class RollNews {

	private final static String URL = "http://roll.news.sina.com.cn/s/channel.php";//新闻链接
	private final static String ENCODING = "gb2312";
	private final static String TYPE = "roll";//新闻类型
	private final static String LINK_TO_WORD = "\" target=\"_blank\">";//A标签 “href内容”到“文本”之间的部分
	private static NewsDaoInf dao = new NewsDao();// 引用dao进行对数据库的操作

	/**
	 * 抓取新浪滚动信息
	 * 
	 * @param type
	 *            新闻类别
	 * @param url
	 *            新闻标题列表页面链接
	 * @param sLinkToWord
	 *            a标签 “href正文”结束位置到“文本”之间的部分，如：" target="_blank">
	 */
	public static void getNews() {

		NodeFilter filter = new TagNameFilter("ul");
		Parser parser = new Parser();
		NodeList list = null;
		try {
			parser.setURL(URL);// 互联网模块的地址
			parser.setEncoding(ENCODING);
			list = parser.extractAllNodesThatMatch(filter);
		} catch (ParserException e) {
			System.out.println("抓取信息出错，出错信息为：");
			e.printStackTrace();
		}
		for (int i = 0; i < list.size(); i++) {
			Tag node = (Tag) list.elementAt(i);
			for (int j = 1; j < node.getChildren().size(); j++) {
				String textStr = node.getChildren().elementAt(j).toHtml()
						.trim();
				String link = getLink(textStr);// 获取链接
				String title = getTitle(textStr);// 获取标题
				String body = getNewsBody(link);// 获取内容
				if (!"".equals(link)&& !"".equals(title) && !"".equals(body)) {
					/** 写入数据库 */
					NewsBean newsBean = new NewsBean(0, title, body, link,
							link.substring(link.lastIndexOf("/") - 10,
									link.lastIndexOf("/")), TYPE);
					dao.add(newsBean);
				}
			}
		}
	}

	/**
	 * 获得A标签中的链接
	 * 
	 * @param texrStr
	 *            抓取下来页面转换出的字符串
	 * @return 子页面链接
	 */
	private static String getLink(String texrStr) {
		// 链接字符串
		String link = "";
		if (texrStr.length() > 0) {
			int linkbegin = texrStr.indexOf("href=\"");// 截取<a>链接字符串起始位置
			int linkend = texrStr.indexOf(LINK_TO_WORD);// 截取<a>链接字符串结束位置
			String sublink = texrStr.substring(linkbegin + "href=\"".length(),
					linkend);

			if (sublink.indexOf("target") != -1) {
				link = sublink.substring(0, sublink.indexOf("\""));
			} else {
				link = sublink;// 链接字符串
			}
		}
		return link;
	}

	/**
	 * 获取A标签中的文本内容
	 * 
	 * @param textStr
	 *            抓取下来页面转换出的字符串
	 * @return 标题
	 */
	private static String getTitle(String textStr) {
		int titlebegin = textStr.indexOf(LINK_TO_WORD);
		int titleend = textStr.indexOf("</a>");
		String title = textStr.substring(titlebegin + LINK_TO_WORD.length(),
				titleend).trim();
		System.out.println("正在抓取: " + title);
		// 通过标题判断该新闻是否已经存在
		if (title.contains("视频:") || title.contains("视频：")) {
			System.out.println("【无法获得视频新闻】");
			return "";
		}
		if (title.contains("(图)")) {
			title = title.replace("(图)", "");
		}
		if (dao.hasNews(title)) {
			System.out.println("【该记录已经存在】");
			return "";
		}

		return title;
	}

	/**
	 * 新闻内容处理
	 * 
	 * @param link
	 *            内容的链接
	 * @return 内容
	 */
	private static String getNewsBody(String link) {
		NodeFilter bodyfilter = new AndFilter(new TagNameFilter("div"),
				new HasAttributeFilter("id", "artibody"));
		Parser bodyparser = new Parser();
		NodeList bodylist = null;
		try {
			bodyparser.setURL(link);// 地址url
			bodyparser.setEncoding(ENCODING);
			bodylist = bodyparser.extractAllNodesThatMatch(bodyfilter);
		} catch (ParserException e) {
			System.out.println("抓取信息子页面出错，出错信息为：");
			e.printStackTrace();
			return "";
		}

		// 新闻内容字符串
		if (bodylist.elementAt(0) == null) {
			System.out.println("【新闻无内容】");
			return "";
		}
		String newstextstr = bodylist.elementAt(0).toHtml().trim();
		// 只保留正文内容，保留P标签以保持其排版
		int bodybegin = newstextstr.indexOf("<p>");
		int bodyend = newstextstr.lastIndexOf("</p>") + 4;

		String body = "";
		if (bodybegin < 0 || bodybegin >= bodyend) {
			body = newstextstr;
		} else {
			body = newstextstr.substring(bodybegin, bodyend);
		}
		int bodyimgbegin = newstextstr.indexOf("<div class=\"img_wrapper\">");
		int bodyimgend = newstextstr.lastIndexOf("<span class=\"img_descr\">");
		if (bodyimgbegin >= 0 && bodyimgbegin < bodyimgend) {
			body = newstextstr.substring(bodyimgbegin, bodyimgend) + "</div>"
					+ body;
		}
		int bodyremovebegin = body.indexOf("<div id=\"news_like\"");
		int bodyremoveend = body.lastIndexOf("<p class=\"fr\">");
		if (bodyremovebegin > 0 && bodyremovebegin < bodyremoveend) {
			body = body.replace(body.substring(bodyremovebegin, bodyremoveend),
					"");
		}
		return body;

	}

}