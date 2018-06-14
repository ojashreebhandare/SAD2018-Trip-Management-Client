/**package main.java.jdbc;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.*;
import java.util.Properties;

public class ResourceManager implements Serializable {

    public Connection con;
    public Statement stmt;
    private static ConnectionProvider dbConnectorObj;

    private ConnectionProvider()
    {
        Properties prop=null;
        prop= DBPropertyLoader.getProperties();
        try
        {
            Class.forName(prop.getProperty("driverClass"));
            con=DriverManager.getConnection(prop.getProperty("url"),prop.getProperty("user"),prop.getProperty("password"));

        }catch(Exception e)
        {
            System.out.println(e);

        }
    }
        /** The Constant serialVersionUID.
        private static final long serialVersionUID = 5266060111955362695L;

        @Resource(name="jdbc/postgres")
        private static final DataSource dataSource;

        static {
            try {
                Context initContext = new InitialContext();
                Context context = (Context) initContext.lookup("java:comp/env");
                dataSource = (DataSource) context.lookup("jdbc/postgres");
            } catch (NamingException ex) {
                throw new ExceptionInInitializerError("dataSource not initialized");
            }
        }

        /**
         * Gets the datasource
         * @return

        public static DataSource getDataSource() {
            return dataSource;
        }

        /**
         * Gets the Domain Specific Language (DSL) context.
         *
         * @param connection
         *            the database connection to be used to create the DSL context
         * @return the DSL context

        public static synchronized DSLContext getDSLContext(Connection connection) {
            Configuration configuration = new DefaultConfiguration().set(connection).set(SQLDialect.POSTGRES);
            DSLContext dslc = DSL.using(configuration);
            return dslc;
        }

        /**
         * Gets the configuration.
         *
         * @return the configuration
         * @throws SQLException

        public static synchronized Configuration getConfiguration() throws SQLException {
            Configuration configuration = new DefaultConfiguration().set(getConnection()).set(SQLDialect.POSTGRES);
            return configuration;
        }

        /**
         * Gets the database connection.
         *
         * @return the connection
         * @throws SQLException
         *             if the connection to the database fails (due to wrong url or
         *             credentials)

        public static synchronized Connection getConnection() throws SQLException {
            return dataSource.getConnection();
        }

        /**
         * Close a database connection and ignore SQLException if it occurs.
         *
         * @param connection
         *            The connection to close.

        public static void close(Connection connection) {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }

        /**
         * Close a prepared statement and ignore SQLException if it occurs.
         *
         * @param stmt
         *            The statement to close.

        public static void close(PreparedStatement stmt) {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }

        /**
         * Close a result set and ignore SQLException if it occurs.
         *
         * @param rs
         *            The result set to close.

        public static void close(ResultSet rs) {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }

        }

    }

*/