package cn.edu.hfut.dmic.webcollector.plugin.redis;

import cn.edu.hfut.dmic.webcollector.crawldb.CrawDatumWriter;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.util.CrawlDatumFormater;
import com.sleepycat.je.DatabaseEntry;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanResult;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by champion_xu on 2017/12/15.
 */
public class RedisCrawDatumWriter implements CrawDatumWriter{

    Jedis jedis = null;
    public RedisCrawDatumWriter(Jedis jedis){
        this.jedis = jedis;
    }

    public long writeCrawDatum(String hsetKey, CrawlDatum crawlDatum) {
        String value = CrawlDatumFormater.datumToJsonStr(crawlDatum);
        return RedisDBUtils.writeHset( hsetKey, crawlDatum.key(), value);

    }

    public long writeCrawDatums(String hsetKey, CrawlDatums crawlDatums) {
        int count = 0 ;
        for( CrawlDatum crawlDatum : crawlDatums ){
            count += this.writeCrawDatum(hsetKey, crawlDatum);
        }
        return count;
    }

    public CrawlDatum getCrawDatum(String hasetKey, String fieldKey) {
        CrawlDatum crawlDatum = null;
        String value = RedisDBUtils.readHset(hasetKey, fieldKey);
        try{
            if (value != null) {
                crawlDatum = createCrawlDatum(new DatabaseEntry(fieldKey.getBytes()), new DatabaseEntry(value.getBytes()));
            }
        }catch (Exception e){

        }
        return crawlDatum;
    }

    public CrawlDatum getCrawDatum ( String hasetKey){
        CrawlDatum crawlDatum = null;
        Map.Entry<String, String> mapentry = null;
        try {
            mapentry = RedisDBUtils.getHsetNext(hasetKey);
            if(mapentry != null) {
                String fieldKey = mapentry.getKey();
                String value = mapentry.getValue();
                crawlDatum = createCrawlDatum(RedisDBUtils.strToEntry(fieldKey), RedisDBUtils.strToEntry(value));
                RedisDBUtils.removeHaset(hasetKey, fieldKey);
            }
        }catch (Exception e){

        }
        return crawlDatum;
    }

    public CrawlDatum createCrawlDatum(DatabaseEntry key, DatabaseEntry value) throws Exception{
        String datumKey=new String(key.getData(),"utf-8");
        String valueStr=new String(value.getData(),"utf-8");
        return CrawlDatumFormater.jsonStrToDatum(datumKey, valueStr);
    }

    public boolean isHsetKeyExits(Jedis jedis, String haskey, String fieldkey){
        return  RedisDBUtils.isHsetExists(jedis, haskey, fieldkey);
    }

}
