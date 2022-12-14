package service.impl;

import dao.OrderDao;
import dao.impl.OrderDaoImpl;
import domain.DemandVo;
import domain.OrderDetailVo;
import domain.PostDemandVo;
import service.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    OrderDao orderDao = new OrderDaoImpl();

    @Override
    public boolean createOrder(PostDemandVo postDemandVo) {
        return orderDao.insertNewOrder(postDemandVo);
    }

    @Override
    public List<DemandVo> getAllOrder(int userId) {
        return orderDao.findDemandByUserId(userId);
    }

    @Override
    public List<DemandVo> getOrderByState(String state) {
        return orderDao.findDemandByState(state);
    }

    @Override
    public OrderDetailVo getOrderDetail(String orderId) {
        return orderDao.findOrderDetailByOrderId(orderId);
    }
}
