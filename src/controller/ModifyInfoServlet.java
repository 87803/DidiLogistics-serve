package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
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

//用于获取用户信息，修改用户信息
@WebServlet(name = "ModifyInfoServlet", value = "/auth/modifyInfo")
public class ModifyInfoServlet extends HttpServlet {
    UserService userService = new UserServiceImpl();

    //获取用户信息
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("token");
        DecodedJWT decodedJWT = JWTUtils.decodeRsa(token);
        String userID = decodedJWT.getClaim("userID").asString();
        User user = userService.getUserInfo(Integer.parseInt(userID));
        ResponseUtils.responseJson(200, "获取用户信息成功", user, response);
    }

    //修改用户信息
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getHeader("token");
        DecodedJWT decodedJWT = JWTUtils.decodeRsa(token);
        String userID = decodedJWT.getClaim("userID").asString();

        JSONObject data = JsonUtils.getRequestPostJson(request);
        User user = JSON.toJavaObject(data, User.class);
        user.setUserID(Integer.parseInt(userID));
        //System.out.println(data);
        //System.out.println(user);
        //判断用户是否要修改密码
        if (data.getString("oldPassword") != null && !data.getString("oldPassword").equals("")) {
            if (!userService.updatePassword(Integer.parseInt(userID), data.getString("oldPassword"), data.getString("newPassword"))) {
                ResponseUtils.responseJson(400, "修改密码失败", response);
                return;
            }
        }

        if (decodedJWT.getClaim("type").asString().equals("1")) {//司机
            boolean result = userService.updateUserInfo(user) && userService.updateDriverInfo(user);
            if (result)
                ResponseUtils.responseJson(200, "修改信息成功", response);
            else
                ResponseUtils.responseJson(400, "修改信息失败", response);
        } else {//货主
            if (userService.updateUserInfo(user)) {
                ResponseUtils.responseJson(200, "修改信息成功", response);
            } else {
                ResponseUtils.responseJson(400, "修改用户信息失败", response);
            }
        }
    }
}
