package cn.edu.hfut.dmic.webcollector.plugin.redis;

import cn.edu.hfut.dmic.webcollector.crawldb.CrawDatumWriter;
import cn.edu.hfut.dmic.webcollector.crawldb.Generator;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import redis.clients.jedis.Jedis;

/**
 * Created by champion_xu on 2017/12/15.
 */
public class RedisGenerator extends Generator {

    private String key;
    private Jedis jedis;
    RedisCrawDatumWriter writer;

    public RedisGenerator(Jedis jedis, String key){
        this.jedis = jedis;
        this.key = key;
         writer = new RedisCrawDatumWriter(jedis);
    }


    @Override
    public CrawlDatum nextWithoutFilter() throws Exception {

        return writer.getCrawDatum(key);
    }

    @Override
    public void close() throws Exception {
        jedis.close();
    }
}
