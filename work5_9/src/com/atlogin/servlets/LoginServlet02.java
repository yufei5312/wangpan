package com.atlogin.servlets;

import com.Thymeleaf.viewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

public class LoginServlet02 extends viewBaseServlet {
    public String email = null;
    public String password = null;
    public String userName = null;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        email = req.getParameter("email");
        password = req.getParameter("password");

        System.out.println(email);
        System.out.println(password);

        String url = "jdbc:mysql://localhost:3306/mypan?useUnicode=true&characterEncoding=utf8";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, "root", "nineone4536251");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Statement statement = null;
        try {
            statement = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String sql = String.format("SELECT name FROM users WHERE email = '%s' AND passwd = '%s'", email, password);
        System.out.println(email);
        System.out.println(password);

        ResultSet rs = null;
        try {
            rs = statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //对于更新，删除，修改操作等不需要返回结果的情况，可直接使用
        //statement.executeUpdate(sql);

        //如果需要结果，处理resultset对象获取返回结果（仅针对查询语句）
        try {
            if (rs.next()) {
                try {
                    userName = (String) rs.getObject(1);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        //如果使用了resultset，则需关闭
        try {
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("userName in LoginServlet02 is:"+userName);

        if(userName!=null){
            HttpSession session = req.getSession();
            session.setAttribute("user",userName);
            resp.sendRedirect(req.getContextPath()+"/up_downLoad.html");
        }else{
            req.setAttribute("errorMsg", "登录失败，请检查邮箱及密码是否正确");
            super.processTemplate("index",req,resp);
        }
    }
}
