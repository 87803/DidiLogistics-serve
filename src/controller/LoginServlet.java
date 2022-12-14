package controller;

import com.alibaba.fastjson.JSONObject;
import domain.User;
import service.UserService;
import service.impl.UserServiceImpl;
import util.JWTUtils;
import util.JsonUtils;
import util.ResponseUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
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
        User user = userService.login(phone, password);
        if (user != null) {
            // 生成token
            Map<String, String> map = new HashMap<>();
            map.put("phone", phone);
            map.put("userID", user.getUserID() + "");
            map.put("type", user.isType() ? "1" : "0");
            String token = JWTUtils.getTokenRsa(map);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", token);
            jsonObject.put("user", user);
            // 返回token和用户信息
            ResponseUtils.responseJson(200, "登录成功", jsonObject, response);
        } else {   //登录失败
            ResponseUtils.responseJson(400, "账号或密码错误，请重试", response);
            System.out.println("登录失败");
        }

    }
}
