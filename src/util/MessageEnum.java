package util;

//订单状态，包括：待接单，已接单，进行中，待支付，已完成，已取消
public class MessageEnum {
    public static final String CANCEL_ORDER = "{0} 操作了取消订单";
    public static final String TAKING_ORDER = "司机 {0} 完成了接单，快去看看吧";
    public static final String TAKING_ORDER_DRIVER = "您已完成接单，请尽快联系货主完成发货";
    public static final String START_ORDER = "订单已完成发货，货物即将启程，请耐心等待";
    public static final String START_ORDER_DRIVER = "您已完成发货，货物描述：{0}，即将送往：{1}";
    public static final String FINISH_ORDER = "订单已完成，请尽快完成支付";
    public static final String FINISH_ORDER_DRIVER = "订单已完成，系统已通知货主进行支付";
    public static final String PAY_ORDER = "订单已完成支付，感谢您的使用";
    public static final String PAY_ORDER_DRIVER = "订单已完成支付，{0}元已入账，感谢您的使用";
    public static final String PUSH_ORDER = "您已向司机 {0} 推送了该笔订单，等待司机接单";
    public static final String PUSH_ORDER_DRIVER = "货主 {0} 向你推送了一个订单，货物内容：{1}，由 {2} 发往 {3}，请尽快查看";
}
