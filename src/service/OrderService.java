package service;

import domain.DemandVo;
import domain.OrderDetailVo;
import domain.PostDemandVo;

import java.util.List;

public interface OrderService {
    boolean createOrder(PostDemandVo postDemandVo);

    List<DemandVo> getAllOrder(int userId, boolean userType);

    List<DemandVo> getOrderByState(String state);

    OrderDetailVo getOrderDetail(String orderId);

    boolean updateOrderState(String orderId, int userId, String state, boolean userType);

    boolean pushOrder(String orderId, int driverId);

    List<DemandVo> getOrderByUserIDStartEnd(int userID, String start, String end);

    boolean updatePrice(String orderId, int userId, int price);
}
