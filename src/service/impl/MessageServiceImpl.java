package service.impl;

import dao.MessageDao;
import dao.impl.MessageDaoImpl;
import domain.MessageVo;
import service.MessageService;

import java.util.List;

public class MessageServiceImpl implements MessageService {
    MessageDao messageDao = new MessageDaoImpl();

    //插入新消息
    @Override
    public boolean createNewMessage(int userID, String orderID, String content) {
        return messageDao.insertNewMessage(userID, orderID, content);
    }

    //根据用户id查询消息
    @Override
    public List<MessageVo> getMessageByUserId(int userId) {
        return messageDao.findMessageByUserId(userId);
    }
}
