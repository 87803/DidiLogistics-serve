import service.MessageService;
import service.OrderService;
import service.UserService;
import service.impl.MessageServiceImpl;
import service.impl.OrderServiceImpl;
import service.impl.UserServiceImpl;

public class test {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        OrderService orderService = new OrderServiceImpl();
        MessageService messageService = new MessageServiceImpl();
        System.out.println(userService.login("16755556666", "12345678"));
//        Date date = new SimpleDateFormat("yyyy/MM/dd").parse("2022/11/23");
//        long time = date.getTime();//返回当前日期对应的long类型的毫秒数
//        java.sql.Date date2 = new java.sql.Date(time);
//        System.out.println(.getAllOrder(1));
//        Map<String, String> map = new HashMap<>();
//        map.put("name", "张三");
//        map.put("code", "123456");
//        String token = JWTUtils.getTokenRsa(map);
//        System.out.println(token);
//        try {
//
//            System.out.println(decodedJWT.getClaim("name").asString());
//            System.out.println(decodedJWT.getClaim("code").asString());
//        } catch (Exception e) {
//            System.out.println("解析失败");
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
//        System.out.println("发送成功");
//        String sql = "INSERT INTO `user`(`phone`, `password`) VALUES(?,?)";
//        try {
//            PreparedStatement ps = JdbcUtils.conn.prepareStatement(sql);
//            ps.setString(1, "phone");
//            ps.setString(2, "password");
//            int result = ps.executeUpdate();
//            if (result == 1) {
//                System.out.println("success");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}
