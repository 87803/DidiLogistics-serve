package dao;

import domain.User;

public interface UserDao {
    //插入新用户，用于注册
    boolean insertNewUser(String phone, String password, boolean type);

    //根据用户ID查询用户
    User findUserByID(int userID);

    //根据手机号和密码查询用户
    User findUserByPhoneAndPassword(String phone, String password);

    //修改密码
    boolean updatePassword(int userID, String password, String newPassword);

    //修改用户信息
    boolean updateUserInfo(User user);

}
