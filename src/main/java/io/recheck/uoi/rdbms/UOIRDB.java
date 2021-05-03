package io.recheck.uoi.rdbms;


import java.sql.*;

public class UOIRDB {

    private Connection connection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/uoi_backend", "Dakata", "test");
        return con;
    }

    public void insertIntoSystems(String system, String url, String type) {
        try {
            Connection con = connection();
            Statement stmt = con.createStatement();
            int res = stmt.executeUpdate("INSERT INTO systems (systemName, baseUrl, callType) VALUES ('" + system + "', '" + url + "', '" + type + "')");
            System.out.println("Updating has been done to "+ res+ "row(s).");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void getAllResults() {
        try {
            Connection con = connection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from systems");
            while (rs.next())
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
            con.close();
        } catch (
                Exception e) {
            System.out.println(e);
        }
    }
}


