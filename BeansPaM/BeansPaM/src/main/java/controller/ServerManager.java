/**
 * 최초 문서 생성일: 09.23
 * @author 강동준
 * 
 * 생성이유: 
 * 		1. 서버가 열리고 닫힐 때 Connection 객체가 생성되게 하기 위함.
 * 		2. 서버가 실행되자마자 실험을 하기 위한 사이트를 열기 위함.
 */

package controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import database.JdbcUtil;

@WebListener
public class ServerManager implements ServletContextListener {
	private static boolean isRunManager = false;
	
	public static boolean getIsRunManager() { return isRunManager; }
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		isRunManager = true;
		
		JdbcUtil.connecting();
		
		String url = "http://localhost:8090/BeansPaM/fms/admin";
		String edgePath = "C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe";
//		String chromePath = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
		
		try {
			// if (Desktop.isDesktopSupported())
			//	Desktop.getDesktop().browse(new URI(url)); // 기본 브라우저에서 그냥 열기
			new ProcessBuilder(edgePath, "--new-window", url).start(); // Edge에서 열기
//			new ProcessBuilder(chromePath, "--new-window", url).start(); // 크롬에서 열기
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		isRunManager = false;
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		isRunManager = true;
		
		JdbcUtil.connClose();
		
		isRunManager = false;
	}
}