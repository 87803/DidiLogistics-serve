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

    @Override
    public List<DriverVo> findDriverByStateLenWei(boolean state, double len, double wei) {
        String sql = "select * from user join driver d on user.user_id = d.user_id where state = ? and car_length >= ? and car_weight >= ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setBoolean(1, state);
            ps.setDouble(2, len);
            ps.setDouble(3, wei);
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

    @Override
    public List<DriverVo> findDriverByStateLenWeiStart(boolean state, double len, double wei, String start) {
        String sql = "select * from user join driver d on user.user_id = d.user_id where state = ? and car_length >= ? and car_weight >= ? and cur_city like ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setBoolean(1, state);
            ps.setDouble(2, len);
            ps.setDouble(3, wei);
            ps.setString(4, "%" + start + "%");
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

    @Override
    public List<DriverVo> findDriverByStateLenWeiEnd(boolean state, double len, double wei, String end) {
        String sql = "select * from user join driver d on user.user_id = d.user_id where state = ? and car_length >= ? and car_weight >= ? and des_city like ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setBoolean(1, state);
            ps.setDouble(2, len);
            ps.setDouble(3, wei);
            ps.setString(4, "%" + end + "%");
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

    @Override
    public List<DriverVo> findDriverByStateLenWeiStartEnd(boolean state, double len, double wei, String start, String end) {
        String sql = "select * from user join driver d on user.user_id = d.user_id where state = ? and car_length >= ? and car_weight >= ? and cur_city like ? and des_city like ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setBoolean(1, state);
            ps.setDouble(2, len);
            ps.setDouble(3, wei);
            ps.setString(4, "%" + start + "%");
            ps.setString(5, "%" + end + "%");
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

    @Override
    public boolean updateDriverIncome(int driverId, int income) {
        String sql = "update driver set income = (?+income) where user_id = ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setInt(1, income);
            ps.setInt(2, driverId);
            int i = ps.executeUpdate();
            if (i > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
