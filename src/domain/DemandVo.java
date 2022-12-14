package domain;

//用于展示订单列表的信息，只含有订单的基本信息
public class DemandVo {
    private String orderID;
    private String orderTime;
    private String startPlaceCity;
    private String desPlaceCity;
    private String deliverTime;
    private Double weight;
    private int price;
    private String state;
    private String description;

    public DemandVo() {
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getStartPlaceCity() {
        return startPlaceCity;
    }

    public void setStartPlaceCity(String startPlaceCity) {
        this.startPlaceCity = startPlaceCity;
    }

    public String getDesPlaceCity() {
        return desPlaceCity;
    }

    public void setDesPlaceCity(String desPlaceCity) {
        this.desPlaceCity = desPlaceCity;
    }

    public String getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(String deliverTime) {
        this.deliverTime = deliverTime;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
