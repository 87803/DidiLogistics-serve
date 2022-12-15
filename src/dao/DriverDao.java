package dao;

import domain.DriverVo;
import domain.User;

import java.util.List;

public interface DriverDao {
    //修改司机信息
    boolean updateDriverInfo(User user);

    //获取司机信息
    List<DriverVo> findDriverByState(boolean state);
}
