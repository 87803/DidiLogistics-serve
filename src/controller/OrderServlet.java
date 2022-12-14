package controller;

import service.OrderService;
import service.impl.OrderServiceImpl;
import util.ResponseUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "OrderServlet", value = "/auth/order")
public class OrderServlet extends HttpServlet {
    private final OrderService orderService = new OrderServiceImpl();

    //获取订单详情
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            String orderID = request.getParameter("orderID");
            ResponseUtils.responseJson(200, "获取订单成功", orderService.getOrderDetail(orderID), response);
        } catch (Exception e) {
            ResponseUtils.responseJson(400, "获取订单详情失败", response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
