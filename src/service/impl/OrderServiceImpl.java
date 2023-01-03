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

    //创建订单，用户发布需求
    @Override
    public boolean createOrder(PostDemandVo postDemandVo) {
        return orderDao.insertNewOrder(postDemandVo);
    }

    //根据用户id查询订单
    @Override
    public List<DemandVo> getAllOrder(int userId, boolean userType) {
        return orderDao.findDemandByUserId(userId, userType);
    }

    //根据订单状态查询订单
    @Override
    public List<DemandVo> getOrderByState(String state) {
        return orderDao.findDemandByState(state);
    }

    //根据订单id查询订单详情
    @Override
    public OrderDetailVo getOrderDetail(String orderId) {
        return orderDao.findOrderDetailByOrderId(orderId);
    }

    //更新订单状态，用户订单操作
    @Override
    public boolean updateOrderState(String orderId, int userId, String state, boolean userType) {
        OrderDetailVo orderDetailVo = orderDao.findOrderDetailByOrderId(orderId);
        String curState = orderDetailVo.getState();
        int driverId = Integer.parseInt(orderDetailVo.getDriverID() != null ? orderDetailVo.getDriverID() : "0");
        int ownerId = Integer.parseInt(orderDetailVo.getOwnerID());
        boolean opResult = false;
        String ownerMessage = "";   //货主端要显示的消息
        String driverMessage = "";  //司机端要显示的消息
        //订单当前状态，要执行的操作，操作用户三者均需合法，才能执行操作
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
            if (driverId != 0)  //如果司机id不为0，说明有司机接单，需要给司机发送消息
                messageService.createNewMessage(driverId, orderId, driverMessage);
            return true;
        }
        return false;
    }

    //向司机推送订单
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

    //根据起终点筛选订单列表
    @Override
    public List<DemandVo> getOrderByUserIDStartEnd(int userID, String start, String end) {
        User user = userDao.findUserByID(userID);
        //获取司机的货车长度和载重
        double len = user.getCarLength();
        double wei = user.getCarWeight();
        //判断用户是否筛选了起点和终点，调用不同的方法
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

    //修改订单价格
    @Override
    public boolean updatePrice(String orderId, int userId, int price) {
        return orderDao.updatePriceByOrderIDAndUserID(orderId, userId, price);
    }
}
