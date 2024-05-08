package com.atlogin.servlets;

import com.Thymeleaf.viewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LoginServlet01 extends viewBaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("userName");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        System.out.println(name);
        System.out.println(password);
        System.out.println(email);

        Path path = Paths.get("D:\\Users\\LEGION\\IDEA\\work\\out\\artifacts\\work_war_exploded\\upload\\"+name);
        Path pathCreate = Files.createDirectories(path);
        resp.sendRedirect(req.getContextPath()+"/up_downLoad.html");
    }
}