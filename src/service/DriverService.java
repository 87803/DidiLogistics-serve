package service;

import domain.DriverVo;

import java.util.List;

public interface DriverService {
    //根据筛选条件获取在线司机列表
    List<DriverVo> getOnlineDriverByUserIDStartEnd(int userID, String start, String end);
}
