package service;

import domain.DriverVo;

import java.util.List;

public interface DriverService {
    //获取司机信息
    List<DriverVo> getDriverByState(boolean state);
}
