package dao;

import domain.DemandVo;
import domain.OrderDetailVo;
import domain.PostDemandVo;

import java.util.List;

public interface OrderDao {
    //插入新订单
    boolean insertNewOrder(PostDemandVo postDemandVo);

    //根据用户id查询订单
    List<DemandVo> findDemandByUserId(int userId);

    List<DemandVo> findDemandByState(String state);

    OrderDetailVo findOrderDetailByOrderId(String orderId);
}
