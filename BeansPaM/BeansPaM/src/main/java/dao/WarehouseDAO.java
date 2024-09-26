package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import database.JdbcUtil;
import vo.WarehouseVO;

public class WarehouseDAO {
    
    // 모든 창고 데이터를 조회하는 메서드
    public List<WarehouseVO> getAllWarehouses() {
        List<WarehouseVO> warehouses = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String query = "SELECT W_NO, W_LOC, W_TEMP, W_HUMI, M_NO, W_ISSUE FROM WAREHOUSE";

        try {
            // 연결 생성 (JdbcUtil.connecting() 필요없다면 생략)
            conn = JdbcUtil.getConnection();  
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                WarehouseVO warehouse = new WarehouseVO();
                warehouse.setW_no(rs.getInt("W_NO"));
                warehouse.setW_loc(rs.getString("W_LOC"));
                warehouse.setW_temp(rs.getDouble("W_TEMP"));  
                warehouse.setW_humi(rs.getDouble("W_HUMI")); 
                warehouse.setM_no(rs.getInt("M_NO"));
                warehouse.setW_issue(rs.getString("W_ISSUE"));
                warehouses.add(warehouse);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(rs);
            JdbcUtil.close(pstmt);
//            JdbcUtil.close(con);  // 연결 종료
        }
        
        return warehouses;
    }
}
