package com.domiyi.weixin.servlet;

import com.domiyi.weixin.util.CheckUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

/**
 * Created by  OSCAR on 2018/12/19
 * 验证微信token
 */
@Controller
@RequestMapping("/weixin")
public class WeixinServlet  {

    @RequestMapping("vv")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");
        System.out.println("123");
        //将response打印输出
        PrintWriter out = resp.getWriter();
        try {
            if (CheckUtil.checkSignature(signature,timestamp,nonce)){
                out.println(echostr);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


}
