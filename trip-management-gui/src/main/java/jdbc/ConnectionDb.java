package jdbc;


import java.sql.*;
public class ConnectionDb
{
    public Connection con;
    public Statement stmt;
    private static ConnectionDb dbConnectorObj;
    public static final int path=1;


    private ConnectionDb()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/makemytrip","root","123456");
        }catch(Exception e)
        {
            System.out.println(e);

        }
    }

    public static ConnectionDb getInstance() {
        if (dbConnectorObj == null)
            dbConnectorObj = new ConnectionDb();


        return dbConnectorObj;
    }

    public int insert(String insertQuery) throws SQLException {
        stmt= con.createStatement();
        int resultCount = stmt.executeUpdate(insertQuery);
        return resultCount;

    }
    public int delete(String insertQuery) throws SQLException {
        stmt= con.createStatement();
        int resultCount = stmt.executeUpdate(insertQuery);
        return resultCount;

    }

    public ResultSet selectQuery(String insertQuery) throws SQLException {
        stmt= con.createStatement();
        ResultSet rset = stmt.executeQuery(insertQuery);
        return rset;

    }

    public int update(String insertQuery) throws SQLException {
        stmt= con.createStatement();
        int resultCount = stmt.executeUpdate(insertQuery);
        return resultCount;

    }

}
