/**
 * 문서 생성일: 09.22
 * @author 송상훈
 * 
 * 문서 변경일: 09.24
 * @author 송상훈
 * getQnaList, searchQnaList, getQnaDetail 메서드 생성, insertQna 변경
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vo.QnaVO;
import database.JdbcUtil;

public class QnaDAO {
    private Connection conn;

    public QnaDAO() {
        this.conn = JdbcUtil.getConnection();
    }

    // 게시글 목록을 가져오는 메서드
    public List<QnaVO> getQnaList() {
        List<QnaVO> qnaList = new ArrayList<>();
        String sql = "SELECT Q_NO, Q_TITLE, Q_WRITER, Q_RIGHT, Q_DATE, Q_VIEWS FROM QNA ORDER BY Q_NO DESC";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                QnaVO qna = new QnaVO();
                qna.setQ_no(rs.getInt("Q_NO"));
                qna.setQ_title(rs.getString("Q_TITLE"));
                qna.setQ_writer(rs.getString("Q_WRITER"));
                qna.setQ_right(rs.getString("Q_RIGHT"));
                qna.setQ_date(rs.getDate("Q_DATE"));
                qna.setQ_views(rs.getInt("Q_VIEWS"));
                qnaList.add(qna);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return qnaList;
    }

    // 게시글 검색 메서드
    public List<QnaVO> searchQnaList(String searchType, String keyword) {
        List<QnaVO> qnaList = new ArrayList<>();
        String sql = "";

        // 검색 조건에 맞는 SQL 작성
        if (searchType.equals("writer")) {
            sql = "SELECT Q_NO, Q_TITLE, Q_WRITER, Q_RIGHT, Q_DATE, Q_VIEWS FROM QNA WHERE Q_WRITER LIKE ? ORDER BY Q_NO DESC";
        } else if (searchType.equals("title")) {
            sql = "SELECT Q_NO, Q_TITLE, Q_WRITER, Q_RIGHT, Q_DATE, Q_VIEWS FROM QNA WHERE Q_TITLE LIKE ? ORDER BY Q_NO DESC";
        } else if (searchType.equals("content")) {
            sql = "SELECT Q_NO, Q_TITLE, Q_WRITER, Q_RIGHT, Q_DATE, Q_VIEWS FROM QNA WHERE Q_CONTENT LIKE ? ORDER BY Q_NO DESC";
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                QnaVO qna = new QnaVO();
                qna.setQ_no(rs.getInt("Q_NO"));
                qna.setQ_title(rs.getString("Q_TITLE"));
                qna.setQ_writer(rs.getString("Q_WRITER"));
                qna.setQ_right(rs.getString("Q_RIGHT"));
                qna.setQ_date(rs.getDate("Q_DATE"));
                qna.setQ_views(rs.getInt("Q_VIEWS"));
                qnaList.add(qna);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return qnaList;
    }

    // 게시글 등록 메서드
    public void insertQna(String title, String writer, String content) {
        String sql = "INSERT INTO QNA (Q_NO, Q_TITLE, Q_WRITER, Q_DATE, Q_CONTENT) "
                   + "VALUES (SEQ_Q_NO.NEXTVAL, ?, ?, SYSDATE, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, writer);
            pstmt.setString(3, content);
            pstmt.executeUpdate();
            JdbcUtil.commit(conn);
        } catch (SQLException e) {
            JdbcUtil.rollback(conn);
            e.printStackTrace();
        }
    }

    // 게시글 상세 정보 가져오는 메서드
    public QnaVO getQnaDetail(int q_no) {
        QnaVO qna = null;
        String sql = "SELECT Q_NO, Q_TITLE, Q_WRITER, Q_RIGHT, Q_DATE, Q_VIEWS, Q_CONTENT "
                   + "FROM QNA WHERE Q_NO = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, q_no);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                qna = new QnaVO();
                qna.setQ_no(rs.getInt("Q_NO"));
                qna.setQ_title(rs.getString("Q_TITLE"));
                qna.setQ_writer(rs.getString("Q_WRITER"));
                qna.setQ_right(rs.getString("Q_RIGHT"));
                qna.setQ_date(rs.getDate("Q_DATE"));
                qna.setQ_views(rs.getInt("Q_VIEWS"));
                qna.setQ_content(rs.getString("Q_CONTENT"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return qna;
    }
}
