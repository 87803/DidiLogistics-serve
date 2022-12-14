package service;

import domain.MessageVo;

import java.util.List;

public interface MessageService {
    //插入新消息
    boolean createNewMessage(int userID, String orderID, String content);

    //根据用户id查询消息
    List<MessageVo> getMessageByUserId(int userId);
}
