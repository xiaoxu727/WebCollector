package com.champion.data.crawler.GSJIllegal.processor;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import com.champion.data.crawler.GSJIllegal.model.ShCreditPermissionInfo;
import com.champion.data.crawler.GSJIllegal.model.ShCreditPermissionList;
import com.champion.data.crawler.GSJIllegal.model.ShCreditPublishInfo;
import com.champion.data.crawler.GSJIllegal.model.ShCreditPublishList;
import com.champion.data.crawler.GSJIllegal.pipline.ShCreditApp;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by champion_xu on 2017/12/8.
 * 上海行政处罚和行政许可
 */
public class ShCreditPermissionPublish extends BreadthCrawler {


    /**
     * 构造一个基于伯克利DB的爬虫
     * 伯克利DB文件夹为crawlPath，crawlPath中维护了历史URL等信息
     * 不同任务不要使用相同的crawlPath
     * 两个使用相同crawlPath的爬虫并行爬取会产生错误
     *
     * @param crawlPath 伯克利DB使用的文件夹
     * @param autoParse 是否根据设置的正则自动探测新URL
     */
    static String PUBLISH_DETAIL_URL = "http://cxw.shcredit.gov.cn:8081/sh_xyxxzc/sgsinfo/getcfinfo.action?cfid=";
    static String PERMMISSION_DETAIL_URL = "http://cxw.shcredit.gov.cn:8081/sh_xyxxzc/sgsinfo/getxkinfo.action?xkid=";
    public ShCreditPermissionPublish(String crawlPath, boolean autoParse) throws IOException {
        super(crawlPath, autoParse);
        getConf().setExecuteInterval(10);
//        for(int i = 0; i< 720; i++){
//            String url = String.format("http://cxw.shcredit.gov.cn:8081/sh_xyxxzc/cflist/newcfgrid.action?search=false&nd=1512717189234&rows=100&page=%d&sidx=&sord=asc", i);
//            addSeed(new CrawlDatum(url).type("publishList"));
//        }
//        for(int i = 0; i< 10130; i++){
//            String url = String.format("http://cxw.shcredit.gov.cn:8081/sh_xyxxzc/xklist/newxkgrid.action?search=false&nd=1516062301613&rows=100&page=%d&sidx=&sord=asc0", i);
//            addSeed(new CrawlDatum(url).type("permissionList"));
//        }
//
//
//        addSeed(new CrawlDatum("http://cxw.shcredit.gov.cn:8081/sh_xyxxzc/sgsinfo/getcfinfo.action?cfid=57632CEDE8FB7F95E0530100007F4755").type("detail"));
//        addSeed(new CrawlDatum("http://cxw.shcredit.gov.cn:8081/sh_xyxxzc/xklist/newxkgrid.action?search=false&nd=1516062301613&rows=10&page=3&sidx=&sord=asc")
//                .type("permissionList"));
//        addSeed(new CrawlDatum("http://cxw.shcredit.gov.cn:8081/sh_xyxxzc/sgsinfo/getxkinfo.action?xkid=（沪）JZ安许证字[2016]016109")
//                .type("permissionDetail"));
        File file = new File("config/seed");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        while ( (line =reader.readLine()) != null ){
            addSeed(new CrawlDatum(line)
                .type("permissionDetail"));
        }
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        if("publishList".equals(page.crawlDatum().type())) {
            JsonObject object = page.jsonObject().getAsJsonObject();
            JsonArray jsonElements = object.getAsJsonArray("gridModel");
            List<ShCreditPublishList> publishLists = new ArrayList<ShCreditPublishList>();
            for (JsonElement obj : jsonElements) {
                ShCreditPublishList record = new ShCreditPublishList();
                String cfid = getJsonValueByKey(obj.getAsJsonObject(), "cfid");
                record.setCfid(cfid);
                record.setCfmc(getJsonValueByKey(obj.getAsJsonObject(), "cfmc"));
                record.setCfwsh(getJsonValueByKey(obj.getAsJsonObject(), "cfwsh"));
                record.setXzxdr(getJsonValueByKey(obj.getAsJsonObject(), "xzxdr"));
                record.setDetailUrl(PUBLISH_DETAIL_URL + cfid);
                publishLists.add(record);
                CrawlDatum crawlDatum = new CrawlDatum(PUBLISH_DETAIL_URL + cfid);
                crawlDatum.type("publishDetail");
                next.add(crawlDatum);
            }
            ShCreditApp.insertShCreditPublishListBatch(publishLists);
        }else if("publishDetail".equals(page.crawlDatum().type())){
            page.html(page.html().replace("[","").replace("]",""));
            JsonObject object = page.jsonObject();
            ShCreditPublishInfo publishInfo = new ShCreditPublishInfo();
            publishInfo.setCfjdrq(getJsonValueByKey(object, "cfjdrq"));
            publishInfo.setCfwsh(getJsonValueByKey(object, "cfwsh"));
            publishInfo.setXzxdr(getJsonValueByKey(object, "xzxdr"));
            publishInfo.setCfjguan(getJsonValueByKey(object, "cfjguan"));
            publishInfo.setCflb(getJsonValueByKey(object, "cflb"));
            publishInfo.setCfyj(getJsonValueByKey(object, "cfyj"));
            publishInfo.setCfmc(getJsonValueByKey(object, "cfmc"));
            publishInfo.setCfsy(getJsonValueByKey(object, "cfsy"));
            publishInfo.setUrl(page.url());
            ShCreditApp.insertShCreditPublishInfo(publishInfo);
        }else if("permissionList".equals(page.crawlDatum().type())){
            JsonObject object = page.jsonObject().getAsJsonObject();
            JsonArray jsonElements = object.getAsJsonArray("gridModel");
            List<ShCreditPermissionList> permissionLists = new ArrayList<ShCreditPermissionList>();
            for (JsonElement obj : jsonElements) {
                ShCreditPermissionList record = new ShCreditPermissionList();
                String xkid = getJsonValueByKey(obj.getAsJsonObject(), "xkid");
                record.setXkid(xkid);
                record.setXkwsh(getJsonValueByKey(obj.getAsJsonObject(), "xkwsh"));
                record.setXmmc(getJsonValueByKey(obj.getAsJsonObject(), "xmmc"));
                record.setXzxdr(getJsonValueByKey(obj.getAsJsonObject(), "xzxdr"));
                record.setDetailUrl(PERMMISSION_DETAIL_URL + xkid);
                permissionLists.add(record);
                CrawlDatum crawlDatum = new CrawlDatum(PERMMISSION_DETAIL_URL + xkid);
                crawlDatum.type("permissionDetail");
                next.add(crawlDatum);
            }
            ShCreditApp.insertShCreditPermissionListBatch(permissionLists);
        }else if("permissionDetail".equals(page.crawlDatum().type())){
            page.html(page.html().replace("[","").replace("]",""));
            JsonObject object = page.jsonObject();
            ShCreditPermissionInfo permissionInfo = new ShCreditPermissionInfo();
            permissionInfo.setSplb(getJsonValueByKey(object, "splb"));
            permissionInfo.setXkjdrq(getJsonValueByKey(object, "xkjdrq"));
            permissionInfo.setXkjg(getJsonValueByKey(object, "xkjg"));
            permissionInfo.setXkjzrq(getJsonValueByKey(object, "xkjzrq"));
            permissionInfo.setXknr(getJsonValueByKey(object, "xknr") );
            permissionInfo.setXkwsh(getJsonValueByKey(object, "xkwsh"));
            permissionInfo.setXmmc(getJsonValueByKey(object, "xmmc") );
            permissionInfo.setXzxdr(getJsonValueByKey(object, "xzxdr"));
            permissionInfo.setUrl(page.url());
            ShCreditApp.insertShCreditPermissionInfo(permissionInfo);
        }
    }
    public String getJsonValueByKey(JsonObject obj, String key){
        if(!obj.get(key).isJsonNull()){
            return  obj.get(key).getAsString();
        }
        return null;

    }
    public static void main(String[] args) throws Exception{
        ShCreditPermissionPublish crawler = new ShCreditPermissionPublish("CreditSH", true);
        crawler.setThreads(5);
        crawler.start(100);

    }
}
