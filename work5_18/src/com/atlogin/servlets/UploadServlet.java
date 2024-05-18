package com.atlogin.servlets;

import com.Thymeleaf.viewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@WebServlet("/uploadServlet")
@MultipartConfig
public class UploadServlet extends viewBaseServlet {


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 获取当前会话
        HttpSession session = req.getSession();
        String userName = (String) session.getAttribute("user");

        //设置请求的编码格式
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");
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

        System.out.println("userName in servlet02 is  " + userName);

        String realPath = req.getServletContext().getRealPath("/upload/");
        System.out.println("userName: "+ userName);
        uploadfile.write(realPath + userName + "/" + filename);

        session = req.getSession();
        String name01 = (String) session.getAttribute("user");
        String workDir = realPath + "\\" + name01;
        File file = new File(workDir);
        File[] files = file.listFiles();
        req.setAttribute("allfiles", files);
        super.processTemplate("files", req, resp);
    }
}


