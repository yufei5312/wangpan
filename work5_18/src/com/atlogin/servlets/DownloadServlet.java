package com.atlogin.servlets;

import com.Thymeleaf.viewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet("/download_file")
public class DownloadServlet extends viewBaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置请求的编码
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");
        String fileName = req.getParameter("filename");
        System.out.println("download:" + fileName);
        String userPath = req.getServletContext().getRealPath("/upload/") + req.getSession().getAttribute("user");
        File file = new File(userPath + "\\" + fileName);
        System.out.println(userPath+"\\"+fileName);
        if (file.exists() && file.isFile()) {
            //表示要下载的是一个可执行文件,浏览器不会识别而是直接下载
            resp.setContentType("application/octet-stream");
            //设置相应头部，指定浏览器如何处理下载的文件（这里是作为附件进行下载)
            resp.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"),"ISO-8859-1"));
            //得到输入流
            FileInputStream is = new FileInputStream(file);
            //得到输出流
            ServletOutputStream os = resp.getOutputStream();
            byte[] temp = new byte[1024];
            int len = 0;
            while ((len = is.read(temp)) != -1) {
                os.write(temp, 0, len);
            }
            os.close();
            is.close();
            file = new File(userPath);
            File[] files = file.listFiles();
            req.setAttribute("allfiles", files);
            super.processTemplate("files", req, resp);
        } else {
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("<h2>要下载的文件不存在</h2>");
            resp.getWriter().close();
            file = new File(userPath);
            File[] files = file.listFiles();
            req.setAttribute("allfiles", files);
            super.processTemplate("files", req, resp);
        }
    }
}
