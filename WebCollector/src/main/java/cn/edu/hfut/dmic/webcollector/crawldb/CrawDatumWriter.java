package cn.edu.hfut.dmic.webcollector.crawldb;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.util.CrawlDatumFormater;
import com.sleepycat.je.DatabaseEntry;
import redis.clients.jedis.Jedis;

/**
 * Created by champion_xu on 2017/12/15.
 */
public interface CrawDatumWriter {

    public long writeCrawDatum(String key, CrawlDatum crawlDatum);

    public long writeCrawDatums(String key, CrawlDatums crawlDatums);

    public  CrawlDatum getCrawDatum(String key);

    public  CrawlDatum getCrawDatum(String key, String key2);

}
