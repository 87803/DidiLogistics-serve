package dao.impl;

import dao.UserDao;
import domain.User;
import util.JdbcUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean insertNewUser(String phone, String password, boolean type) {
        String sql = "INSERT INTO `user`(`phone`, `password`, `type`) VALUES(?,?,?)";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, phone);
            ps.setString(2, password);
            ps.setBoolean(3, type);
            int result = ps.executeUpdate();
            if (result == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;//throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public User findUserByID(int userID) {
        String sql = "select * from user left join driver on user.user_id = driver.user_id where user.user_id = ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setInt(1, userID);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setPhone(resultSet.getString("phone"));
                user.setName(resultSet.getString("name"));
                user.setNickname(resultSet.getString("nickname"));
                user.setType(resultSet.getBoolean("type"));
                user.setUserID(resultSet.getInt("user_id"));
                user.setCurCity(resultSet.getString("cur_city"));
                user.setDesCity(resultSet.getString("des_city"));
                user.setCarWeight(resultSet.getDouble("car_weight"));
                user.setCarLength(resultSet.getDouble("car_length"));
                user.setState(resultSet.getBoolean("state"));
                user.setIncome(resultSet.getInt("income"));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public User findUserByPhoneAndPassword(String phone, String password) {
        String sql = "select * from user left join driver on user.user_id = driver.user_id where phone = ? and password = ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, phone);
            ps.setString(2, password);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setPhone(resultSet.getString("phone"));
                user.setName(resultSet.getString("name"));
                user.setNickname(resultSet.getString("nickname"));
                user.setType(resultSet.getBoolean("type"));
                user.setUserID(resultSet.getInt("user_id"));
                user.setCurCity(resultSet.getString("cur_city"));
                user.setDesCity(resultSet.getString("des_city"));
                user.setCarWeight(resultSet.getDouble("car_weight"));
                user.setCarLength(resultSet.getDouble("car_length"));
                user.setState(resultSet.getBoolean("state"));
                user.setIncome(resultSet.getInt("income"));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean updatePassword(int userID, String password, String newPassword) {
        String sql = "update user set password = ? where user_id = ? and password = ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, newPassword);
            ps.setInt(2, userID);
            ps.setString(3, password);
            int result = ps.executeUpdate();
            if (result == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;//throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean updateUserInfo(User user) {
        String sql = "update user set name = ? where user_id = ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setInt(2, user.getUserID());
            int result = ps.executeUpdate();
            if (result == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;//throw new RuntimeException(e);
        }
        return false;
    }
}
