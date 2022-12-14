package domain;

public class User {
    private int userID;
    private String nickname;
    private String name;
    private String password;
    private boolean type;
    private String phone;
    private String curCity;
    private String desCity;
    private boolean state;
    private Double carLength;
    private Double carWeight;

    public User() {
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCurCity() {
        return curCity;
    }

    public void setCurCity(String curCity) {
        this.curCity = curCity;
    }

    public String getDesCity() {
        return desCity;
    }

    public void setDesCity(String desCity) {
        this.desCity = desCity;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Double getCarLength() {
        return carLength;
    }

    public void setCarLength(Double carLength) {
        this.carLength = carLength;
    }

    public Double getCarWeight() {
        return carWeight;
    }

    public void setCarWeight(Double carWeight) {
        this.carWeight = carWeight;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", nickname='" + nickname + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                ", phone='" + phone + '\'' +
                ", curCity='" + curCity + '\'' +
                ", desCity='" + desCity + '\'' +
                ", state=" + state +
                ", carLength=" + carLength +
                ", carWeight=" + carWeight +
                '}';
    }
}
