package com.atlogin.servlets;

import com.Thymeleaf.viewBaseServlet;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet02 extends viewBaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        System.out.println(email);
        System.out.println(password);

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=utf8";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, "root", "yufei5312");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(conn);


        /*Statement statement = null;
        try {
            statement = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
        String sql = "SELECT name FROM users WHERE email = ? and password = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            ps.setObject(1,email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            ps.setObject(2,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet rs = null;
        try {
            rs = ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        /*ResultSet rs = null;
        try {
            rs = statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
        //对于更新，删除，修改操作等不需要返回结果的情况，可直接使用
        //statement.executeUpdate(sql);

        //如果需要结果，处理resultset对象获取返回结果（仅针对查询语句）
        String userName = null;
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
        System.out.println(userName);

        //如果使用了resultset，则需关闭
        try {
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        if (userName != null) {
            String realPath = req.getServletContext().getRealPath("\\upload");
            HttpSession session = req.getSession();
            session.setAttribute("user",userName);
            String name = (String) session.getAttribute("user");
            String workDir = realPath + "\\" +name;
            System.out.println(workDir);
            File file = new File(workDir);
            File[] files = file.listFiles();
            req.setAttribute("allfiles",files);
            super.processTemplate("file",req,resp);
        }
        else{
            req.setAttribute("errorMsg01","登录失败，请检查邮箱及密码是否正确");//设置键值对显示相应错误信息
            super.processTemplate("register",req,resp);//这里用了thymeleaf渲染页面
        }
    }
}
