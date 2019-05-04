package com.domiyi.weixin.servlet;

import com.domiyi.weixin.pojo.TextMessage;
import com.domiyi.weixin.util.CheckUtil;
import com.domiyi.weixin.util.MessageUtil;
import org.dom4j.DocumentException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

/**
 * Created by  OSCAR on 2018/12/19
 * 验证微信token
 */
@WebServlet("/vv")
public class WeixinServlet  extends HttpServlet{

    @Override
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

    /**
     * 1value里面真的有值么？--因为这是发送消息，所以发送人，接收人，时间，内容是固定的，所以可以获得value
     * 2
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        //把微信消息返回给客户端
        PrintWriter out = resp.getWriter();
        try {
            Map<String,String> map = MessageUtil.xmlToMap(req);
            String fromUserName = map.get("FromUserName");
            String toUserName = map.get("ToUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");

            String message = null;
            if (MessageUtil.MESSAGE_TEXT.equals(msgType)){
                //按照关键字进行回复
                if ("1".equals(content)){
                    message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.firstMenu());
                }else if ("2".equals(content)){
                    message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.secondMenu());
                }else  if ("?".equals(content) || "?".equals(content)){
                    message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.menuText());
                }

                //以下是做和微信编辑模式下一样的样式
            }else if (MessageUtil.MESSAGE_EVENT.equals(msgType)){
                String eventType = map.get("Event");//可以获取到事件类型
                if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){//关注以后回复主菜单
                    message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.menuText());
                }
            }

             System.out.println();

             out.print(message);//把消息发送到客户端

        } catch (DocumentException e) {
            e.printStackTrace();
        }finally {
            out.close();
        }

    }


}
