/**
 * 최초 생성일: 2024-09-25
 * @author 임성현
 * 
 * 최종 수정일: 2024-09-25
 * @author 임성현
 * 
 * 주요 수정 내용: 결재 상신 액션 클래스 생성 및 입력
 * ps. document 테이블이면 클래스 이름을 DocumentDAO로 만들어주셨으면 좋겠어요.
 */
package dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

import vo.DocumentVO;
import static database.JdbcUtil.*;

public class ApprovalDAO {

	public int approval(DocumentVO documentVO) {
		int att = 0;
		Connection con = getConnection();
		PreparedStatement ps = null;
		String sql = "INSERT INTO DOCUMENT (D_NO, D_TITLE, D_CLASS, M_NO, D_CONTENT, D_REQUEST) VALUES (SEQ_D_NO.NEXTVAL, ?, ?, ?, ?, SYSDATE)";
		System.out.println("title: " + documentVO.getD_title());

		try {

			if (documentVO.getM_no() < 1) {
				return att;
			}

			ps = con.prepareStatement(sql);
			ps.setString(1, documentVO.getD_title());
			ps.setString(2, documentVO.getD_class());
			ps.setInt(3, documentVO.getM_no());
			ps.setString(4, documentVO.getD_content());
			att = ps.executeUpdate();

			if (att > 0) {
				commit();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}
		return att;
	}

}
