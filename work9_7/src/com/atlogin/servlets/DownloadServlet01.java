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
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/download_file")
public class DownloadServlet01 extends viewBaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取基础路径
        Path basePath = Paths.get("D:/upload");
        String fileName = req.getParameter("filename");
        System.out.println(fileName);
        //获取用户名
        String user = (String)req.getSession().getAttribute("user");
        //路径规范化
        Path userPath = basePath.resolve(user);
        Path normalizedPath = userPath.normalize();
        // 检查是否试图访问特定目录之外的路径
        if (!normalizedPath.startsWith(userPath)) {
            sendErrorResponse(resp, "非法访问");
            return;
        }
        //用File的构造函数自动连接路径和文件名产生新路径
        File file = new File(normalizedPath.toFile(), fileName);
        System.out.println(normalizedPath + "\\" + fileName);
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
            is.close();
            os.close();
        }
        else
        {
            sendErrorResponse(resp, "要下载的文件不存在");
        }
    }
    private void sendErrorResponse(HttpServletResponse resp, String message) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write("<h2>" + message + "</h2>");
        resp.getWriter().close();
    }
}
