/**
 * 문서 생성일 24.09.06
 * @author 한지수
 */

package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import vo.MaterialVO;

public class MaterialDAO {
	Connection conn = null;
	
	public MaterialDAO() {
		conn = database.JdbcUtil.getConnection();
	}
	

	/**
	 * 리스트에 넣기 위해 모든 자재를 가져오는 함수
	 * @author 한지수
	 * @see database.JdbcUtil
	 */
    public List<MaterialVO> getAllMaterials() throws SQLException {
        List<MaterialVO> materials = new ArrayList<>();
        String query = "SELECT * FROM material";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
             
            while (rs.next()) {
                MaterialVO material = new MaterialVO();
                material.setT_corr(rs.getString("t_corr"));
                material.setS_incom(rs.getString("s_incom"));
                material.setS_date(rs.getDate("s_date"));
                material.setT_value(rs.getInt("t_value"));
                material.setP_no(rs.getString("p_no"));
                material.setM_no(rs.getInt("m_no"));
                material.setM_dept(rs.getString("m_dept"));
                material.setS_amount(rs.getInt("s_amount"));
                material.setW_no(rs.getInt("w_no"));
                materials.add(material);
            }
        }
        return materials;
    }

    /**
     * 자재 추가
     */
    public void addMaterial(MaterialVO material) throws SQLException {
        String query = "INSERT INTO material (t_corr, s_incom, s_date, t_value, p_no, m_no, m_dept, s_amount, w_no) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
             
            pstmt.setString(1, material.getT_corr());
            pstmt.setString(2, material.getS_incom());
            pstmt.setDate(3, material.getS_date());
            pstmt.setInt(4, material.getT_value());
            pstmt.setString(5, material.getP_no());
            pstmt.setInt(6, material.getM_no());
            pstmt.setString(7, material.getM_dept());
            pstmt.setInt(8, material.getS_amount());
            pstmt.setInt(9, material.getW_no());
            pstmt.executeUpdate();
        }
    }

    public void editMaterial(MaterialVO material) throws SQLException {
        String query = "UPDATE material SET t_corr = ?, s_incom = ?, s_date = ?, t_value = ?, p_no = ?, m_no = ?, m_dept = ?, s_amount = ?, w_no = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
             
            pstmt.setString(1, material.getT_corr());
            pstmt.setString(2, material.getS_incom());
            pstmt.setDate(3, material.getS_date());
            pstmt.setInt(4, material.getT_value());
            pstmt.setString(5, material.getP_no());
            pstmt.setInt(6, material.getM_no());
            pstmt.setString(7, material.getM_dept());
            pstmt.setInt(8, material.getS_amount());
            pstmt.setInt(9, material.getW_no());
            pstmt.executeUpdate();
        }
    }

    public void deleteMaterials(String[] tCorrs) {
        PreparedStatement pstmt = null;

        try {
            StringBuilder sql = new StringBuilder("DELETE FROM material WHERE t_corr IN (");
            for (int i = 0; i < tCorrs.length; i++) {
                sql.append("?");
                if (i < tCorrs.length - 1) {
                    sql.append(", ");
                }
            }
            sql.append(")");

            pstmt = conn.prepareStatement(sql.toString());

            for (int i = 0; i < tCorrs.length; i++) {
                pstmt.setString(i + 1, tCorrs[i].trim());
            }

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("삭제된 행 수: " + affectedRows);
            } else {
                System.out.println("삭제할 행이 없습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 오류 로그 출력
        } finally {
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) {}
        }
    }
}