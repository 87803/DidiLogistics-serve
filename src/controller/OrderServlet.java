package controller;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import domain.OrderDetailVo;
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

//用于获取订单详情，对订单进行操作
@WebServlet(name = "OrderServlet", value = "/auth/order")
public class OrderServlet extends HttpServlet {
    private final OrderService orderService = new OrderServiceImpl();

    //获取订单详情
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            String orderID = request.getParameter("orderID");
            String token = request.getHeader("token");
            DecodedJWT decodedJWT = JWTUtils.decodeRsa(token);
            boolean userType = decodedJWT.getClaim("type").asString().equals("1");
            OrderDetailVo orderDetailVo = orderService.getOrderDetail(orderID);
            //如果是司机且“待接单”，则隐去详细地址
            if (userType && orderDetailVo.getState().equals("待接单")) {
                orderDetailVo.setDesPlaceDetail("******");
                orderDetailVo.setStartPlaceDetail("******");
            }
            ResponseUtils.responseJson(200, "获取订单成功", orderDetailVo, response);
        } catch (Exception e) {
            ResponseUtils.responseJson(400, "获取订单详情失败", response);
        }
    }

    //对订单进行操作
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getHeader("token");
        DecodedJWT decodedJWT = JWTUtils.decodeRsa(token);
        String userID = decodedJWT.getClaim("userID").asString();
        boolean userType = decodedJWT.getClaim("type").asString().equals("1");

        JSONObject postData = JsonUtils.getRequestPostJson(request);
        String orderID = postData.getString("orderID");
        int type = postData.getInteger("type");
        //根据不同的操作类型返回不同的结果
        switch (type) {
            case 0: //支付
                if (orderService.updateOrderState(orderID, Integer.parseInt(userID), "已完成", userType)) {
                    ResponseUtils.responseJson(200, "支付成功", response);
                } else {
                    ResponseUtils.responseJson(400, "支付失败", response);
                }
                break;
            case 1: //取消
                if (orderService.updateOrderState(orderID, Integer.parseInt(userID), "已取消", userType))
                    ResponseUtils.responseJson(200, "取消订单成功", response);
                else
                    ResponseUtils.responseJson(400, "取消订单失败", response);
                break;
            case 2://接单
                if (orderService.updateOrderState(orderID, Integer.parseInt(userID), "已接单", userType))
                    ResponseUtils.responseJson(200, "接单成功", response);
                else
                    ResponseUtils.responseJson(400, "接单失败", response);
                break;
            case 3://开始行程
                if (orderService.updateOrderState(orderID, Integer.parseInt(userID), "进行中", userType))
                    ResponseUtils.responseJson(200, "开始行程成功", response);
                else
                    ResponseUtils.responseJson(400, "开始行程失败", response);
                break;
            case 4://结束行程
                if (orderService.updateOrderState(orderID, Integer.parseInt(userID), "待支付", userType))
                    ResponseUtils.responseJson(200, "结束行程成功", response);
                else
                    ResponseUtils.responseJson(400, "结束行程失败", response);
                break;
            case 5://向司机推送订单
                if (orderService.pushOrder(orderID, postData.getInteger("driverID")))
                    ResponseUtils.responseJson(200, "推送订单成功", response);
                else
                    ResponseUtils.responseJson(400, "推送订单失败", response);
                break;
            case 6://修改价格
                if (orderService.updatePrice(orderID, Integer.parseInt(userID), postData.getInteger("price")))
                    ResponseUtils.responseJson(200, "修改价格成功", response);
                else
                    ResponseUtils.responseJson(400, "修改价格失败", response);
            default:
                ResponseUtils.responseJson(500, "参数错误，非法的请求", response);
        }
    }
}
