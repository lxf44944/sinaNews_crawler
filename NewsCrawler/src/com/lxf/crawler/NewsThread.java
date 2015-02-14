package com.lxf.crawler;

/**
 * 执行循环爬取的线程
 * 
 * @author 刘向峰
 * 
 */
public class NewsThread implements Runnable {

	private static final long TIME_OUT = 1000 * 60 * 5;// 间隔5分钟爬取一次
	private static final NewsThread NEWS_THREAD = new NewsThread();//创建
	private volatile static boolean isStart = true;//共享变量（shared variable）信号,默认启动线程

	/**
	 * 私有化构造器，创建单例模式
	 */
	private NewsThread() {
		// 避免不小心在类的内部调用构造器
		//throw new AssertionError();
	}

	/**
	 * 获取单例
	 * 
	 * @return NewsThread单例对象
	 */
	public static NewsThread getInstance() {
		return NEWS_THREAD;
	}

	/**
	 * 使用共享变量（shared variable）发出信号
	 * 
	 * @param status
	 *            启动线程输入true,停止线程输入false
	 */
	public static void changeStatus(boolean status) {
		isStart = status;
	}

	@Override
	public void run() {
		int i = 0;// 执行次数
		while (isStart) {
			System.out.println("************************开始第" + ++i
					+ "次抓取************************");
			RollNews.getNews();
			System.out.println("************************结束第" + i
					+ "次抓取************************");
			try {
				Thread.sleep(TIME_OUT);
			} catch (InterruptedException e) {
				System.out.println("sleep interrupted");
			}

		}
	}

}
