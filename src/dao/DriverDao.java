package dao;

import domain.DriverVo;
import domain.User;

import java.util.List;

public interface DriverDao {
    //修改司机信息
    boolean updateDriverInfo(User user);

    //获取司机信息
    List<DriverVo> findDriverByState(boolean state);

    List<DriverVo> findDriverByStateLenWei(boolean state, double len, double wei);

    List<DriverVo> findDriverByStateLenWeiStart(boolean state, double len, double wei, String start);

    List<DriverVo> findDriverByStateLenWeiEnd(boolean state, double len, double wei, String end);

    List<DriverVo> findDriverByStateLenWeiStartEnd(boolean state, double len, double wei, String start, String end);

    boolean updateDriverIncome(int driverId, int income);
}
