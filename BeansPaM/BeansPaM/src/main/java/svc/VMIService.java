/**
 * @author 강동준
 * @since 09.30
 * @see controller.FMSController
 */
package svc;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.StockDAO;

public class VMIService {
	/**
	 * request에 저장된 sql문들을 가져와 하나씩 데이터베이스에 반영하는 메소드
	 * 
	 * @author 강동준
	 * @since 09.30
	 * @see controller.FMSController
	 */
	public void saveChanges(HttpServletRequest request, HttpServletResponse response) {
		try {
	        // POST로 전송된 문자열 데이터를 받아오기
	        BufferedReader reader = request.getReader();
	        StringBuilder stringBuilder = new StringBuilder();
	        String line;
	        int result = 0;
	        StockDAO dao = new StockDAO();
	        
	        // 데이터를 읽어와서 문자열로 저장
	        while ((line = reader.readLine()) != null) {
	            stringBuilder.append(line);
	        }

	        String[] sqls = stringBuilder.toString().split(",");
	        for (String sql : sqls) {
				result += dao.updateStock(sql) ? 1 : 0;
			}

	        response.setContentType("text/plain");
	        response.setCharacterEncoding("UTF-8");
	        if (result == sqls.length) {
	        	response.getWriter().write("모든 데이터 저장에 성공하였습니다.");
	        } else {
	        	response.getWriter().write("일부 데이터 저장에 성공하였습니다: " + result + "/" + sqls.length);
			}
		} catch (Exception e) {
	        response.setContentType("text/plain");
	        response.setCharacterEncoding("UTF-8");
	        try {
				response.getWriter().write("더이터 저장에 실패하였습니다.");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
