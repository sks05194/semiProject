/**
 * 최초 생성일: 2024-09-11
 * @author 강동준
 * 
 * 마지막 수정일: 2024-09-23
 * @author 강동준
 * 
 * 주요 수정 내용: ServerManager 클래스 생성에 따른 일부 메소드 변화
 */

package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import controller.ServerManager;

public class JdbcUtil {
	private static Connection conn = null;

	private JdbcUtil() { }

	public static Connection getConnection() {
		return conn;
	}

	public static void connecting() {
		if (!ServerManager.getIsRunManager())
			return;

		if (conn == null) {
			try {
				Context initCtx = new InitialContext();
				DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/BeansPaMDB");

				conn = ds.getConnection();
				conn.setAutoCommit(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void connClose() {
		if (!ServerManager.getIsRunManager())
			return;

		if (conn == null)
			return;

		try {
			conn.close();
			conn = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void close(Statement st) {
		if (st == null)
			return;

		try {
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void close(PreparedStatement ps) {
		if (ps == null)
			return;

		try {
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void close(ResultSet rs) {
		if (rs == null)
			return;

		try {
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void commit(Connection conn) {
		if (conn == null)
			return;

		try {
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void rollback(Connection conn) {
		if (conn == null)
			return;

		try {
			conn.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}