package dao;

import domain.DriverVo;
import domain.User;

import java.util.List;

public interface DriverDao {
    //修改司机信息
    boolean updateDriverInfo(User user);

    //获取司机信息
    List<DriverVo> findDriverByState(boolean state);

    //根据不同条件获取司机列表信息
    List<DriverVo> findDriverByStateLenWei(boolean state, double len, double wei);

    //根据不同条件获取司机列表信息
    List<DriverVo> findDriverByStateLenWeiStart(boolean state, double len, double wei, String start);

    //根据不同条件获取司机列表信息
    List<DriverVo> findDriverByStateLenWeiEnd(boolean state, double len, double wei, String end);

    //根据不同条件获取司机列表信息
    List<DriverVo> findDriverByStateLenWeiStartEnd(boolean state, double len, double wei, String start, String end);

    //更新司机收入
    boolean updateDriverIncome(int driverId, int income);
}
