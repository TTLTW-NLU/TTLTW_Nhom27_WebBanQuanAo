package vn.edu.hcmuaf.fit.webbanquanao.database;

public class Properties {
    public static final String HOST = System.getenv("DB_HOST");
    public static final String PORT = System.getenv("DB_PORT");
    public static final String DBNAME = System.getenv("DB_NAME");
    public static final String USERNAME = System.getenv("DB_USER");
    public static final String PASSWORD = System.getenv("DB_PASSWORD");
}
