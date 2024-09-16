package com.atlogin.servlets;

import com.Thymeleaf.viewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

@WebServlet("/register")
public class LoginServlet01 extends viewBaseServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("userName");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        System.out.println(name);
        System.out.println(password);
        System.out.println(email);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=utf8&userSSL=false&serverTimezone=GMT%2B8";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, "root", "yufei5312");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(conn);

        Statement statement = null;
        try {
            statement = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String sql01 = String.format("SELECT name FROM users WHERE email = '%s' and password = '%s'", email, password);
        String sql02 = String.format("INSERT INTO users VALUES('%s','%s','%s')", name, email,password );

        ResultSet rs = null;
        try {
            rs = statement.executeQuery(sql01);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String username = null;
        try {
            if(rs.next())
            {
                username = (String) rs.getObject(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(username!=null)
        {
            req.setAttribute("errorMsg02","您已注册过账号");
            super.processTemplate("register",req,resp);
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
        }
        try {
            statement.executeUpdate(sql02);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //String realPath = req.getServletContext().getRealPath("\\upload");
        String basePath = "D:\\upload";
        Path path = Paths.get(basePath+"\\"+name);
        Files.createDirectory(path);
        String workDir = basePath + "\\" +name;
        HttpSession session = req.getSession();//这里先设置session后面才能调用键值对
        session.setAttribute("user",name);
        System.out.println("nameUse:"+name);
        System.out.println(workDir);
        File file = new File(workDir);
        File[] files = file.listFiles();
        req.setAttribute("allfiles",files);
        super.processTemplate("file",req,resp);
    }
}