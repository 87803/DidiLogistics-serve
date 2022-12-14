package controller;

import com.alibaba.fastjson.JSONObject;
import service.UserService;
import service.impl.UserServiceImpl;
import util.JsonUtils;
import util.RedisUtils;
import util.ResponseUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonData = JsonUtils.getRequestPostJson(request);
        String phone = jsonData.getString("phone");
        String password = jsonData.getString("password");
        String code = jsonData.getString("code");
        Boolean type = jsonData.getBoolean("type");
        if (RedisUtils.verify(phone, code)) {
            if (userService.register(phone, password, type))
                ResponseUtils.responseJson(200, "注册成功", response);
            else {   //注册失败
                ResponseUtils.responseJson(400, "注册失败，可能是当前用户已存在，请重试", response);
                System.out.println("注册失败");
            }
        } else {
            ResponseUtils.responseJson(400, "验证码错误", response);
            System.out.println("验证码错误");
        }
    }
}
