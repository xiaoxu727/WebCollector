package cn.edu.hfut.dmic.webcollector.plugin.redis;

import cn.edu.hfut.dmic.webcollector.crawldb.CrawDatumWriter;
import cn.edu.hfut.dmic.webcollector.crawldb.DBManager;
import cn.edu.hfut.dmic.webcollector.crawldb.Generator;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.WebSite;
import cn.edu.hfut.dmic.webcollector.util.CrawlDatumFormater;
import cn.edu.hfut.dmic.webcollector.util.MD5Utils;
import redis.clients.jedis.Jedis;
import com.google.gson.Gson;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by champion_xu on 2017/12/13.
 */
public class RedisDBManager extends DBManager {

    private Jedis jedis;
    private String addr;
    private int port;
//    用来存种子网页
    private String seedKey;
//    存储访问过的
    private String seenKey;
//    将要被访问的url
    private String linkedKey;
    private CrawDatumWriter writer = null;


    public RedisDBManager (String addr, int port, String seedKey, String seenKey, String linkedKey){
        this.addr = addr;
        this.port = port;
        this.seedKey = seedKey;
        this.seenKey = seenKey;
        this.linkedKey = linkedKey;
        RedisDBUtils.InitRedis(this.addr,this.port);
        this.jedis = RedisDBUtils.getJedis();
        this.writer = new RedisCrawDatumWriter(jedis);
    }

    @Override
    public boolean isDBExists() {
        Jedis jedis = RedisDBUtils.getJedis();
        if(jedis != null && jedis.ping().equals("PONG")){
            return true;
        }
        return false;
    }

    @Override
    public void clear() throws Exception {
        jedis.del(this.seedKey);
        jedis.del(this.seenKey);
        jedis.del(this.linkedKey);
//        RedisDBUtils.returnResource(jedis);
    }

    @Override
    public Generator createGenerator() {
        RedisGenerator generator = new RedisGenerator(this.jedis, linkedKey);
        return generator;
    }

    @Override
    public void open() throws Exception {
        if(jedis!= null&&jedis.isConnected()){
            return;
        }
        if(jedis == null && !RedisDBUtils.isClose() ){
            jedis = RedisDBUtils.getJedis();
            return;
        }
    }

    @Override
    public void close() throws Exception {
        RedisDBUtils.returnResource(jedis);
    }

    @Override
    public void inject(CrawlDatum datum, boolean force) throws Exception {
        if(!force){
            if(RedisDBUtils.isHsetExists(jedis, linkedKey, datum.key())){
                return;
            }
        }
        writer.writeCrawDatum(linkedKey, datum);
    }

    @Override
    public void inject(CrawlDatums datums, boolean force) throws Exception {
        for(CrawlDatum crawlDatum : datums){
            inject(crawlDatum, force);
        }
    }

    @Override
    public void merge() throws Exception {
        Iterator<Map.Entry<String,String>> it = RedisDBUtils.getHsetIterator(seedKey);
         Gson gson = new Gson();
        CrawlDatums crawlDatums = new CrawlDatums();
        while(it.hasNext()){
            Map.Entry<String,String> entry = it.next();
            String jsonStr = entry.getValue();
            CrawlDatum crawlDatum = CrawlDatumFormater.jsonStrToDatum(jsonStr);
            WebSite webSite = WebSite.websiteFromCrawDautm(crawlDatum);
            if(webSite.isValid()){
                crawlDatum.key(MD5Utils.md5(webSite.getUrl(),"utf-8"));
                crawlDatum.url(webSite.getUrl());
                crawlDatum.type("seed");
                crawlDatum.meta("siteName",webSite.getSiteName());
                crawlDatum.meta("maxDepth", webSite.getMaxDepth());
                crawlDatum.meta("negative", webSite.getNegativeFilter());
                crawlDatum.meta("positive",webSite.getPositiveFilter());
                crawlDatums.add(crawlDatum);
            }

        }
        writeParseSegment(crawlDatums);

    }

    @Override
    public void initSegmentWriter() throws Exception {

    }

    @Override
    public void writeFetchSegment(CrawlDatum fetchDatum) throws Exception {
        writer.writeCrawDatum(seenKey, fetchDatum);

    }

    @Override
    public void writeParseSegment(CrawlDatums parseDatums) throws Exception {
        for(CrawlDatum crawlDatum : parseDatums){
            if(!RedisDBUtils.isHsetExists(jedis,seenKey,crawlDatum.key())||"seed".equals(crawlDatum.type())){
                writer.writeCrawDatum(linkedKey, crawlDatum);
            }
        }

    }
    @Override
    public void writeSeedSegment(CrawlDatums seedDatums) throws Exception {
        for(CrawlDatum crawlDatum : seedDatums){
            if(!RedisDBUtils.isHsetExists(jedis,seedKey,crawlDatum.key())){
                writer.writeCrawDatum(seedKey, crawlDatum);
            }
        }
    }

    @Override
    public void closeSegmentWriter() throws Exception {

    }
}
