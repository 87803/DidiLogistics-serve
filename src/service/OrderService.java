package service;

import domain.DemandVo;
import domain.OrderDetailVo;
import domain.PostDemandVo;

import java.util.List;

public interface OrderService {
    boolean createOrder(PostDemandVo postDemandVo);

    List<DemandVo> getAllOrder(int userId);

    List<DemandVo> getOrderByState(String state);

    OrderDetailVo getOrderDetail(String orderId);
}
