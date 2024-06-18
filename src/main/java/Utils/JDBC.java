package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBC {
    static String ConnectionString = "jdbc:sqlserver://localhost:1433;databaseName=QLHocVien;encrypt=true;trustServerCertificate=true;";
    static String username = "adminHocVu";
    static String password = "01365";

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement getStmt(String sql, Object... args) throws SQLException {
        Connection conn = DriverManager.getConnection(ConnectionString, username, password);
        PreparedStatement pstmt;

        pstmt = (sql.trim().startsWith("{")) ? conn.prepareCall(sql) : conn.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) pstmt.setObject(i + 1, args[i]);

        return pstmt;
    }

    public static int executeUpdate(String sql, Object... args) {
        try (PreparedStatement stmt = getStmt(sql, args)) {
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet executeQuery(String sql, Object... args) {
        try (PreparedStatement stmt = getStmt(sql, args)) {
            return stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object value(String sql, Object... args) {
        try (ResultSet rs = executeQuery(sql, args)) {
            if (rs.next()) {
                return rs.getObject(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}



