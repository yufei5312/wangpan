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
public class DownloadServlet01 extends viewBaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filePath = req.getParameter("filePath");
        if (filePath == null || filePath.contains("..") || filePath.contains("/") || filePath.contains("\\"))//检查路径是否无效
        {
            resp.setContentType("text/html;charset=UTF=8");
            resp.getWriter().write("<h2>文件路径无效</h2>");
            resp.getWriter().close();
            return;
        }
        String fileName = req.getParameter("filename");
        System.out.println(fileName);
        String userPath = getServletContext().getRealPath("/upload/") + req.getSession().getAttribute("user");
        File file = new File(userPath + "\\" + fileName);
        System.out.println(userPath + "\\" + fileName);
        if (file.exists() && file.isFile())//文件可执行
        {
            resp.setContentType("application/x-msdownload");
            //设置头部
            resp.setHeader("Content-Disposition","attachment;filename="+file.getName());
            //得到输入流
            FileInputStream is = new FileInputStream(file);
            //得到输出流
            ServletOutputStream os = resp.getOutputStream();
            byte[] temp = new byte[1024];
            int len = 0;
            while ((len = is.read(temp))!= -1)
            {
                os.write(temp, 0, len);
            }
            os.close();
            is.close();
        }
        else
        {
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("<h2>要下载的文件不存在</h2>");
            resp.getWriter().close();
        }
    }
}
