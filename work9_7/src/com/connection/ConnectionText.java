package com.connection;

import org.junit.Test;

import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.Properties;

import static java.lang.Class.forName;

public class ConnectionText {

    public String userName;
    @Test
    public void testConnection() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        Class.forName("com.mysql.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/mypan?useUnicode=true&characterEncoding=utf8";

        Connection conn = DriverManager.getConnection(url,"localhost","yufei5312");
        System.out.println(conn);

        Statement statement = conn.createStatement();
        String sql = String.format("SELECT name FROM users WHERE email = '%s' and passwd = '%s'", "zhangsan@std.uestc.edu.cn","zhang123haha" );

        ResultSet rs = statement.executeQuery(sql);

        //对于更新，删除，修改操作等不需要返回结果的情况，可直接使用
        //statement.executeUpdate(sql);

        //如果需要结果，处理resultset对象获取返回结果（仅针对查询语句）
        String userName = (String) rs.getObject(1);

        //如果使用了resultset，则需关闭
        rs.close();
        statement.close();
        conn.close();
        
    }

}