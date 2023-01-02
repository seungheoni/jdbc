package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JDBC - DriverManager 사용
 */
@Slf4j
public class MemberRepository {

    public Member save(Member member) throws SQLException {
        String sql = "INSERT INTO member(member_id,money) VALUES (?,?)";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2,member.getMoney());
            pstmt.executeUpdate();
        } catch (SQLException e) {
           log.error("db error",e);
        } finally {
            close(conn, pstmt,null);
        }

        return member;
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {

        if(rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.error("rs close erorr",e);
            }
        }

        if(pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                log.error("state close erorr",e);
            }
        }

        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                log.error("conn close error",e);
            }
        }

    }

    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }

}
