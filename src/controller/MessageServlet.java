package controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import domain.MessageVo;
import service.MessageService;
import service.impl.MessageServiceImpl;
import util.JWTUtils;
import util.ResponseUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

//用于获取消息列表
@WebServlet(name = "MessageServlet", value = "/auth/message")
public class MessageServlet extends HttpServlet {
    MessageService messageService = new MessageServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("token");
        DecodedJWT decodedJWT = JWTUtils.decodeRsa(token);
        String userID = decodedJWT.getClaim("userID").asString();
        List<MessageVo> data = messageService.getMessageByUserId(Integer.parseInt(userID));
        ResponseUtils.responseJson(200, "获取订单列表成功", data, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }
}
