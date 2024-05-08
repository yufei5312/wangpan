package com.connection;

import com.atlogin.servlets.LoginServlet02;
import org.junit.Test;

import java.sql.*;

public class ConnectionTest{

    public String userName;
    @Test
    public void testConnection () throws SQLException, ClassNotFoundException {

        LoginServlet02 getM = new LoginServlet02();

        String url = "jdbc:mysql://localhost:3306/mypan?useUnicode=true&characterEncoding=utf8";

        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection conn = DriverManager.getConnection(url, "root", "nineone4536251");
        Statement statement = conn.createStatement();

        String sql = String.format("SELECT `name` FROM users WHERE email = '%s' AND passwd = '%s'",
                getM.email,
                getM.password
        );

        System.out.println("ok" + sql);

        ResultSet rs = statement.executeQuery(sql);

        //对于更新，删除，修改操作等不需要返回结果的情况，可直接使用
        //statement.executeUpdate(sql);

        //如果需要结果，处理resultset对象获取返回结果（仅针对查询语句）
        if (rs.next()) {
            userName = (String) rs.getObject(1);
        }

        //如果使用了resultset，则需关闭
        rs.close();
        statement.close();
        conn.close();
    }

}