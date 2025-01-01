package com.webflick.dao;

import lombok.Getter;

@Getter
public enum DatabaseDriver {
    H2("org.h2.Driver", "H2", "h2://"),
    MYSQL("com.mysql.jdbc.Driver", "MySQL", "mysql://"),
    POSTGRESQL("org.postgresql.Driver", "PostgreSQL", "postgresql://"),
    ORACLE("oracle.jdbc.OracleDriver", "Oracle", "oracle:thin:@"),
    SQLSERVER("com.microsoft.sqlserver.jdbc.SQLServerDriver", "SQL Server", "sqlserver://"),
    SQLITE("org.sqlite.JDBC", "SQLite", "sqlite://"),
    MARIADB("org.mariadb.jdbc.Driver", "MariaDB", "mariadb://"),
    DB2("com.ibm.db2.jcc.DB2Driver", "DB2", "db2://"),;

    private final String driverClassName;
    private final String databaseName;
    private final String urLPrefix;

    DatabaseDriver(String driverClassName, String databaseName, String urlPrefix) {
        this.driverClassName = driverClassName;
        this.databaseName = databaseName;
        this.urLPrefix = urlPrefix;
    }

    public static String getUrlPrefixByDatabaseName(String databaseName) {
        for (DatabaseDriver driver : values()) {
            if (driver.getDatabaseName().equalsIgnoreCase(databaseName)) {
                return driver.getUrLPrefix();
            }
        }
        throw new IllegalArgumentException("No driver found for database: " + databaseName);
    }

    public static String getDriverClassNameByDatabaseName(String databaseName) {
        for (DatabaseDriver driver : values()) {
            if (driver.getDatabaseName().equalsIgnoreCase(databaseName)) {
                return driver.getDriverClassName();
            }
        }
        throw new IllegalArgumentException("No driver found for database: " + databaseName);
    }
}
