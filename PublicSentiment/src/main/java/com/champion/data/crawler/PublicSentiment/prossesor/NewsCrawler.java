package com.champion.data.crawler.PublicSentiment.prossesor;

import cn.edu.hfut.dmic.contentextractor.ContentExtractor;
import cn.edu.hfut.dmic.contentextractor.News;
import cn.edu.hfut.dmic.webcollector.model.*;
import cn.edu.hfut.dmic.webcollector.plugin.redis.RedisAutoParseCrawler;
import cn.edu.hfut.dmic.webcollector.util.GsonUtils;
import cn.edu.hfut.dmic.webcollector.util.MD5Utils;
import cn.edu.hfut.dmic.webcollector.util.PropertiesUtils;
import com.champion.data.crawler.PublicSentiment.pipline.HBasePiplineDBManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * Created by champion_xu on 2017/12/20.
 */
public class NewsCrawler extends RedisAutoParseCrawler {
    public static final Logger LOG = LoggerFactory.getLogger(Page.class);
    HBasePiplineDBManager piplineDBManager = null ;
    static String PATH ="config/config.properties";
    static {

    }
    public NewsCrawler(String addr, int port, String seedKey, String seenKey, String linkedKey, boolean autoParse) {
        super(addr, port, seedKey, seenKey, linkedKey, autoParse);
        try{
            dbManager.merge();
            piplineDBManager = new HBasePiplineDBManager();

        }catch (Exception e){
            LOG.info("新闻爬虫初始化失败!");
            LOG.info(e.getMessage());
        }
    }
    public NewsCrawler(boolean autoParse){
        this(PATH, autoParse);
    }

    public NewsCrawler(String path, boolean autoParse){
        piplineDBManager = new HBasePiplineDBManager();
        try {
            String websitesStr = PropertiesUtils.getAsString(PATH, "websites");
            Gson gson = new Gson();
            List<WebSite> webSiteList = gson.fromJson(websitesStr,new TypeToken<List<WebSite>>(){}.getType());
            CrawlDatums seedDatums = new CrawlDatums();
            for(WebSite webSite : webSiteList){
                CrawlDatum crawlDatum = webSite.toCrawDatum();
                crawlDatum.meta("depth","1");
                seedDatums.add(crawlDatum);
//                dbManager.inject(crawlDatum);
            }
            dbManager.writeSeedSegment(seedDatums);
            dbManager.writeParseSegment(seedDatums);
//            dbManager.merge();
            CrawlDatum datum = dbManager.createGenerator().next();
            int initCount = 0;
            while (datum != null){
                addSeed(datum);
                if(initCount >= 1000){
                    break;
                }
                datum = dbManager.createGenerator().next();
                initCount ++;
            }

        }catch (Exception e){
            LOG.info("新闻爬虫初始化失败!");
            e.printStackTrace();
             LOG.info(e.getMessage());
        }
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
            News news =null;
        try{
            news = ContentExtractor.getNewsByHtml(page.html());
            news.setUrl(page.url());
            piplineDBManager.insert("ps_news", page.url(), "what", "key", page.key());
            piplineDBManager.insert("ps_news", page.url(), "what", "title", news.getTitle());
            piplineDBManager.insert("ps_news", page.url(), "what", "content", news.getContent());
            piplineDBManager.insert("ps_news", page.url(), "what", "publishDate", news.getTime());
            piplineDBManager.insert("ps_news", page.url(), "what", "url", page.url());
            piplineDBManager.insert("ps_news", page.url(), "what", "site", page.meta("siteName"));
            piplineDBManager.insert("ps_news", page.url(), "when", "crawDate", page.meta("siteName"));
            LOG.info(news.toString());

         }catch (Exception e){
            LOG.info("hbase insert data error,detail url:"+page.url());
            if(news != null){
                LOG.info("hbase insert data error,detail news:" + news.toString());
            }
         }finally {
            if(page.metaAsInt("depth")+ 1 > page.metaAsInt("maxDepth") ){
                return;
            }
            Links links = new Links();

            links.addByRegex(page.doc(),page.meta("postiveFilter"));
            int pageInit =  page.metaAsInt("depth");
            for (String link : links){
                CrawlDatum crawlDatum = new CrawlDatum(link);
                crawlDatum.key(MD5Utils.md5(link,"utf-8"));
                crawlDatum.url(link);
                crawlDatum.type("link");
                crawlDatum.meta(page.meta());
                crawlDatum.meta("depth", pageInit +1);
//                crawlDatum
                next.add(crawlDatum);
            }
        }
    }
    public static void main(String[] args){
        try{
            NewsCrawler crawler = new NewsCrawler(false);
            crawler.setThreads(1);
            crawler.start(2);
        }catch (Exception e){

        }
    }
}
