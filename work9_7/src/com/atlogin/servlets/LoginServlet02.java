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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String userName = queryLoginInfoNew(email, password);
        if (userName != null) {
            //String realPath = req.getServletContext().getRealPath("\\upload");
            String basePath = "D:\\upload";
            HttpSession session = req.getSession();
            session.setAttribute("user", userName);
            String name = (String) session.getAttribute("user");
            String workDir = basePath + "\\" + name;
            System.out.println(workDir);
            File file = new File(workDir);
            File[] files = file.listFiles();
            req.setAttribute("allfiles", files);
            super.processTemplate("file", req, resp);
        } else {
            req.setAttribute("errorMsg01", "登录失败，请检查邮箱及密码是否正确");
            super.processTemplate("register", req, resp);
        }
    }
    public String queryLoginInfoNew(String email, String password) {
        String userName = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/demo? useUnicode=true&characterEncoding=utf8";
            Connection conn = DriverManager.getConnection(url, "root", "yufei5312");
            String safe_sql = "SELECT name FROM users WHERE email = ? and password = ?";
            PreparedStatement ps = conn.prepareStatement(safe_sql);
            ps.setObject(1, email);
            ps.setObject(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                userName = (String)rs.getObject(1);
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userName;
    }
}
