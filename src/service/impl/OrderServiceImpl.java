package service.impl;

import dao.DriverDao;
import dao.OrderDao;
import dao.UserDao;
import dao.impl.DriverDaoImpl;
import dao.impl.OrderDaoImpl;
import dao.impl.UserDaoImpl;
import domain.DemandVo;
import domain.OrderDetailVo;
import domain.PostDemandVo;
import domain.User;
import service.MessageService;
import service.OrderService;
import util.MessageEnum;

import java.text.MessageFormat;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    OrderDao orderDao = new OrderDaoImpl();
    UserDao userDao = new UserDaoImpl();
    DriverDao driverDao = new DriverDaoImpl();
    MessageService messageService = new MessageServiceImpl();

    @Override
    public boolean createOrder(PostDemandVo postDemandVo) {
        return orderDao.insertNewOrder(postDemandVo);
    }

    @Override
    public List<DemandVo> getAllOrder(int userId, boolean userType) {
        return orderDao.findDemandByUserId(userId, userType);
    }

    @Override
    public List<DemandVo> getOrderByState(String state) {
        return orderDao.findDemandByState(state);
    }

    @Override
    public OrderDetailVo getOrderDetail(String orderId) {
        return orderDao.findOrderDetailByOrderId(orderId);
    }

    @Override
    public boolean updateOrderState(String orderId, int userId, String state, boolean userType) {
        OrderDetailVo orderDetailVo = orderDao.findOrderDetailByOrderId(orderId);
        String curState = orderDetailVo.getState();
        int driverId = Integer.parseInt(orderDetailVo.getDriverID() != null ? orderDetailVo.getDriverID() : "0");
        int ownerId = Integer.parseInt(orderDetailVo.getOwnerID());
        boolean opResult = false;
        String ownerMessage = "";
        String driverMessage = "";
        if (state.equals("已取消") && ((userType && driverId == userId) || (!userType && ownerId == userId))) {//取消订单，判断是否为该用户的订单
            if (curState.equals("待接单") || curState.equals("已接单")) { // 只有待接单和已接单的订单可以取消
                if (!userType) {
                    if (orderDao.updateOrderStateByUser(orderId, userId, state)) {
                        ownerMessage = MessageFormat.format(MessageEnum.CANCEL_ORDER, "您");
                        driverMessage = MessageFormat.format(MessageEnum.CANCEL_ORDER, "货主");
                        opResult = true;
                    }
                } else {
                    if (orderDao.updateOrderStateByDriver(orderId, userId, state)) {
                        driverMessage = MessageFormat.format(MessageEnum.CANCEL_ORDER, "您");
                        ownerMessage = MessageFormat.format(MessageEnum.CANCEL_ORDER, "司机");
                        opResult = true;
                    }
                }
            }
        } else if (state.equals("已接单") && curState.equals("待接单") && userType) {//接单，判断是否为司机
            if (orderDao.updateOrderStateAndDriverByUser(orderId, ownerId, userId, state)) {
                ownerMessage = MessageFormat.format(MessageEnum.TAKING_ORDER, orderDetailVo.getOwnerName());
                driverMessage = MessageEnum.TAKING_ORDER_DRIVER;
                driverId = userId;
                opResult = true;
            }
        } else if (state.equals("进行中") && curState.equals("已接单") && userType) {//开始行程
            if (orderDao.updateOrderStateByDriver(orderId, userId, state)) {
                ownerMessage = MessageEnum.START_ORDER;
                driverMessage = MessageFormat.format(MessageEnum.START_ORDER_DRIVER, orderDetailVo.getDescription(), orderDetailVo.getDesPlaceProvince() + orderDetailVo.getDesPlaceCity() + orderDetailVo.getDesPlaceDistrict());
                opResult = true;
            }
        } else if (state.equals("待支付") && curState.equals("进行中") && userType) {//司机送达货物
            if (orderDao.updateOrderStateByDriver(orderId, userId, state)) {
                ownerMessage = MessageEnum.FINISH_ORDER;
                driverMessage = MessageEnum.FINISH_ORDER_DRIVER;
                opResult = true;
            }
        } else if (state.equals("已完成") && curState.equals("待支付") && !userType) {//货主确认收货，完成支付
            if (orderDao.updateOrderStateByUser(orderId, userId, state)) {
                ownerMessage = MessageEnum.PAY_ORDER;
                driverMessage = MessageFormat.format(MessageEnum.PAY_ORDER_DRIVER, orderDetailVo.getPrice());
                opResult = true;
                driverDao.updateDriverIncome(driverId, Integer.parseInt(orderDetailVo.getPrice())); //更新司机收入
            }
        }

        if (opResult) {
            messageService.createNewMessage(ownerId, orderId, ownerMessage);
            if (driverId != 0)
                messageService.createNewMessage(driverId, orderId, driverMessage);
            return true;
        }
        return false;
    }

    @Override
    public boolean pushOrder(String orderId, int driverId) {
        OrderDetailVo orderDetailVo = orderDao.findOrderDetailByOrderId(orderId);
        User driver = userDao.findUserByID(driverId);
        if (messageService.createNewMessage(driverId, orderId, MessageFormat.format(MessageEnum.PUSH_ORDER_DRIVER,
                orderDetailVo.getOwnerName(),
                orderDetailVo.getDescription(),
                orderDetailVo.getStartPlaceProvince() + orderDetailVo.getStartPlaceCity() + orderDetailVo.getStartPlaceDistrict(),
                orderDetailVo.getDesPlaceProvince() + orderDetailVo.getDesPlaceCity() + orderDetailVo.getDesPlaceDistrict()))) {
            messageService.createNewMessage(Integer.parseInt(orderDetailVo.getOwnerID()), orderId, MessageFormat.format(MessageEnum.PUSH_ORDER, driver.getName()));
            return true;
        }

        return false;
    }

    @Override
    public List<DemandVo> getOrderByUserIDStartEnd(int userID, String start, String end) {
        User user = userDao.findUserByID(userID);

        double len = user.getCarLength();
        double wei = user.getCarWeight();
        if (start.equals("null") && end.equals("null")) {
            return orderDao.findDemandByStateLenWei("待接单", len, wei);
        } else if (start.equals("null")) {
            return orderDao.findDemandByStateLenWeiEnd("待接单", len, wei, end);
        } else if (end.equals("null")) {
            return orderDao.findDemandByStateLenWeiStart("待接单", len, wei, start);
        } else {
            return orderDao.findDemandByStateLenWeiStartEnd("待接单", len, wei, start, end);
        }
    }

    @Override
    public boolean updatePrice(String orderId, int userId, int price) {
        return orderDao.updatePriceByOrderIDAndUserID(orderId, userId, price);
    }
}
