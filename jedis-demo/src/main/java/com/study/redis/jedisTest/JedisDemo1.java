package com.study.redis.jedisTest;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @Author: luohx
 * @Description: 秒杀版本1.0和改进版本1.1 解决链接超时问题和使用jedis.watch乐观锁解决超卖问题
 * @Date: 2021/6/16 0016 14:51
 */
public class JedisDemo1 {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379, 1000);
        String value = jedis.ping();
        System.out.println(value);
        jedis.close();
    }

    public static boolean secKill(String uid, String proId) throws IOException {
        //1、uid和proId非空判断
        if (uid == null || proId == null) {
            return false;
        }
        //2、连接redis
//        Jedis jedis = new Jedis("127.0.0.1", 6379, 1000);
        //修改为通过连接池获取jedis对象，解决链接超时问题
        JedisPool jedisPool = JedisPoolUtil.getJedisPoolInstance();
        Jedis jedis = jedisPool.getResource();
        //3、拼接key
        //3.1 库存key
        String kcKey = "sk:" + proId + ":qt";
        //3.2 秒杀成功用户key
        String userKey = "sk:" + proId + ":user";

        //监视库存
        jedis.watch(kcKey);

        //4、获取库存，如果null，秒杀还没开始
        String kc = jedis.get(kcKey);
        if (kc == null) {
            System.out.println("秒杀还没有开始，请等待");
            jedis.close();
            return false;
        }
        //5、判断用户是否重复秒杀操作
        if (jedis.sismember(userKey, uid)) {
            System.out.println("已经秒杀成功了，不能重复秒杀");
            jedis.close();
            return false;
        }
        //6、判断如果商品数量，库存数量小于1，秒杀结束
        if (Integer.parseInt(kc) <= 0) {
            System.out.println("秒杀已经结束了");
            jedis.close();
            return false;
        }
        //7、秒杀过程

        //使用事务
        Transaction multi = jedis.multi();

        //组队操作
        multi.decr(kcKey);
        multi.sadd(userKey, uid);

        List<Object> results = multi.exec();

        if (results == null || results.size() == 0) {
            System.out.println("秒杀失败了....");
            jedis.close();
            return false;
        }

        //7.1、库存-1
//        jedis.decr(kcKey);
        //7.2、把秒杀成功用户添加秒杀清单里
//        jedis.sadd(userKey, uid);
        System.out.println("秒杀成功了...");
        jedis.close();
        return true;
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
        jedis.set("name", "lucy");

        String name = jedis.get("name");
        System.out.println(name);

        jedis.mset("k1", "v1", "k2", "v2");
        List<String> mget = jedis.mget("k1", "k2");
        System.out.println(mget);

        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }
        jedis.close();
    }
}
