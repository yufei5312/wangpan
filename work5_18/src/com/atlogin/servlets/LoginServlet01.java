package com.atlogin.servlets;

import com.Thymeleaf.viewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;


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

        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");
        String name = req.getParameter("userName");
        String  password = req.getParameter("password");
        String email = req.getParameter("email");

        System.out.println(name);
        System.out.println(password);
        System.out.println(email);

        String sql = String.format("SELECT name FROM users WHERE email = '%s' AND passwd = '%s'", email, password);
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String userName = null;
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

        HttpSession session = req.getSession();
        if(userName!=null){
            req.setAttribute("errorMsg02", "您已注册账号，请登录");
            super.processTemplate("index",req,resp);
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
        }else{
            System.out.println(1);
           sql = String.format("INSERT INTO users(name, passwd, email) VALUES('%s', '%s' ,'%s')",name,password,email);
            try {
                statement.executeUpdate(sql);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String realPathpre = req.getServletContext().getRealPath("\\work_war_exploded");
            String realPath = req.getServletContext().getRealPath("\\upload");
            if(realPath==null){
                Path path01 = Paths.get(realPathpre+"\\"+"upload");
                Path pathCreate01 = Files.createDirectories(path01);
            }
            Path path = Paths.get(realPath+"\\"+name);
            Path pathCreate = Files.createDirectories(path);

            System.out.println("nameUse:"+name);

            session.setAttribute("user",name);

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

            String name01 = (String) session.getAttribute("user");
            String workDir = realPath + "\\" + name01;
            File file = new File(workDir);
            File[] files = file.listFiles();

            req.setAttribute("allfiles", files);
            super.processTemplate("files", req, resp);
        }
    }
}