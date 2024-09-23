/**
 * 문서 생성일: 09.22
 * @author 송상훈
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.JdbcUtil;

public class QnaDAO {
    /**
     * @author 송상훈
     */
    public void insertQna(String title, String writer, String content, String category) {
        String sql = "INSERT INTO QNA (Q_NO, Q_TITLE, Q_WRITER, Q_RIGHT, Q_DATE, Q_VIEWS, Q_CONTENT) "
                   + "VALUES (SEQ_QNA.NEXTVAL, ?, ?, ?, SYSDATE, 0, ?)";

        try (PreparedStatement pstmt = JdbcUtil.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, writer);
            pstmt.setString(3, category);
            pstmt.setString(4, content);

            // 로그로 값을 확인
            System.out.println("제목: " + title);
            System.out.println("작성자: " + writer);
            System.out.println("내용: " + content);
            System.out.println("카테고리: " + category);

            pstmt.executeUpdate();
            JdbcUtil.commit(JdbcUtil.getConnection());
            System.out.println("게시글이 성공적으로 저장되었습니다.");
        } catch (SQLException e) {
            JdbcUtil.rollback(JdbcUtil.getConnection());
            e.printStackTrace();
            System.out.println("게시글 저장 중 오류 발생: " + e.getMessage());
        }
    }

}
