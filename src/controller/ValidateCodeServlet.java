package controller;

import com.alibaba.fastjson.JSONObject;
import service.UserService;
import service.impl.UserServiceImpl;
import util.JsonUtils;
import util.ResponseUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ValidateCodeServlet", value = "/verificationCode")
public class ValidateCodeServlet extends HttpServlet {
    UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonData = JsonUtils.getRequestPostJson(request);
        String phone = jsonData.getString("phone");
        //注册成功
        if (userService.getVerificationCode(phone)) {
            ResponseUtils.responseJson(200, "验证码发送成功，请注意查收", response);
        } else {   //注册失败
            ResponseUtils.responseJson(400, "验证码发送失败，请稍后重试", response);
            System.out.println("验证码发送失败");
        }
    }
}
