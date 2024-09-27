package dao;

import static database.JdbcUtil.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import vo.SalaryVO;

public class SalaryDAO {
	
	public ArrayList<SalaryVO> getMemberByNo(int m_no) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		SalaryVO salaryVO = null;
		ArrayList<SalaryVO> list = new ArrayList<SalaryVO>();

		try {
			String sql = "SELECT * FROM salary WHERE M_NO = ? ORDER BY SAL_DATE DESC";
			ps = getConnection().prepareStatement(sql);
			ps.setInt(1, m_no);
			rs = ps.executeQuery();


			// 데이터 처리
			while (rs.next()) {
				salaryVO = new SalaryVO();
				salaryVO.setM_no(rs.getInt("M_NO"));
				salaryVO.setSal_date(rs.getDate("SAL_DATE"));
				salaryVO.setSal_salary(rs.getInt("SAL_SALARY"));
				list.add(salaryVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 자원 반환
			close(rs);
			close(ps);
		}
		return list;
	}
}