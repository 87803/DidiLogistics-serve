package dao;

import domain.User;

public interface DriverDao {
    //修改司机信息
    boolean updateDriverInfo(User user);
}
