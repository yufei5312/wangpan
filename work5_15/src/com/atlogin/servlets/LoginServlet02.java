package com.atlogin.servlets;

import com.Thymeleaf.viewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class LoginServlet02 extends viewBaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String userName = null;

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

        String realPath = req.getServletContext().getRealPath("\\upload");
        HttpSession session = req.getSession();

        if(userName!=null){
            session.setAttribute("user",userName);
            String name01 = (String) session.getAttribute("user");
            String workDir = realPath + "\\" + name01;
            File file = new File(workDir);
            File[] files = file.listFiles();
            req.setAttribute("allfiles", files);
            super.processTemplate("files", req, resp);
        }else{
            req.setAttribute("errorMsg", "登录失败，请检查邮箱及密码是否正确");
            super.processTemplate("index",req,resp);
        }
    }
}
