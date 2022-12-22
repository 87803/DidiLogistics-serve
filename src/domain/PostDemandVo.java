package domain;

//用于接收用户提交的订单内容
public class PostDemandVo {
    private int ownerID;
    private String startPlaceCity;
    private String desPlaceCity;
    private String deliverTime;
    private Double weight;
    private int price;
    private String state;
    private String description;
    private Double length;
    private String startPlaceProvince;
    private String startPlaceDistrict;
    private String desPlaceProvince;
    private String desPlaceDistrict;
    private String startPlaceDetail;
    private String desPlaceDetail;
    private Double distance;
    private Double recommendPrice;

    public PostDemandVo() {
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

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public String getStartPlaceProvince() {
        return startPlaceProvince;
    }

    public void setStartPlaceProvince(String startPlaceProvince) {
        this.startPlaceProvince = startPlaceProvince;
    }

    public String getStartPlaceDistrict() {
        return startPlaceDistrict;
    }

    public void setStartPlaceDistrict(String startPlaceDistrict) {
        this.startPlaceDistrict = startPlaceDistrict;
    }

    public String getDesPlaceProvince() {
        return desPlaceProvince;
    }

    public void setDesPlaceProvince(String desPlaceProvince) {
        this.desPlaceProvince = desPlaceProvince;
    }

    public String getDesPlaceDistrict() {
        return desPlaceDistrict;
    }

    public void setDesPlaceDistrict(String desPlaceDistrict) {
        this.desPlaceDistrict = desPlaceDistrict;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }


    public String getStartPlaceDetail() {
        return startPlaceDetail;
    }

    public void setStartPlaceDetail(String startPlaceDetail) {
        this.startPlaceDetail = startPlaceDetail;
    }

    public String getDesPlaceDetail() {
        return desPlaceDetail;
    }

    public void setDesPlaceDetail(String desPlaceDetail) {
        this.desPlaceDetail = desPlaceDetail;
    }

    @Override
    public String toString() {
        return "PostDemandVo{" +
                "ownerID=" + ownerID +
                ", startPlaceCity='" + startPlaceCity + '\'' +
                ", desPlaceCity='" + desPlaceCity + '\'' +
                ", deliverTime='" + deliverTime + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                ", state='" + state + '\'' +
                ", description='" + description + '\'' +
                ", length=" + length +
                ", startPlaceProvince='" + startPlaceProvince + '\'' +
                ", startPlaceDistrict='" + startPlaceDistrict + '\'' +
                ", desPlaceProvince='" + desPlaceProvince + '\'' +
                ", desPlaceDistrict='" + desPlaceDistrict + '\'' +
                ", startPlaceDetail='" + startPlaceDetail + '\'' +
                ", desPlaceDetail='" + desPlaceDetail + '\'' +
                '}';
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getRecommendPrice() {
        return recommendPrice;
    }

    public void setRecommendPrice(Double recommendPrice) {
        this.recommendPrice = recommendPrice;
    }
}
