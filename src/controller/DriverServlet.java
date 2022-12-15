package controller;

import domain.DriverVo;
import service.DriverService;
import service.impl.DriverServiceImpl;
import util.ResponseUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(name = "DriverServlet", value = "/auth/driver")
public class DriverServlet extends HttpServlet {
    DriverService driverService = new DriverServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
//        String token = request.getHeader("token");
//        DecodedJWT decodedJWT = JWTUtils.decodeRsa(token);
//        String userID = decodedJWT.getClaim("userID").asString();
        List<DriverVo> data = driverService.getDriverByState(true);
        ResponseUtils.responseJson(200, "获取司机列表成功", data, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
