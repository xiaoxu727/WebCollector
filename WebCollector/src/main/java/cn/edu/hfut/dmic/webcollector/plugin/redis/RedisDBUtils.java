package cn.edu.hfut.dmic.webcollector.plugin.redis;


import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.util.CrawlDatumFormater;
import com.sleepycat.je.DatabaseEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ScanResult;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.*;

/**
 * Created by champion_xu on 2017/12/13.
 */
public class RedisDBUtils {

    Logger LOG = LoggerFactory.getLogger(RedisDBUtils.class);

    //Redis服务器IP
    private static String ADDR = "192.168.25.65";

    //Redis的端口号
    private static int PORT = 6379;

    //访问密码
    private static String AUTH = null;

    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = 1024;

    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 200;

    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 10000;

    private static int TIMEOUT = 10000;

    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;

    private static JedisPool jedisPool = null;

    /**
     * 初始化Redis连接池
     */
    public static void InitRedis(String addr, int port) {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            ADDR = addr;
            PORT = port;
            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化Redis连接池
     */
    public static void InitRedis() {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Jedis实例
     * @return
     */
    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool == null) {
                InitRedis();
            }
            Jedis resource = jedisPool.getResource();
            return resource;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 释放jedis资源
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
//            jedisPool.close();
        }
    }

    public static void close(){
        if(jedisPool != null){
            jedisPool.close();
        }
    }

    public static boolean isClose(){
        if(jedisPool != null){
            return jedisPool.isClosed();
        }
       return true;
    }
    public static long writeSet(String setKey, String value){
        Jedis jedis = getJedis();
        long result = 0;
        if(jedis != null){
            result = jedis.sadd(setKey, value);
            returnResource(jedis);
            return result;
        }
        return result;

    }
    public  static long writeHset( String hsetKey, String field, String value){
        Jedis jedis = getJedis();
        long result = 0;
        if(jedis != null ){
           result = jedis.hset(hsetKey, field, value);
            returnResource(jedis);
            return  result;

        }
        return result;
    }


    public static String readHset( String hsetKey, String field){
        Jedis jedis = getJedis();
        String value = null;
        if(jedis != null){
            value = jedis.hget(hsetKey, field);
        }
        returnResource(jedis);
        return value;
    }

      public static Map.Entry<String, String> readHset(String hsetKey){
          Jedis jedis = getJedis();
          Iterator<Map.Entry<String, String>> it = getHsetIterator( hsetKey);
          Map.Entry<String,String> mapentry = null;
          if(it.hasNext()){
              mapentry = it.next();
          }
          returnResource(jedis);
          return  mapentry;
    }

    public static Map.Entry<String,String>  getHsetNext(String hsetKey){
        Jedis jedis = getJedis();
        Map<String,String> map = new HashMap<String, String>();
        String setKey = hsetKey + ":key:set";
        if(!RedisDBUtils.isKeyExists(jedis, setKey)){
            Set<String> keys = jedis.hkeys(hsetKey);
            for(String key : keys){
                jedis.sadd(setKey, key);
            }
        }
        String firstFiled = jedis.spop(setKey);
        String value = jedis.hget(hsetKey,firstFiled);
        returnResource(jedis);
        if(firstFiled != null && value != null){
            map.put(firstFiled,value);
            return map.entrySet().iterator().next();
        }
        return  null;
    }

    public static Iterator<Map.Entry<String, String>> getHsetIterator(String hsetKey){
        Jedis jedis = getJedis();
        Map<String,String> hashMapKey = jedis.hgetAll(hsetKey);

        // map 的第一种迭代方式
        Set<Map.Entry<String, String>> entry = hashMapKey.entrySet();
        Iterator<Map.Entry<String, String>> it = entry.iterator();
        returnResource(jedis);
        return  it;
    }



    public static DatabaseEntry strToEntry(String str) throws UnsupportedEncodingException {
        return new DatabaseEntry(str.getBytes("utf-8"));
    }

    public static boolean isHsetExists(Jedis jedis, String key, String field){
        if(jedis != null){
            return jedis.hexists(key,field);
        }
        return  false;
    }

    public static boolean isKeyExists(Jedis jedis, String key){
        if(jedis != null){
            return jedis.exists(key);
        }
        return false;
    }

    public static boolean removeHaset( String key, String filedKey){
        Jedis jedis = getJedis();
        if(jedis != null){
            jedis.hdel(key,filedKey);
            returnResource(jedis);
            return  true;
        }
        return false;
    }

    public static String getValueFromSet(String key){
        Jedis jedis = getJedis();
        if(jedis != null){
            String value = jedis.spop(key);
            jedis.sadd(key,value);
            returnResource(jedis);
            return value;
        }
        return null;
    }

}
