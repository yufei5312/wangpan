package com.atlogin.servlets;

import com.Thymeleaf.viewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;

@WebServlet("/uploadServlet")
@MultipartConfig
public class UploadServlet extends viewBaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        String userName = (String)session.getAttribute("user");

        //设置请求的编码格式
        req.setCharacterEncoding("utf-8");

        //获取表单中的文本域中的内容
        String filename = req.getParameter("filename");
        System.out.println(filename);

        //获取Part对象
        Part uploadfile = req.getPart("uploadfile");

        //上传文件的名字
        String submittedFileName = null;
        if (filename.isEmpty()) {
            filename = uploadfile.getSubmittedFileName();
        }
        //将文件上传到指定位置
        //uploadfile.write("C:/Users/24930/Desktop/网盘/wangpan/wangpan/work5_4/out/artifacts/work_war_exploded/" + submittedFileName);
        String realPath = req.getServletContext().getRealPath("/upload/");
        System.out.println(userName);
        uploadfile.write(realPath + userName + "/" + filename);

        session = req.getSession();
        String name = (String)session.getAttribute("user");
        String workDir = realPath + "\\" + name;
        File file = new File(workDir);
        File[] files = file.listFiles();
        req.setAttribute("allfiles",files);
        super.processTemplate("file",req,resp);
    }
}