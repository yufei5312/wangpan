package com.atlogin.servlets;

import com.connection.ConnectionTest;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet("/uploadServlet")
@MultipartConfig
public class UploadServlet extends HttpServlet {


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ConnectionTest a = new ConnectionTest();
        //设置请求的编码格式
        req.setCharacterEncoding("utf-8");
        //获取表单中的文本域中的内容
        String filename = req.getParameter("filename");
        System.out.println("文件名称  "+filename);

        //获取Part对象
        Part uploadfile = req.getPart("uploadfile");

        //上传文件的名字
        if (filename.isEmpty()) {
            filename = uploadfile.getSubmittedFileName();
       }
        //将文件上传到指定位置
        //uploadfile.write("d:/" + submittedFileName);
        String realPath = req.getServletContext().getRealPath("/upload/");
        System.out.println("username  "+a.userName);
        uploadfile.write(realPath + a.userName + "/" + filename);
    }
}

