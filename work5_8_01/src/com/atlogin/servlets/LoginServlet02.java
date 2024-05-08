package com.atlogin.servlets;

import com.Thymeleaf.viewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet02 extends viewBaseServlet {
    public String email;
    public String password;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        email = req.getParameter("email");
        password = req.getParameter("password");

        System.out.println(email);
        System.out.println(password);
        super.processTemplate("index", req, resp);
    }

}
