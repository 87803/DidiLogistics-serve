package dao.impl;

import dao.MessageDao;
import domain.MessageVo;
import util.JdbcUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MessageDaoImpl implements MessageDao {
    @Override
    public boolean insertNewMessage(int userID, String orderID, String content) {
        String sql = "INSERT INTO `message`(`user_id`, `order_id`, `content`) VALUES(?,?,?)";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setInt(1, userID);
            ps.setString(2, orderID);
            ps.setString(3, content);
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
    public List<MessageVo> findMessageByUserId(int userId) {
        String sql = "select * from `message` where user_id = ? order by message_id desc";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet resultSet = ps.executeQuery();
            List<MessageVo> messageVoList = new ArrayList<>();
            while (resultSet.next()) {
                MessageVo messageVo = new MessageVo();
                messageVo.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultSet.getTimestamp("time")));
                messageVo.setUserID(String.valueOf(resultSet.getInt("user_id")));
                messageVo.setOrderID(resultSet.getString("order_id"));
                messageVo.setContent(resultSet.getString("content"));
                messageVoList.add(messageVo);
            }
            return messageVoList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
