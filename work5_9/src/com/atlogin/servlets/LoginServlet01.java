package com.atlogin.servlets;

import com.Thymeleaf.viewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginServlet01 extends viewBaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

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

        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("userName");
        String  password = req.getParameter("password");
        String email = req.getParameter("email");

        System.out.println(name);
        System.out.println(password);
        System.out.println(email);


        String sql = String.format("INSERT INTO users(name, passwd, email) VALUES('%s', '%s' ,'%s')",name,password,email);
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String realPath = req.getServletContext().getRealPath("\\upload");
        Path path = Paths.get(realPath+"\\"+name);
        Path pathCreate = Files.createDirectories(path);

        System.out.println("nameUse:"+name);

        HttpSession session = req.getSession();
        session.setAttribute("user",name);
        resp.sendRedirect(req.getContextPath()+"/up_downLoad.html");

    }
}