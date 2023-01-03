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

    //根据订单状态获取订单列表数据
    List<DemandVo> findDemandByState(String state);

    //根据订单ID获取订单详情
    OrderDetailVo findOrderDetailByOrderId(String orderId);

    //根据订单ID获取订单状态
    String findOrderStateByOrderId(String orderId);

    //货主更新订单状态
    boolean updateOrderStateByUser(String orderId, int userId, String state);

    //更新订单状态及该订单的司机ID
    boolean updateOrderStateAndDriverByUser(String orderId, int ownerId, int driverId, String state);

    //司机更新订单状态
    boolean updateOrderStateByDriver(String orderId, int userId, String state);

    //查找某用户所有待接单订单中的最小长度
    double findMinLengthByUserId(int userId);

    //查找某用户所有待接单订单中的最小重量
    double findMinWeightByUserId(int userId);

    //根据不同条件获取订单列表信息
    List<DemandVo> findDemandByStateLenWei(String state, double len, double wei);

    //根据不同条件获取订单列表信息
    List<DemandVo> findDemandByStateLenWeiStart(String state, double len, double wei, String start);

    //根据不同条件获取订单列表信息
    List<DemandVo> findDemandByStateLenWeiEnd(String state, double len, double wei, String end);

    //根据不同条件获取订单列表信息
    List<DemandVo> findDemandByStateLenWeiStartEnd(String state, double len, double wei, String start, String end);

    //货主更新订单价格
    boolean updatePriceByOrderIDAndUserID(String orderId, int userId, int price);
}
