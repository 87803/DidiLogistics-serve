package service.impl;

import dao.DriverDao;
import dao.impl.DriverDaoImpl;
import domain.DriverVo;
import service.DriverService;

import java.util.List;

public class DriverServiceImpl implements DriverService {
    DriverDao driverDao = new DriverDaoImpl();

    @Override
    public List<DriverVo> getDriverByState(boolean state) {
        return driverDao.findDriverByState(state);
    }
}
