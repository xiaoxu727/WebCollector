package cn.edu.hfut.dmic.webcollector.plugin.redis;

import cn.edu.hfut.dmic.webcollector.crawler.AutoParseCrawler;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.util.PropertiesUtils;

/**
 * Created by champion_xu on 2017/12/20.
 */
public abstract class RedisAutoParseCrawler extends AutoParseCrawler {
    protected static String ADDR = "";
    protected static int PORT;
    protected static  String SEEDKEY;
    protected static String SEENKEY;
    protected static String LINKEDKEY;
    static String PATH ="config/config.properties";
    static {
        ADDR = PropertiesUtils.getAsString(PATH, "redis_url");
        PORT = PropertiesUtils.getAsInt(PATH, "redis_port");
        SEEDKEY = PropertiesUtils.getAsString(PATH, "seedKey");
        SEENKEY = PropertiesUtils.getAsString(PATH, "seenKey");
        LINKEDKEY = PropertiesUtils.getAsString(PATH, "linkedKey");
    }

    public RedisAutoParseCrawler(String addr, int port, String seedKey, String seenKey, String linkedKey, boolean autoParse){
        super(autoParse);
        this.ADDR = addr;
        this.PORT = port;
        this.SEEDKEY = seedKey;
        this.SEENKEY = seenKey;
        this.LINKEDKEY = linkedKey;
        this.dbManager = new RedisDBManager(ADDR, PORT, SEEDKEY, SEENKEY, LINKEDKEY);
    }

    public RedisAutoParseCrawler(){
        super(false);
        this.dbManager = new RedisDBManager(ADDR, PORT, SEEDKEY, SEENKEY, LINKEDKEY);
    }

}
