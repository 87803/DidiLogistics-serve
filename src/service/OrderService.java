package service;

import domain.DemandVo;
import domain.OrderDetailVo;
import domain.PostDemandVo;

import java.util.List;

public interface OrderService {
    //创建订单，用户发布需求
    boolean createOrder(PostDemandVo postDemandVo);

    //根据用户id、用户类型获取订单列表
    List<DemandVo> getAllOrder(int userId, boolean userType);

    //根据订单状态获取订单列表
    List<DemandVo> getOrderByState(String state);

    //根据订单id获取订单详情
    OrderDetailVo getOrderDetail(String orderId);

    //更新订单状态，用户订单操作
    boolean updateOrderState(String orderId, int userId, String state, boolean userType);

    //向司机推送订单
    boolean pushOrder(String orderId, int driverId);

    //根据起终点筛选订单列表
    List<DemandVo> getOrderByUserIDStartEnd(int userID, String start, String end);

    //修改订单价格
    boolean updatePrice(String orderId, int userId, int price);
}
