package controller;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import domain.DemandVo;
import domain.PostDemandVo;
import service.OrderService;
import service.impl.OrderServiceImpl;
import util.JWTUtils;
import util.JsonUtils;
import util.ResponseUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DemandServlet", value = "/auth/demand")
public class DemandServlet extends HttpServlet {
    private final OrderService orderService = new OrderServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("token");
        DecodedJWT decodedJWT = JWTUtils.decodeRsa(token);
        String userID = decodedJWT.getClaim("userID").asString();
        String type = decodedJWT.getClaim("type").asString();
        List<DemandVo> data;
        if (type.equals("0")) {//货主只能取到自己的订单
            data = orderService.getAllOrder(Integer.parseInt(userID));
        } else {
            data = orderService.getOrderByState("待接单");
        }
        ResponseUtils.responseJson(200, "获取订单列表成功", data, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jsonStr = JsonUtils.getRequestPostStr(request);
        String token = request.getHeader("token");
        System.out.println("token: " + token);
        PostDemandVo postDemandVo = JSONObject.parseObject(jsonStr, PostDemandVo.class);
//        decodedJWT.getClaim("phone");
        DecodedJWT decodedJWT = JWTUtils.decodeRsa(token);
        postDemandVo.setOwnerID(Integer.parseInt(decodedJWT.getClaim("userID").asString()));
        System.out.println(postDemandVo);
        System.out.println(jsonStr);
        if (orderService.createOrder(postDemandVo))
            ResponseUtils.responseJson(200, "创建订单成功", response);
        else
            ResponseUtils.responseJson(400, "创建订单失败", response);
    }
}
