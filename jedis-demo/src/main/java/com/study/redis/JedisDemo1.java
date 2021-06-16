package com.study.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * @Author: luohx
 * @Description: 描述
 * @Date: 2021/6/16 0016 14:51
 */
public class JedisDemo1 {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379, 1000);
        String value = jedis.ping();
        System.out.println(value);
        jedis.close();
    }

    //操作zset
    @Test
    public void demo5() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.zadd("china", 100d, "上海");
        Set<String> china = jedis.zrange("china", 0, -1);

        System.out.println(china);
        jedis.close();
    }

    //操作hash
    @Test
    public void demo4() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.hset("users", "age", "20");
        String hget = jedis.hget("users", "age");

        System.out.println(hget);
        jedis.close();
    }

    //操作set
    @Test
    public void demo3() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.sadd("names", "lucy");
        jedis.sadd("names", "jack");

        Set<String> names = jedis.smembers("names");
        System.out.println(names);
        jedis.close();
    }

    //操作list集合
    @Test
    public void demo2() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        //获取所有的key
        jedis.lpush("key1", "lucy", "mary", "jack");
        List<String> values = jedis.lrange("key1", 0, -1);
        System.out.println(values);
        jedis.close();
    }

    //操作key string
    @Test
    public void demo1() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        //获取所有的key
        jedis.set("name","lucy");

        String name = jedis.get("name");
        System.out.println(name);

        jedis.mset("k1","v1","k2","v2");
        List<String> mget = jedis.mget("k1", "k2");
        System.out.println(mget);

        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }
        jedis.close();
    }
}
