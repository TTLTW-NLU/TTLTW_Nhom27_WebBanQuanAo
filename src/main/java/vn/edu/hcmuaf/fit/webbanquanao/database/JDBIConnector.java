package vn.edu.hcmuaf.fit.webbanquanao.database;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.jdbi.v3.core.Jdbi;

import java.sql.SQLException;

public class JDBIConnector {
    private static Jdbi jdbi;

    public static Jdbi get() {
        if (jdbi == null) {
            try {
                connect();
            } catch (SQLException e) {
                throw new RuntimeException("Không thể kết nối DB", e);
            }
        }
        return jdbi;
    }

    public static void connect() throws SQLException {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setServerName(Properties.HOST);
        ds.setPort(Integer.parseInt(Properties.PORT));
        ds.setDatabaseName(Properties.DBNAME);
        ds.setUser(Properties.USERNAME);
        ds.setPassword(Properties.PASSWORD);

        // Cấu hình SSL (vì Aiven yêu cầu sslMode=REQUIRED)
        ds.setUseSSL(true);
        ds.setRequireSSL(true);
        ds.setVerifyServerCertificate(false); // Có thể bật lại nếu bạn cài chứng chỉ CA
        try {
            ds.setAutoReconnect(true);
            ds.setUseCompression(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        jdbi = Jdbi.create(ds);
    }



}