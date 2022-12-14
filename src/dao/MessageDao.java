package dao;

import domain.MessageVo;

import java.util.List;

public interface MessageDao {
    //插入新消息
    boolean insertNewMessage(int userID, String orderID, String content);

    //根据用户id查询消息
    List<MessageVo> findMessageByUserId(int userId);
}
