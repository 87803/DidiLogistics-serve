package dao.impl;

import dao.DriverDao;
import domain.DriverVo;
import domain.User;
import util.JdbcUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriverDaoImpl implements DriverDao {
    @Override
    public boolean updateDriverInfo(User user) {
        String sql = "update driver set cur_city = ?, des_city = ?, car_length=?,car_weight=?,state=? where user_id = ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, user.getCurCity());
            ps.setString(2, user.getDesCity());
            ps.setDouble(3, user.getCarLength());
            ps.setDouble(4, user.getCarWeight());
            ps.setBoolean(5, user.isState());
            ps.setInt(6, user.getUserID());
            int result = ps.executeUpdate();
            if (result == 1) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;

    }

    @Override
    public List<DriverVo> findDriverByState(boolean state) {
        String sql = "select * from user join driver d on user.user_id = d.user_id where state = ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setBoolean(1, state);
            ResultSet resultSet = ps.executeQuery();
            List<DriverVo> driverVoList = new ArrayList<>();
            while (resultSet.next()) {
                DriverVo driverVo = new DriverVo();
                driverVo.setUserID(resultSet.getInt("user_id"));
                driverVo.setName(resultSet.getString("name"));
                driverVo.setPhone(resultSet.getString("phone"));
                driverVo.setCurCity(resultSet.getString("cur_city"));
                driverVo.setDesCity(resultSet.getString("des_city"));
                driverVo.setCarLength(resultSet.getDouble("car_length"));
                driverVo.setCarWeight(resultSet.getDouble("car_weight"));
                driverVoList.add(driverVo);
            }
            return driverVoList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}
