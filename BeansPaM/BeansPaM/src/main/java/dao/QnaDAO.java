/**
 * 문서 생성일: 09.22
 * @author 송상훈
 * 
 * 문서 변경일: 09.24
 * @author 송상훈
 * 생성: getQnaList, deleteQna, updateReadCount
 * 수정: getTotalQnaCount, searchQnaList, getQnaDetail
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

    // 게시글 목록을 가져오는 메서드 (페이징 처리 포함)
    public List<QnaVO> getQnaList(int page, int limit) {
        List<QnaVO> qnaList = new ArrayList<>();
        String sql = "SELECT * FROM (SELECT ROWNUM rnum, Q_NO, Q_TITLE, Q_WRITER, Q_DATE, Q_VIEWS "
                   + "FROM (SELECT Q_NO, Q_TITLE, Q_WRITER, Q_DATE, Q_VIEWS FROM QNA ORDER BY Q_NO DESC)) "
                   + "WHERE rnum BETWEEN ? AND ?";

        int startRow = (page - 1) * limit + 1; // 시작하는 rownum 계산
        int endRow = startRow + limit - 1;     // 끝나는 rownum 계산

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, startRow);
            pstmt.setInt(2, endRow);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                QnaVO qna = new QnaVO();
                qna.setQ_no(rs.getInt("Q_NO"));
                qna.setQ_title(rs.getString("Q_TITLE"));
                qna.setQ_writer(rs.getString("Q_WRITER"));
                qna.setQ_date(rs.getDate("Q_DATE"));
                qna.setQ_views(rs.getInt("Q_VIEWS"));
                qnaList.add(qna);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return qnaList;
    }

    // 전체 게시글 수를 구하는 메서드
    public int getTotalQnaCount() {
        int totalCount = 0;
        String sql = "SELECT COUNT(*) FROM QNA";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalCount;
    }

    // 게시글 검색 메서드
    public List<QnaVO> searchQnaList(String searchType, String keyword) {
        List<QnaVO> qnaList = new ArrayList<>();
        String sql = "";

        if (searchType.equals("writer")) {
            sql = "SELECT Q_NO, Q_TITLE, Q_WRITER, Q_DATE, Q_VIEWS FROM QNA WHERE Q_WRITER LIKE ? ORDER BY Q_NO DESC";
        } else if (searchType.equals("title")) {
            sql = "SELECT Q_NO, Q_TITLE, Q_WRITER, Q_DATE, Q_VIEWS FROM QNA WHERE Q_TITLE LIKE ? ORDER BY Q_NO DESC";
        } else if (searchType.equals("content")) {
            sql = "SELECT Q_NO, Q_TITLE, Q_WRITER, Q_DATE, Q_VIEWS FROM QNA WHERE Q_CONTENT LIKE ? ORDER BY Q_NO DESC";
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                QnaVO qna = new QnaVO();
                qna.setQ_no(rs.getInt("Q_NO"));
                qna.setQ_title(rs.getString("Q_TITLE"));
                qna.setQ_writer(rs.getString("Q_WRITER"));
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
            JdbcUtil.commit();
        } catch (SQLException e) {
            JdbcUtil.rollback();
            e.printStackTrace();
        }
    }

    // 게시글 상세 정보 가져오는 메서드 (조회수 증가 포함)
    public QnaVO getQnaDetail(int q_no) {
        QnaVO qna = null;
        String sql = "SELECT Q_NO, Q_TITLE, Q_WRITER, Q_DATE, Q_VIEWS, Q_CONTENT "
                   + "FROM QNA WHERE Q_NO = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, q_no);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // 게시글 정보를 가져오기 전에 조회수 증가
                updateReadCount(q_no);

                qna = new QnaVO();
                qna.setQ_no(rs.getInt("Q_NO"));
                qna.setQ_title(rs.getString("Q_TITLE"));
                qna.setQ_writer(rs.getString("Q_WRITER"));
                qna.setQ_date(rs.getDate("Q_DATE"));
                qna.setQ_views(rs.getInt("Q_VIEWS"));
                qna.setQ_content(rs.getString("Q_CONTENT"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return qna;
    }

    // 게시글 삭제 메서드
    public void deleteQna(int q_no) {
        String sql = "DELETE FROM QNA WHERE Q_NO = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, q_no);
            pstmt.executeUpdate();
            JdbcUtil.commit();  // 커밋
        } catch (SQLException e) {
            JdbcUtil.rollback();  // 에러 발생 시 롤백
            e.printStackTrace();
        }
    }

    // 조회수 업데이트 메서드
    public int updateReadCount(int q_no) {
        PreparedStatement pstmt = null;
        int updateCount = 0;
        String sql = "UPDATE QNA SET Q_VIEWS = Q_VIEWS + 1 WHERE Q_NO = ?";
        
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, q_no);
            updateCount = pstmt.executeUpdate();
            JdbcUtil.commit();
        } catch (SQLException ex) {
            System.out.println("조회수 업데이트 에러 : " + ex);
        } finally {
            JdbcUtil.close(pstmt);
        }

        return updateCount;
    }
}
