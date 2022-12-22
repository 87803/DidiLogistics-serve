package service.impl;

import dao.DriverDao;
import dao.UserDao;
import dao.impl.DriverDaoImpl;
import dao.impl.UserDaoImpl;
import domain.User;
import service.UserService;
import util.EmailUtils;
import util.RedisUtils;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();
    DriverDao driverDao = new DriverDaoImpl();

    @Override
    public boolean register(String phone, String password, boolean type) {
        return userDao.insertNewUser(phone, password, type);
    }

    @Override
    public boolean getVerificationCode(String phone) {
        String code = (int) ((Math.random() * 9 + 1) * 100000) + "";
        System.out.println("sending");
        //对字符串的操作
        //存储一个值
        try {
            if (!RedisUtils.set(phone, code))
                return false;
            System.out.println("验证码已发送，phone: " + phone + ", code: " + code);
            EmailUtils.sendEmail(phone, code, "");
            return true;
        } catch (Exception e) {
            System.out.println("未能连接到Redis，" + e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @Override
    public User getUserInfo(int userID) {
        return userDao.findUserByID(userID);
    }

    @Override
    public User login(String phone, String password) {
        return userDao.findUserByPhoneAndPassword(phone, password);
    }

    @Override
    public boolean updatePassword(int userID, String password, String newPassword) {
        return userDao.updatePassword(userID, password, newPassword);
    }

    @Override
    public boolean updateUserInfo(User user) {
        return userDao.updateUserInfo(user);
    }

    @Override
    public boolean updateDriverInfo(User user) {
        return driverDao.updateDriverInfo(user);
    }


}
