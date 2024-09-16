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

        //获取用户信息
        HttpSession session = req.getSession();
        String userName = (String)session.getAttribute("user");

        //设置请求的编码格式
        req.setCharacterEncoding("utf-8");

        //获取表单中的文本域中的内容
        String filename = req.getParameter("filename");
        //检查文件名是否有效
        if (!(filename == null) && !(filename.matches("^[\\p{L}0-9_.\\s]*$"))) {
            sendErrorResponse(resp, "文件名无效");
            return;
        }
        System.out.println(filename);

        //获取Part对象
        Part uploadfile = req.getPart("uploadfile");

        //检查单个文件上传大小
        long maxFileSize = 10 * 1024 * 1024; // 10 MB
        if (uploadfile.getSize() > maxFileSize) {
            sendErrorResponse(resp, "文件大小不能超过 10MB");
            return;
        }

        //上传文件的名字
        if (filename.isEmpty()) {
            filename = uploadfile.getSubmittedFileName();
        }
        //将文件上传到指定位置
        String basePath = "D:\\upload";
        //uploadfile.write("C:/Users/24930/Desktop/网盘/wangpan/wangpan/work5_4/out/artifacts/work_war_exploded/" + submittedFileName);
        System.out.println(userName);
        uploadfile.write(basePath + "/" + userName + "/" + filename);
        File userDir = new File(basePath , userName);
        File[] files = userDir.listFiles();
        req.setAttribute("allfiles",files);
        super.processTemplate("file",req,resp);
    }
    private void sendErrorResponse(HttpServletResponse resp, String message) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write("<h2>" + message + "</h2>");
        resp.getWriter().close();
    }
}