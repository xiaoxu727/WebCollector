package cn.edu.hfut.dmic.webcollector.plugin.redis;

import cn.edu.hfut.dmic.webcollector.crawler.Crawler;
import cn.edu.hfut.dmic.webcollector.fetcher.Executor;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BerkeleyDBManager;

/**
 * Created by champion_xu on 2017/12/20.
 */
public class RedisCrawler extends Crawler{

    public RedisCrawler(String addr, int port, String seedKey, String seenKey, String linkedKey, Executor executor) {
        super(new RedisDBManager(addr, port, seedKey, seenKey, linkedKey), executor);
    }

}
