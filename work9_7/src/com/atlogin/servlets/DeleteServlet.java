package com.atlogin.servlets;
import com.Thymeleaf.viewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet("/delete_file")
public class DeleteServlet extends viewBaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String filename = req.getParameter("filename");
        System.out.println("delete:"+filename);
        String userpath = "D:/upload/" + req.getSession().getAttribute("user");
        try {
            File file = new File(userpath + "\\" + filename);
            if(file.delete())
            {
                System.out.println(file.getName()+"文件已被删除");
            }
            else {
                System.out.println("文件删除失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        File file = new File(userpath);
        File[] files = file.listFiles();
        req.setAttribute("allfiles",files);
        super.processTemplate("file",req,resp);
    }
}
