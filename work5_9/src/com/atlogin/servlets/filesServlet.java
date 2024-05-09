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
public class filesServlet extends viewBaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String fileName = req.getParameter("filename01");
        System.out.println(fileName);
        String userPath = getServletContext().getRealPath("/upload/") + req.getSession().getAttribute("user");
        File file = new File(userPath + "/" + fileName);
        if (file.exists() && file.isFile()) {
            //表示要下载的是一个可执行文件
            resp.setContentType("application/x-msdownload");
            //设置相应头部，指定浏览器如何处理下载的文件（这里是作为附件进行下载)
            resp.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
            FileInputStream is = new FileInputStream(file);
            ServletOutputStream os = resp.getOutputStream();
            byte[] temp = new byte[1024];
            int len = 0;
            while ((len = is.read(temp)) != -1) {
                os.write(temp, 0, len);
            }
            os.close();
            is.close();
        } else {
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("<h2>要下载的文件不存在</h2>");
            resp.getWriter().close();
        }
    }
}