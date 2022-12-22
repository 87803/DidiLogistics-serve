package dao;

import domain.DemandVo;
import domain.OrderDetailVo;
import domain.PostDemandVo;

import java.util.List;

public interface OrderDao {
    //插入新订单
    boolean insertNewOrder(PostDemandVo postDemandVo);

    //根据用户id查询订单
    List<DemandVo> findDemandByUserId(int userId, boolean userType);

    List<DemandVo> findDemandByState(String state);

    OrderDetailVo findOrderDetailByOrderId(String orderId);

    String findOrderStateByOrderId(String orderId);

    boolean updateOrderStateByUser(String orderId, int userId, String state);

    boolean updateOrderStateAndDriverByUser(String orderId, int ownerId, int driverId, String state);

    boolean updateOrderStateByDriver(String orderId, int userId, String state);

    //查找某用户所有待接单订单中的最小长度
    double findMinLengthByUserId(int userId);

    double findMinWeightByUserId(int userId);

    List<DemandVo> findDemandByStateLenWei(String state, double len, double wei);

    List<DemandVo> findDemandByStateLenWeiStart(String state, double len, double wei, String start);

    List<DemandVo> findDemandByStateLenWeiEnd(String state, double len, double wei, String end);

    List<DemandVo> findDemandByStateLenWeiStartEnd(String state, double len, double wei, String start, String end);

    boolean updatePriceByOrderIDAndUserID(String orderId, int userId, int price);
}
