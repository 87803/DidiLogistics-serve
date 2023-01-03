package util;

import redis.clients.jedis.Jedis;

// Redis工具类，用于保存和验证验证码
public class RedisUtils {
    private static final Jedis jedis = new Jedis("localhost", 6379);

    public static void close() {
        jedis.close();
    }

    public static boolean set(String key, String value) {
        String s = jedis.set(key, value);
        jedis.expire(key, 5 * 60);//设置过期时间5分钟
        return s.equals("OK");
    }

    public static boolean verify(String key, String value) {
        String s = jedis.get(key);
        if (s == null) {
            return false;
        }
        return s.equals(value);
    }
}
