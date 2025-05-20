package vn.edu.hcmuaf.fit.web.redis;

import redis.clients.jedis.Jedis;

public class RedisManager {
    private static Jedis jedis;

    static {
        jedis = new Jedis("localhost", 6379); // Thay đổi nếu Redis không chạy ở localhost
    }

    public static Jedis getJedis() {
        return jedis;
    }
}

