package dao.impl;

import dao.OrderDao;
import domain.DemandVo;
import domain.OrderDetailVo;
import domain.PostDemandVo;
import util.JdbcUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public boolean insertNewOrder(PostDemandVo postDemandVo) {
        String sql = "INSERT INTO `order`(`start_place_province`, `start_place_city`, `start_place_district`,`des_place_province`," +
                "`des_place_city`,`des_place_district`,`deliver_time`,`length`,`weight`,`price`,`owner_id`,`description`, `order_id`, `des_place_detail`, `start_place_detail`, `distance`, `recommend_price`) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, postDemandVo.getStartPlaceProvince());
            ps.setString(2, postDemandVo.getStartPlaceCity());
            ps.setString(3, postDemandVo.getStartPlaceDistrict());
            ps.setString(4, postDemandVo.getDesPlaceProvince());
            ps.setString(5, postDemandVo.getDesPlaceCity());
            ps.setString(6, postDemandVo.getDesPlaceDistrict());
            try {
                java.util.Date date = new SimpleDateFormat("yyyy/MM/dd").parse(postDemandVo.getDeliverTime());
                if (date.compareTo(new Date(new Date().getTime() - 24 * 60 * 60 * 1000)) < 0) {   //如果日期小于当前日期，则返回false
                    return false;
                }
                long time = date.getTime();//返回当前日期对应的long类型的毫秒数
                java.sql.Date date2 = new java.sql.Date(time);
                ps.setDate(7, date2);
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
            ps.setDouble(8, postDemandVo.getLength());
            ps.setDouble(9, postDemandVo.getWeight());
            ps.setInt(10, postDemandVo.getPrice());
            ps.setInt(11, postDemandVo.getOwnerID());
            ps.setString(12, postDemandVo.getDescription());
            String orderID = new Date().getTime() + (int) ((Math.random() * 9 + 1) * 100000) + "";
            ps.setString(13, orderID);
            ps.setString(14, postDemandVo.getDesPlaceDetail());
            ps.setString(15, postDemandVo.getStartPlaceDetail());
            ps.setDouble(16, postDemandVo.getDistance());
            ps.setDouble(17, postDemandVo.getRecommendPrice());
//            System.out.println(postDemandVo);
            int result = ps.executeUpdate();
            if (result == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;//throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public List<DemandVo> findDemandByUserId(int userId, boolean userType) {
        String sql = "select * from `order` where owner_id = ? order by order_time desc";
        if (userType)
            sql = "select * from `order` where driver_id = ? order by order_time desc";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet resultSet = ps.executeQuery();
            List<DemandVo> demandVoList = new ArrayList<>();
            while (resultSet.next()) {
                DemandVo demandVo = new DemandVo();
                demandVo.setOrderID(resultSet.getString("order_id"));
                demandVo.setDeliverTime(resultSet.getDate("deliver_time").toString());
                demandVo.setDescription(resultSet.getString("description"));
                demandVo.setPrice(resultSet.getInt("price"));
                demandVo.setDesPlaceCity(resultSet.getString("des_place_city"));
                demandVo.setState(resultSet.getString("state"));
                Date date = new Date(resultSet.getTimestamp("order_time").getTime());
                demandVo.setOrderTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
                demandVo.setStartPlaceCity(resultSet.getString("start_place_city"));
                demandVo.setWeight(resultSet.getDouble("weight"));
                demandVoList.add(demandVo);
            }
            return demandVoList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<DemandVo> findDemandByState(String state) {
        String sql = "select * from `order` where state = ? order by order_time desc";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, state);
            ResultSet resultSet = ps.executeQuery();
            List<DemandVo> demandVoList = new ArrayList<>();
            while (resultSet.next()) {
                DemandVo demandVo = new DemandVo();
                demandVo.setOrderID(resultSet.getString("order_id"));
                demandVo.setDeliverTime(resultSet.getDate("deliver_time").toString());
                demandVo.setDescription(resultSet.getString("description"));
                demandVo.setPrice(resultSet.getInt("price"));
                demandVo.setDesPlaceCity(resultSet.getString("des_place_city"));
                demandVo.setState(resultSet.getString("state"));
                Date date = new Date(resultSet.getTimestamp("order_time").getTime());
                demandVo.setOrderTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
                demandVo.setStartPlaceCity(resultSet.getString("start_place_city"));
                demandVo.setWeight(resultSet.getDouble("weight"));
                demandVoList.add(demandVo);
            }
            return demandVoList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public OrderDetailVo findOrderDetailByOrderId(String orderId) {
        String sql = "select * from `order` left join `user` as u on `order`.owner_id = u.user_id where order_id = ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, orderId);
            ResultSet resultSet = ps.executeQuery();
            OrderDetailVo orderDetailVo = new OrderDetailVo();
            if (resultSet.next()) {
                orderDetailVo.setOrderID(resultSet.getString("order_id"));
                orderDetailVo.setOwnerID(String.valueOf(resultSet.getInt("owner_id")));
                orderDetailVo.setDriverID(String.valueOf(resultSet.getInt("driver_id")));
                orderDetailVo.setDeliverTime(resultSet.getDate("deliver_time").toString());
                orderDetailVo.setDescription(resultSet.getString("description"));
                orderDetailVo.setPrice(String.valueOf(resultSet.getInt("price")));
                orderDetailVo.setDesPlaceCity(resultSet.getString("des_place_city"));
                orderDetailVo.setState(resultSet.getString("state"));
                Date date = new Date(resultSet.getTimestamp("order_time").getTime());
                orderDetailVo.setOrderTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
                orderDetailVo.setStartPlaceCity(resultSet.getString("start_place_city"));
                orderDetailVo.setWeight(String.valueOf(resultSet.getDouble("weight")));
                orderDetailVo.setOwnerName(resultSet.getString("u.name"));
                orderDetailVo.setOwnerPhone(resultSet.getString("u.phone"));
                orderDetailVo.setStartPlaceProvince(resultSet.getString("start_place_province"));
                orderDetailVo.setStartPlaceDistrict(resultSet.getString("start_place_district"));
                orderDetailVo.setDesPlaceProvince(resultSet.getString("des_place_province"));
                orderDetailVo.setDesPlaceDistrict(resultSet.getString("des_place_district"));
                orderDetailVo.setLength(String.valueOf(resultSet.getDouble("length")));
                orderDetailVo.setDesPlaceDetail(resultSet.getString("des_place_detail"));
                orderDetailVo.setStartPlaceDetail(resultSet.getString("start_place_detail"));
                orderDetailVo.setDistance(String.valueOf(resultSet.getDouble("distance")));
                orderDetailVo.setRecommendPrice(String.valueOf(resultSet.getInt("recommend_price")));
                int i = resultSet.getInt("driver_id");
                if (i != 0) {
                    sql = "select * from `user` where user_id = ?";
                    try {
                        ps = JdbcUtils.conn.prepareStatement(sql);
                        ps.setInt(1, i);
                        resultSet = ps.executeQuery();
                        if (resultSet.next()) {
                            orderDetailVo.setDriverName(resultSet.getString("name"));
                            orderDetailVo.setDriverPhone(resultSet.getString("phone"));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                return orderDetailVo;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String findOrderStateByOrderId(String orderId) {
        String sql = "select state from `order` where order_id = ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, orderId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("state");
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateOrderStateByUser(String orderId, int userId, String state) {
        String sql = "update `order` set state = ? where order_id = ? and owner_id = ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, state);
            ps.setString(2, orderId);
            ps.setInt(3, userId);
            int i = ps.executeUpdate();
            if (i == 1) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateOrderStateAndDriverByUser(String orderId, int ownerId, int driverId, String state) {
        String sql = "update `order` set state = ?,driver_id=? where order_id = ? and owner_id = ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, state);
            ps.setInt(2, driverId);
            ps.setString(3, orderId);
            ps.setInt(4, ownerId);
            int i = ps.executeUpdate();
            if (i == 1) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateOrderStateByDriver(String orderId, int userId, String state) {
        String sql = "update `order` set state = ? where order_id = ? and driver_id = ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, state);
            ps.setString(2, orderId);
            ps.setInt(3, userId);
            int i = ps.executeUpdate();
            if (i == 1) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public double findMinLengthByUserId(int userId) {
        String sql = "select min(length) as min_length from `order` where owner_id = ? and state ='待接单'";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("min_length");
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public double findMinWeightByUserId(int userId) {
        String sql = "select min(weight) as min_weight from `order` where owner_id = ? and state ='待接单'";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("min_weight");
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<DemandVo> findDemandByStateLenWei(String state, double len, double wei) {
        String sql = "select * from `order` where state = ? and length <= ? and weight <= ? order by order_time desc";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, state);
            ps.setDouble(2, len);
            ps.setDouble(3, wei);
            ResultSet resultSet = ps.executeQuery();
            List<DemandVo> demandVoList = new ArrayList<>();
            while (resultSet.next()) {
                DemandVo demandVo = new DemandVo();
                demandVo.setOrderID(resultSet.getString("order_id"));
                demandVo.setDeliverTime(resultSet.getDate("deliver_time").toString());
                demandVo.setDescription(resultSet.getString("description"));
                demandVo.setPrice(resultSet.getInt("price"));
                demandVo.setDesPlaceCity(resultSet.getString("des_place_city"));
                demandVo.setState(resultSet.getString("state"));
                Date date = new Date(resultSet.getTimestamp("order_time").getTime());
                demandVo.setOrderTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
                demandVo.setStartPlaceCity(resultSet.getString("start_place_city"));
                demandVo.setWeight(resultSet.getDouble("weight"));
                demandVoList.add(demandVo);
            }
            return demandVoList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DemandVo> findDemandByStateLenWeiStart(String state, double len, double wei, String start) {
        String sql = "select * from `order` where state = ? and length <= ? and weight <= ? and start_place_city = ? order by order_time desc";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, state);
            ps.setDouble(2, len);
            ps.setDouble(3, wei);
            ps.setString(4, start);
            ResultSet resultSet = ps.executeQuery();
            List<DemandVo> demandVoList = new ArrayList<>();
            while (resultSet.next()) {
                DemandVo demandVo = new DemandVo();
                demandVo.setOrderID(resultSet.getString("order_id"));
                demandVo.setDeliverTime(resultSet.getDate("deliver_time").toString());
                demandVo.setDescription(resultSet.getString("description"));
                demandVo.setPrice(resultSet.getInt("price"));
                demandVo.setDesPlaceCity(resultSet.getString("des_place_city"));
                demandVo.setState(resultSet.getString("state"));
                Date date = new Date(resultSet.getTimestamp("order_time").getTime());
                demandVo.setOrderTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
                demandVo.setStartPlaceCity(resultSet.getString("start_place_city"));
                demandVo.setWeight(resultSet.getDouble("weight"));
                demandVoList.add(demandVo);
            }
            return demandVoList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DemandVo> findDemandByStateLenWeiEnd(String state, double len, double wei, String end) {
        String sql = "select * from `order` where state = ? and length <= ? and weight <= ? and des_place_city = ? order by order_time desc";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, state);
            ps.setDouble(2, len);
            ps.setDouble(3, wei);
            ps.setString(4, end);
            ResultSet resultSet = ps.executeQuery();
            List<DemandVo> demandVoList = new ArrayList<>();
            while (resultSet.next()) {
                DemandVo demandVo = new DemandVo();
                demandVo.setOrderID(resultSet.getString("order_id"));
                demandVo.setDeliverTime(resultSet.getDate("deliver_time").toString());
                demandVo.setDescription(resultSet.getString("description"));
                demandVo.setPrice(resultSet.getInt("price"));
                demandVo.setDesPlaceCity(resultSet.getString("des_place_city"));
                demandVo.setState(resultSet.getString("state"));
                Date date = new Date(resultSet.getTimestamp("order_time").getTime());
                demandVo.setOrderTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
                demandVo.setStartPlaceCity(resultSet.getString("start_place_city"));
                demandVo.setWeight(resultSet.getDouble("weight"));
                demandVoList.add(demandVo);
            }
            return demandVoList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DemandVo> findDemandByStateLenWeiStartEnd(String state, double len, double wei, String start, String end) {
        String sql = "select * from `order` where state = ? and length <= ? and weight <= ? and start_place_city = ? and des_place_city = ? order by order_time desc";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setString(1, state);
            ps.setDouble(2, len);
            ps.setDouble(3, wei);
            ps.setString(4, start);
            ps.setString(5, end);
            ResultSet resultSet = ps.executeQuery();
            List<DemandVo> demandVoList = new ArrayList<>();
            while (resultSet.next()) {
                DemandVo demandVo = new DemandVo();
                demandVo.setOrderID(resultSet.getString("order_id"));
                demandVo.setDeliverTime(resultSet.getDate("deliver_time").toString());
                demandVo.setDescription(resultSet.getString("description"));
                demandVo.setPrice(resultSet.getInt("price"));
                demandVo.setDesPlaceCity(resultSet.getString("des_place_city"));
                demandVo.setState(resultSet.getString("state"));
                Date date = new Date(resultSet.getTimestamp("order_time").getTime());
                demandVo.setOrderTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
                demandVo.setStartPlaceCity(resultSet.getString("start_place_city"));
                demandVo.setWeight(resultSet.getDouble("weight"));
                demandVoList.add(demandVo);
            }
            return demandVoList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updatePriceByOrderIDAndUserID(String orderId, int userId, int price) {
        String sql = "update `order` set price = ? where order_id = ? and owner_id = ?";
        try {
            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
            ps.setInt(1, price);
            ps.setString(2, orderId);
            ps.setInt(3, userId);
            int i = ps.executeUpdate();
            if (i == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
