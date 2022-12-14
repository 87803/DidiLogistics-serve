package dao.impl;

import dao.DriverDao;
import domain.User;
import util.JdbcUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
