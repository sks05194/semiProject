/**
 * @since 09.30
 * @author 강동준
 */
package dao;

import database.JdbcUtil;

import java.sql.SQLException;
import java.sql.Statement;

public class StockDAO {
	/**
	 * @since 09.30
	 * @author 강동준
	 * @see svc.VMIService
	 */
	public boolean updateStock(String sql) {
		boolean result = false;
		Statement st = null;

		try {
			st = JdbcUtil.getConnection().createStatement();
			result = st.executeUpdate(sql) > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(st);
		}

		return result;
	}
}