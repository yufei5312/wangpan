package com.atlogin.servlets;

import com.Thymeleaf.viewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet("/delete")
public class deleteServlet extends viewBaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");
        String fileName = req.getParameter("filename");
        System.out.println("download:" + fileName);
        String userPath = getServletContext().getRealPath("/upload/") + req.getSession().getAttribute("user");
        try{
            File file = new File(userPath + "\\" + fileName);
            if(file.delete()){
                System.out.println(file.getName() + "文件已被删除");
            }else{
                System.out.println("文件删除失败");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        File file = new File(userPath);
        File[] files = file.listFiles();
        req.setAttribute("allfiles", files);
        super.processTemplate("files", req, resp);
    }
}