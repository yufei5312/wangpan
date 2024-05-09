package com.atlogin.servlets;


import com.Thymeleaf.viewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/down_load")
public class downLoadServlet02 extends viewBaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String fileName = req.getParameter("fileName");
            String userPath = getServletContext().getRealPath("/upload/") +
                    req.getSession().getAttribute("user");
    }

}