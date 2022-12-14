package service;

import domain.User;

public interface UserService {
    boolean register(String phone, String password, boolean type);

    boolean getVerificationCode(String phone);

    User getUserInfo(int userID);

    User login(String phone, String password);

    boolean updatePassword(int userID, String password, String newPassword);

    boolean updateUserInfo(User user);

    boolean updateDriverInfo(User user);
}
