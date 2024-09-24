package dao;

import static database.JdbcUtil.close;
import static database.JdbcUtil.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import vo.SalaryVO;

public class SalaryDAO {
	Connection con = null;

	/* 연결객체 생성 메소드 */
	public void setConnection() {
		con = getConnection();
	}

	public double salarysum(int m_no) {
		PreparedStatement ps = null;

		ResultSet rs = null;
		double totalSalary = 0;
		try {
			// SQL 쿼리 실행
			String sql = "SELECT sum(sal_salary) FROM salary WHERE M_NO = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, m_no); // M_NO에 해당하는 값 설정

			// 쿼리 결과 가져오기
			rs = ps.executeQuery();

			if (rs.next()) {
				totalSalary = rs.getDouble(1); // 첫 번째 컬럼 값 가져오기
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 자원 해제
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return totalSalary;
	}
	public ArrayList<Double> salaryval(int m_no) {
		ArrayList<Double> salaryvalList = new ArrayList<Double>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		double salaryval = 0;
		try {
			// SQL 쿼리 실행
			String sql = "SELECT sal_salary*0.1 sal_salaryval FROM salary WHERE M_NO = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, m_no); // M_NO에 해당하는 값 설정
			// 쿼리 결과 가져오기
			rs = ps.executeQuery();

			while (rs.next()) {
				salaryval= rs.getDouble("sal_salaryval"); 
				salaryvalList.add(salaryval);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 자원 해제
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return salaryvalList;
	}

	public ArrayList<SalaryVO> getMemberByNo(int m_no) {
		System.out.println("DAO m_no:" + m_no);
		PreparedStatement ps = null;

		ResultSet rs = null;
		SalaryVO salaryVO = null;
		ArrayList<SalaryVO> list = new ArrayList<SalaryVO>();
		try {
			// Connection 설정
			con = getConnection();

			String sql = "SELECT * FROM salary WHERE M_NO = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, m_no);
			rs = ps.executeQuery();

			System.out.println("con m_no:" + m_no);

			// 데이터 처리
			while (rs.next()) {
				System.out.println("while 문");
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