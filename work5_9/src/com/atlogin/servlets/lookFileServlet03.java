package com.atlogin.servlets;

import com.Thymeleaf.viewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@WebServlet("/lookfile")
public class lookFileServlet03 extends viewBaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String realPath = req.getServletContext().getRealPath("\\upload");
        HttpSession session = req.getSession();
        String name01 = (String) session.getAttribute("user");
        String workDir = realPath + "\\" + name01;
        File file = new File(workDir);
        File[] files = file.listFiles();

        req.setAttribute("allfiles", files);
        super.processTemplate("files", req, resp);
    }
}