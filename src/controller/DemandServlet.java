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

//用于获取需求/订单列表，发布需求
@WebServlet(name = "DemandServlet", value = "/auth/demand")
public class DemandServlet extends HttpServlet {
    private final OrderService orderService = new OrderServiceImpl();

    //获取需求列表
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("token");
        DecodedJWT decodedJWT = JWTUtils.decodeRsa(token);
        String userID = decodedJWT.getClaim("userID").asString();
        String type = decodedJWT.getClaim("type").asString();
        List<DemandVo> data;
        if (type.equals("0")) {//货主只能取到自己的订单
            data = orderService.getAllOrder(Integer.parseInt(userID), false);
        } else {    //司机分两种情况，一种是自己的订单，一种是货主发布的需求
            String order = request.getParameter("order");   //这是获取司机自己的订单
            if (order != null && order.equals("true"))
                data = orderService.getAllOrder(Integer.parseInt(userID), true);
            else    //这是获取所有需求
            {
                String start = request.getParameter("start");
                String end = request.getParameter("end");
                data = orderService.getOrderByUserIDStartEnd(Integer.parseInt(userID), start, end);
            }
        }
        ResponseUtils.responseJson(200, "获取订单列表成功", data, response);
    }

    //发布需求
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jsonStr = JsonUtils.getRequestPostStr(request);
        String token = request.getHeader("token");
        PostDemandVo postDemandVo = JSONObject.parseObject(jsonStr, PostDemandVo.class);
        DecodedJWT decodedJWT = JWTUtils.decodeRsa(token);
        postDemandVo.setOwnerID(Integer.parseInt(decodedJWT.getClaim("userID").asString()));
        if (orderService.createOrder(postDemandVo))
            ResponseUtils.responseJson(200, "创建订单成功", response);
        else
            ResponseUtils.responseJson(400, "创建订单失败", response);
    }
}
