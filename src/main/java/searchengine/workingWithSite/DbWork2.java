package searchengine.workingWithSite;

import java.sql.*;

public class DbWork2 {
    public Connection connection;

    public DbWork2() throws SQLException {
        //String url = "jdbc:mysql://localhost:3306/site";//?autoReconnect=true";
        String url = "jdbc:mysql://localhost:3306/search_engine2";
        String name = "root";
        String pass = "3141";
        connection = DriverManager.getConnection(url, name, pass);
    }

    public void get() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM sitedb");
        StringBuilder builder = new StringBuilder();
        while (resultSet.next()) {
            String url = resultSet.getString("url");
            String name = resultSet.getString("name");
            String text = resultSet.getString("text");
            builder.append(url).append(" ")
                    .append(name).append(" ")
                    .append(text).append("\n");
        }
        System.out.println(builder.toString());
        //   statement.close();
        //  connection.close();
    }

    public void save(String columns, String values) throws SQLException, InterruptedException {
//        String url = "jdbc:mysql://localhost:3306/site";//?autoReconnect=true";
//        String name = "root";
//        String pass = "3141";
//        Connection connection1 = DriverManager.getConnection(url, name, pass);
        Statement statement1 = connection.createStatement();
        statement1.execute("INSERT INTO sitedb ("
                + columns + ") VALUES(" + values + ");");
        //   statement.close();
        //  connection.close();
    }

    public void delete(String conditions) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM sitedb " + conditions);
        //   statement.close();
        connection.close();
    }

    public void truncate() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("TRUNCATE TABLE sitedb");
    }
    public void setUtf8mb4() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("alter table seach_engine.sitedb" +
                " convert to character set utf8mb4 collate utf8mb4_unicode_ci");
    }

    public static void main(String[] args) throws SQLException {
        DbWork2 db = new DbWork2();
        db.truncate();
//        db.setUtf8mb4();
//        db.delete("");
//        db.get();
        //       db.save("url, name, text", "'url2', 'name2', 'text2'");
//        String url = "jdbc:mysql://localhost:3306/site";
//        String name = "root";
//        String password = "3141";
//        Connection connection = DriverManager.getConnection(url, name, password);
//        Statement statement = connection.createStatement();

//        String url1 = "nextUrl";
//        String name1 = "nextName";
//        String text = "nextText";
//        statement.execute("INSERT information(url, name, text) " +
//                "VALUES('" + url1 + "','" + name1 + "','" + text + "')");

//        ResultSet resultSet = statement.executeQuery("select * from information");
//        while (resultSet.next()) {
//            String str = resultSet.getString("name");
//            System.out.println(str);
//        }
//
//        statement.close();
//        connection.close();
    }
}
//alter table site.information convert to character set utf8mb4 collate utf8mb4_unicode_ci