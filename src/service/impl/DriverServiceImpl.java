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


    @Override
    public List<DriverVo> getOnlineDriverByUserIDStartEnd(int userID, String start, String end) {
        double len = orderDao.findMinLengthByUserId(userID);
        double wei = orderDao.findMinWeightByUserId(userID);
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
