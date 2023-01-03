package service;

import domain.User;

public interface UserService {
    //用户注册
    boolean register(String phone, String password, boolean type);

    //获取验证码
    boolean getVerificationCode(String phone);

    //根据用户id获取用户信息
    User getUserInfo(int userID);

    //用户登录
    User login(String phone, String password);

    //修改密码
    boolean updatePassword(int userID, String password, String newPassword);

    //修改用户信息
    boolean updateUserInfo(User user);

    //修改司机信息
    boolean updateDriverInfo(User user);
}
