package controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import domain.DriverVo;
import service.DriverService;
import service.impl.DriverServiceImpl;
import util.JWTUtils;
import util.ResponseUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

//用于货主获取司机列表
@WebServlet(name = "DriverServlet", value = "/auth/driver")
public class DriverServlet extends HttpServlet {
    DriverService driverService = new DriverServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("token");
        DecodedJWT decodedJWT = JWTUtils.decodeRsa(token);
        //得到用户筛选的起点和终点条件
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        int userID = Integer.parseInt(decodedJWT.getClaim("userID").asString());
        List<DriverVo> data = driverService.getOnlineDriverByUserIDStartEnd(userID, start, end);
        ResponseUtils.responseJson(200, "获取司机列表成功", data, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        doGet(request, response);
    }
}
