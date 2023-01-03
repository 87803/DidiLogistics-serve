package service.impl;

import dao.DriverDao;
import dao.OrderDao;
import dao.impl.DriverDaoImpl;
import dao.impl.OrderDaoImpl;
import domain.DriverVo;
import service.DriverService;

import java.util.List;

public class DriverServiceImpl implements DriverService {
    DriverDao driverDao = new DriverDaoImpl();
    OrderDao orderDao = new OrderDaoImpl();


    //根据筛选条件获取在线司机列表
    @Override
    public List<DriverVo> getOnlineDriverByUserIDStartEnd(int userID, String start, String end) {
        //获取货主需求中的最小货物长度和重量
        double len = orderDao.findMinLengthByUserId(userID);
        double wei = orderDao.findMinWeightByUserId(userID);
        //判断用户是否筛选了起点和终点，调用不同的方法
        if (start.equals("null") && end.equals("null")) {
            return driverDao.findDriverByStateLenWei(true, len, wei);
        } else if (start.equals("null")) {
            return driverDao.findDriverByStateLenWeiEnd(true, len, wei, end);
        } else if (end.equals("null")) {
            return driverDao.findDriverByStateLenWeiStart(true, len, wei, start);
        } else {
            return driverDao.findDriverByStateLenWeiStartEnd(true, len, wei, start, end);
        }
    }
}
