package com.champion.data.crawler.GSJIllegal.prossesor;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HtmlUnitRequest;
import cn.edu.hfut.dmic.webcollector.net.HttpClientRequest;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.net.SeleniumRequest;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.util.GsonUtils;
import com.champion.data.crawler.GSJIllegal.model.GsjIllegal;
import com.champion.data.crawler.GSJIllegal.pipline.GsjIllegalApp;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by champion_xu on 2017/11/27.
 */
public class GsjIllegalCrawler extends BreadthCrawler {
    private Map<String,String> detail = null;

    /**
     * 内蒙古网页打不开
     * 辽宁 广东
     *
     * 湖南
     * @param path
     * @param autoParse
     */
    public GsjIllegalCrawler(String path, boolean autoParse ){
        super(path, autoParse);
        this.setThreads(1);

        addSeed(new CrawlDatum("http://sh.gsxt.gov.cn/notice/search/GET/announce?type=0201&mode=all&pageNo=1&areaId=&keyword=")
                .meta("province", "上海")
                .meta("detail", "http://sh.gsxt.gov.cn/notice/search/announce_detail?uuid=%s&category=02&categorySub=01")
        .type("list"));

        addSeed(new CrawlDatum("http://tj.gsxt.gov.cn/corp-query-entprise-info-getAfficheInIllInfo-120000.html?draw=1&start=0&length=10")
                .meta("province", "天津")
//                .meta("detail", "http://sh.gsxt.gov.cn/notice/search/announce_detail?uuid=%s&category=02&categorySub=01")
        .type("list"));

        addSeed(new CrawlDatum("http://bj.gsxt.gov.cn/xxgg/xxggAction!queryGgxx.dhtml?clear=true&notitype=21")
                .meta("province", "北京")
//                .meta("detail", "http://sh.gsxt.gov.cn/notice/search/announce_detail?uuid=%s&category=02&categorySub=01")
        .type("list"));

        addSeed(new CrawlDatum("http://he.gsxt.gov.cn/notice/search/GET/announce?type=0201&mode=all&pageNo=1&areaId=&keyword=")
                .meta("province", "河北")
//                .meta("detail", "http://sh.gsxt.gov.cn/notice/search/announce_detail?uuid=%s&category=02&categorySub=01")
        .type("list"));

        addSeed(new CrawlDatum("http://sx.gsxt.gov.cn/seriousViolation.jspx?mark=01&pageNo=1&title=&area=&t=Tue%20Nov%2028%202017%2014:27:21%20GMT+0800%20(%E4%B8%AD%E5%9B%BD%E6%A0%87%E5%87%86%E6%97%B6%E9%97%B4)")
                .meta("province", "山西")
//                .meta("detail", "http://sh.gsxt.gov.cn/notice/search/announce_detail?uuid=%s&category=02&categorySub=01")
        .type("list"));

        addSeed(new CrawlDatum("http://jl.gsxt.gov.cn/api/Common/AfficheInfos?type=2&subType=21&keyWord=&page=1&judauth=")
                .meta("province", "吉林")
//                .meta("detail", "http://sh.gsxt.gov.cn/notice/search/announce_detail?uuid=%s&category=02&categorySub=01")
        .type("list"));

        addSeed(new CrawlDatum("http://hl.gsxt.gov.cn/seriousViolation.jspx?mark=01&pageNo=1&title=&area=&t=Tue%20Nov%2028%202017%2014:41:41%20GMT+0800%20(%E4%B8%AD%E5%9B%BD%E6%A0%87%E5%87%86%E6%97%B6%E9%97%B4)")
                .meta("province", "黑龙江")
//                .meta("detail", "http://sh.gsxt.gov.cn/notice/search/announce_detail?uuid=%s&category=02&categorySub=01")
        .type("list"));

        addSeed(new CrawlDatum("http://www.jsgsj.gov.cn:58888/province/NoticeServlet.json?yzwfNoticeList=true&type=1&titleNotice=&address=&pageSize=10&curPage=1&sortName=&sortOrder=")
                .meta("province", "江苏")
                .meta("detail", "http://www.jsgsj.gov.cn:58888/province/NoticeServlet.json?yzwfDetail=true&org=%s&id=%s&seqId=%s&type=%s&markType=%s")
        .type("list"));

        addSeed(new CrawlDatum("http://zj.gsxt.gov.cn/pub/infobulletin/list.json?_t=1511860459541&start=0&length=5&params%5BpubType%5D=4&params%5BpubTitle%5D=&params%5BauditArea%5D=")
                .meta("province", "浙江")
//                .meta("detail", "http://www.jsgsj.gov.cn:58888/province/NoticeServlet.json?yzwfDetail=true&org=%s&id=%s&seqId=%s&type=%s&markType=%s")
        .type("list"));

        addSeed(new CrawlDatum("http://218.22.14.70/seriousViolation.jspx?mark=01&pageNo=1&title=&area=&t=Tue%20Nov%2028%202017%2017:18:57%20GMT+0800%20(%E4%B8%AD%E5%9B%BD%E6%A0%87%E5%87%86%E6%97%B6%E9%97%B4)&clwz_blc_atc=UO/Zw6cswLvsEdg1Y3/ccFnHZ5zVnrsGh6kJS9u9RHE=")
                .meta("province", "安徽")
//                .meta("detail", "http://www.jsgsj.gov.cn:58888/province/NoticeServlet.json?yzwfDetail=true&org=%s&id=%s&seqId=%s&type=%s&markType=%s")
        .type("list"));

        addSeed(new CrawlDatum("http://fj.gsxt.gov.cn/notice/search/GET/announce?type=0201&mode=all&pageNo=1&areaId=&keyword=")
                .meta("province", "福建")
//                .meta("detail", "http://www.jsgsj.gov.cn:58888/province/NoticeServlet.json?yzwfDetail=true&org=%s&id=%s&seqId=%s&type=%s&markType=%s")
        .type("list"));

        addSeed(new CrawlDatum("http://jx.gsxt.gov.cn/affichebase/queryAffichebase.do?noticetype=21&citycode=0&searchKey=&radom=0.7983274175785482&currentPage=1&funName=change&search=&url=%2Faffichebase%2FqueryAffichebase.do%3Fnoticetype%3D21%26citycode%3D0%26searchKey%3D%26radom%3D0.7983274175785482&_=1511862514981")
                .meta("province", "江西")
                .meta("detail", "http://jx.gsxt.gov.cn/illegality/queryyzwfinfolieru.do?noticeid=%s&noticetype=%s")
                .type("list"));

          addSeed(new CrawlDatum("http://gx.gsxt.gov.cn/xxgg/xxggAction!queryGgxx.dhtml?clear=true&notitype=21")
                .meta("province", "广西")
//                .meta("detail", "http://jx.gsxt.gov.cn/illegality/queryyzwfinfolieru.do?noticeid=%s&noticetype=%s")
                .type("list"));

          addSeed(new CrawlDatum("http://hi.gsxt.gov.cn/seriousViolation.jspx?mark=01&pageNo=1&title=&area=&t=Wed%20Nov%2029%202017%2013:46:40%20GMT+0800%20(%E4%B8%AD%E5%9B%BD%E6%A0%87%E5%87%86%E6%97%B6%E9%97%B4)")
                .meta("province", "海南")
//                .meta("detail", "http://jx.gsxt.gov.cn/illegality/queryyzwfinfolieru.do?noticeid=%s&noticetype=%s")
                .type("list"));

        addSeed(new CrawlDatum("http://ha.gsxt.gov.cn/seriousViolation.jspx?mark=01&pageNo=1&title=&area=&t=Wed%20Nov%2029%202017%2013:49:34%20GMT+0800%20(%E4%B8%AD%E5%9B%BD%E6%A0%87%E5%87%86%E6%97%B6%E9%97%B4)")
                .meta("province", "河南")
//                .meta("detail", "http://jx.gsxt.gov.cn/illegality/queryyzwfinfolieru.do?noticeid=%s&noticetype=%s")
                .type("list"));

          addSeed(new CrawlDatum("http://hb.gsxt.gov.cn/seriousViolation.jspx?mark=01&pageNo=1&title=&area=&t=Wed%20Nov%2029%202017%2014:27:02%20GMT+0800%20(%E4%B8%AD%E5%9B%BD%E6%A0%87%E5%87%86%E6%97%B6%E9%97%B4)")
                .meta("province", "湖北")
//                .meta("detail", "http://jx.gsxt.gov.cn/illegality/queryyzwfinfolieru.do?noticeid=%s&noticetype=%s")
                .type("list"));

        addSeed(new CrawlDatum("http://hn.gsxt.gov.cn/notice/search/GET/announce?type=0201&mode=all&pageNo=1&areaId=&keyword=")
                .meta("province", "湖南")
                .meta("detail", "http://hn.gsxt.gov.cn/notice/search/announce_detail?uuid=%s&category=02&categorySub=01")
                .type("list"));

        addSeed(new CrawlDatum("http://cq.gsxt.gov.cn/xxgg/yzwfgg/lrgg_list.html")
                .meta("province", "重庆")
//                .meta("detail", "http://hn.gsxt.gov.cn/notice/search/announce_detail?uuid=%s&category=02&categorySub=01")
                .type("list"));

         addSeed(new CrawlDatum("http://sc.gsxt.gov.cn/notice/search/GET/announce?type=0201&mode=all&pageNo=1&areaId=&keyword=")
                .meta("province", "四川")
                .meta("detail", "http://sc.gsxt.gov.cn/notice/%s")
                  .type("list"));

          addSeed(new CrawlDatum("http://gz.gsxt.gov.cn/seriousViolation.jspx?mark=01&pageNo=1&title=&area=&t=Wed%20Nov%2029%202017%2017:02:39%20GMT+0800%20(%E4%B8%AD%E5%9B%BD%E6%A0%87%E5%87%86%E6%97%B6%E9%97%B4)")
                .meta("province", "贵州")
                .meta("detail", "http://gz.gsxt.gov.cn/%s")
                  .type("list"));

          addSeed(new CrawlDatum("http://yn.gsxt.gov.cn/notice/search/GET/announce?type=0201&mode=all&pageNo=1&areaId=&keyword=")
                .meta("province", "云南")
//                .meta("detail", "http://gz.gsxt.gov.cn/%s")
                  .type("list"));

          addSeed(new CrawlDatum("http://xz.gsxt.gov.cn/seriousViolation.jspx?mark=01&pageNo=1&title=&area=&t=Wed%20Nov%2029%202017%2017:54:56%20GMT+0800%20(%E4%B8%AD%E5%9B%BD%E6%A0%87%E5%87%86%E6%97%B6%E9%97%B4)")
                .meta("province", "西藏")
//                .meta("detail", "http://gz.gsxt.gov.cn/%s")
                  .type("list"));

        addSeed(new CrawlDatum("http://sn.gsxt.gov.cn/xxcx.do?method=xxggList&random=1511949609569&xxlx=2&status=2&djjg=&entname=&xh=&glxh=&geetest_challenge=&geetest_validate=&geetest_seccode=&geetest_challenge=&geetest_validate=&geetest_seccode=&page.currentPageNo=1")
                .meta("province", "陕西")
                .meta("detail", "http://sn.gsxt.gov.cn/xxcx.do?method=xxggXq&random=1511949798994&xxlx=%s&status=%s&djjg=&entname=&xh=%s&glxh=%s&geetest_challenge=&geetest_validate=&geetest_seccode=&geetest_challenge=&geetest_validate=&geetest_seccode=&page.currentPageNo=%s")
                .type("list"));

        addSeed(new CrawlDatum("http://gs.gsxt.gov.cn/gsxygs/notice/yzwfIn?queryType=yzwfIn")
                .meta("province", "甘肃")
                .meta("detail", "http://gs.gsxt.gov.cn/gsxygs/notice/yzwfInDetail?illType=in&noticeid=%s")
                .type("list"));

        addSeed(new CrawlDatum("http://qh.gsxt.gov.cn/seriousViolation.jspx?mark=01&pageNo=1&title=&area=&t=Thu%20Nov%2030%202017%2010:05:03%20GMT+0800%20(%E4%B8%AD%E5%9B%BD%E6%A0%87%E5%87%86%E6%97%B6%E9%97%B4)")
                .meta("province", "青海")
//                .meta("detail", "http://gs.gsxt.gov.cn/gsxygs/notice/yzwfInDetail?illType=in&noticeid=%s")
                .type("list"));

        addSeed(new CrawlDatum("http://nx.gsxt.gov.cn/noticeAction_noticeList.action?noticeType=21")
                .meta("province", "宁夏")
//                .meta("detail", "http://gs.gsxt.gov.cn/gsxygs/notice/yzwfInDetail?illType=in&noticeid=%s")
                .type("list"));

        addSeed(new CrawlDatum("http://xj.gsxt.gov.cn/xxgg/xxggAction!queryGgxx.dhtml?clear=true&notitype=21")
                .meta("province", "新疆")
//                .meta("detail", "http://gs.gsxt.gov.cn/gsxygs/notice/yzwfInDetail?illType=in&noticeid=%s")
                .type("list"));

        addSeed(new CrawlDatum("http://ln.gsxt.gov.cn/saicpub/entPublicitySC/entPublicityDC/entPublicity/js/controller/xxgg.js")
                .meta("province", "辽宁")
//                .meta("detail", "http://gs.gsxt.gov.cn/gsxygs/notice/yzwfInDetail?illType=in&noticeid=%s")
                .type("list"));
        addSeed(new CrawlDatum("http://sd.gsxt.gov.cn/pub/notice/")
                .meta("province", "山东")
                .meta("javascripts", "document.getElementById(\"linotice21\").click();document.getElementsByClassName(\"paggeractive\")[0].nextElementSibling.click();document.getElementsByClassName(\"paggeractive\")[0].nextElementSibling.click();document.getElementsByClassName(\"paggeractive\")[0].nextElementSibling.click()")
                .meta("driverType","phantomjs")
                .meta("detail", "http://sd.gsxt.gov.cn%s")
                .type("list"));
    }

    @Override
    public Page getResponse(CrawlDatum crawlDatum) throws Exception {

        HttpRequest request = null;
        String[] clientRequest = {"上海", "新疆","安徽","江西","海南","河南","湖南","四川"};
        String[] seleniumRequst = {"山东"};
        if(Arrays.asList(clientRequest).contains(crawlDatum.meta("province"))){
            request = new HttpClientRequest(crawlDatum);

        }else if(Arrays.asList(seleniumRequst).contains(crawlDatum.meta("province"))){
            request = new SeleniumRequest(crawlDatum);
        }else {
            request = new HttpRequest(crawlDatum);
        }
      request.setTimeoutForRead(60000);

        return request.responsePage();
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        if(page == null){
            return;
        }
        if(page.meta("province").equals("上海")){
            parserSH(page, next);
        }else if(page.meta("province").equals("天津")){
            parserTJ(page, next);
        }else if(page.meta("province").equals("北京")){
            parserBJ(page, next);
        }else if(page.meta("province").equals("河北")){
            parserHB(page, next);
        }else if(page.meta("province").equals("山西")){
            parserSX(page, next);
        }else if(page.meta("province").equals("吉林")){
            parserJL(page, next);
        }else if(page.meta("province").equals("黑龙江")){
            parserHLJ(page, next);
        }else if(page.meta("province").equals("江苏")){
            parserJS(page, next);
        }else if(page.meta("province").equals("浙江")){
            parserZJ(page, next);
        } else if(page.meta("province").equals("安徽")){
            parserAH(page, next);
        }else if(page.meta("province").equals("福建")){
            parserFJ(page, next);
        }else if(page.meta("province").equals("江西")){
            parserJX(page, next);
        }else if(page.meta("province").equals("广西")){
            parserGX(page, next);
        }else if(page.meta("province").equals("海南")){
            parserHN(page, next);
        }else if(page.meta("province").equals("河南")){
            parserHeN(page, next);
        }else if(page.meta("province").equals("湖北")){
            parserHuB(page, next);
        }else if(page.meta("province").equals("湖南")){
            parserHuN(page, next);
        }else if(page.meta("province").equals("重庆")){
            parserCQ(page, next);
        }else if(page.meta("province").equals("四川")){
            parserSC(page, next);
        }else if(page.meta("province").equals("贵州")){
            parserGZ(page, next);
        }else if(page.meta("province").equals("云南")){
            parserYN(page, next);
        }else if(page.meta("province").equals("西藏")){
            parserXZ(page, next);
        }else if(page.meta("province").equals("陕西")){
            parserSXWest(page, next);
        }else if(page.meta("province").equals("甘肃")){
            parserGS(page, next);
        }else if(page.meta("province").equals("青海")){
            parserQH(page, next);
        }else if(page.meta("province").equals("宁夏")){
            parserNX(page, next);
        }else if(page.meta("province").equals("新疆")){
            parserXJ(page, next);
        }else if(page.meta("province").equals("辽宁")){
            parserLN(page, next);
        }else if(page.meta("province").equals("山东")){
            parserSD(page, next);
        }
    }

    /**
     * 上海解析
     * @param page
     * @param next
     */
    public void parserSH(Page page, CrawlDatums next){
        if(page.crawlDatum().matchType("list")) {
            List<GsjIllegal> records = new ArrayList<GsjIllegal>();
            JsonObject object = page.jsonObject();
            JsonObject result = object.getAsJsonObject("result");
            JsonArray data = result.getAsJsonArray("data");
            for(int i = 0; i< data.size(); i++){
                JsonObject obj = data.get(i).getAsJsonObject();
                GsjIllegal record = new GsjIllegal();
                record.setContentUrl(String.format(page.meta("detail"), obj.get("link").getAsString()));
                record.setTitle(obj.get("etpName").getAsString());
                record.setDepartment(obj.get("orgName").getAsString());
                record.setDate(obj.get("date").getAsString());
                record.setProvince(page.meta("province"));
                records.add(record);

                CrawlDatum datum = new CrawlDatum();
                datum.url(record.getContentUrl());
                datum.type("detail");
                datum.meta("province", "上海");
                next.add(datum);

            }
            GsjIllegalApp.createBatch(records);

        }else if(page.crawlDatum().matchType("detail")){
            GsjIllegal record = new GsjIllegal();
            String content = page.select("div[class=jjyc_word]").first().text();
            record.setContentUrl(page.url());
            record.setContent(content);
            GsjIllegalApp.update(page.url(),content );
        }
    } /**
     * 山东解析
     * @param page
     * @param next
     */
    public void parserSD(Page page, CrawlDatums next){
        if(page.crawlDatum().matchType("list")) {
            List<GsjIllegal> records = new ArrayList<GsjIllegal>();
            Element ele = page.select("div[id=noticegourp]").first();

            for(Element element : ele.children()){
                GsjIllegal record = new GsjIllegal();
                record.setContentUrl(String.format(page.meta("detail"), element.select("div[class=col-md-7]>a").first().attr("href")));
                record.setTitle(element.select("div[class=col-md-7]>a").first().text());
                record.setDepartment(element.select("div[class=col-md-3]").first().text());
                record.setDate(element.select("div[class=col-md-2]").first().text());
                record.setProvince(page.meta("province"));
                records.add(record);
                CrawlDatum datum = new CrawlDatum();
                datum.url(record.getContentUrl());
                datum.type("detail");
                datum.meta("province", "山东");
                datum.meta("driverType", "phantomjs");
                next.add(datum);
            }
            GsjIllegalApp.createBatch(records);

        }else if(page.crawlDatum().matchType("detail")){
            GsjIllegal record = new GsjIllegal();
            String content = page.select("div[class=helpinfo]").first().text();
            record.setContentUrl(page.url());
            record.setContent(content);
            GsjIllegalApp.update(page.url(),content );
        }
    }
    /**
     * 湖南解析
     * @param page
     * @param next
     */
    public void parserHuN(Page page, CrawlDatums next){
        if (page.crawlDatum().matchType("list")) {
            List<GsjIllegal> records = new ArrayList<GsjIllegal>();
            String content = page.select("html").first().children().get(1).text();
            JsonObject object = GsonUtils.parse(content).getAsJsonObject();
            JsonObject result = object.getAsJsonObject("result");
            JsonArray data = result.getAsJsonArray("data");
            for(int i = 0; i< data.size(); i++){
                JsonObject obj = data.get(i).getAsJsonObject();
                GsjIllegal record = new GsjIllegal();
                record.setContentUrl(String.format(page.meta("detail"), obj.get("link").getAsString()));
                record.setTitle(obj.get("etpName").getAsString());
                record.setDepartment(obj.get("orgName").getAsString());
                record.setDate(obj.get("date").getAsString());
                record.setProvince(page.meta("province"));
                records.add(record);

                CrawlDatum datum = new CrawlDatum();
                datum.url(record.getContentUrl());
                datum.type("detail");
                datum.meta("province", "湖南");
                next.add(datum);

            }
            GsjIllegalApp.createBatch(records);

        }else if(page.crawlDatum().matchType("detail")){
            GsjIllegal record = new GsjIllegal();
            String content = page.select("div[class=jjyc_word]").first().text();
            record.setContentUrl(page.url());
            record.setContent(content);
            GsjIllegalApp.update(page.url(),content );
        }
    }
    /**
     * 甘肃解析
     * @param page
     * @param next
     */
    public void parserGS(Page page, CrawlDatums next){
        if (page.crawlDatum().matchType("list")) {
            List<GsjIllegal> records = new ArrayList<GsjIllegal>();
            Elements elements = page.select("table[class=notice-table]").first().select("tr");
            for(Element element : elements){
                GsjIllegal record = new GsjIllegal();
                Elements tds = element.select("td");
                if(tds.size() >= 3){
                    record.setTitle(tds.get(0).select("a").text());
                    record.setContentUrl(String.format(page.meta("detail"), tds.get(0).select("a").attr("onclick").replace("showNotice('", "").replace("')", "")));
                    record.setDate(tds.get(2).text());
                    record.setDepartment(tds.get(1).text());
                    record.setProvince(page.meta("province"));
                    records.add(record);
                    CrawlDatum datum = new CrawlDatum();
                    datum.url(record.getContentUrl());
                    datum.type("detail");
                    datum.meta("province", "甘肃");
                    next.add(datum);
                }
            }
            GsjIllegalApp.createBatch(records);

        }else if(page.crawlDatum().matchType("detail")){
            GsjIllegal record = new GsjIllegal();
            String content = page.select("div[class=notice-info]").first().text();
            record.setContentUrl(page.url());
            record.setContent(content);
            GsjIllegalApp.update(page.url(),content );
        }
    }
    /**
     * 江西解析
     * @param page
     * @param next
     */
    public void parserJX(Page page, CrawlDatums next){
        if(page.crawlDatum().matchType("list")) {
            List<GsjIllegal> records = new ArrayList<GsjIllegal>();
            JsonObject object = page.jsonObject();
//            JsonObject result = object.getAsJsonObject("result");
            JsonArray data =object.getAsJsonArray("data");
            for(int i = 0; i< data.size(); i++){
                JsonObject obj = data.get(i).getAsJsonObject();
                GsjIllegal record = new GsjIllegal();
                record.setCname(obj.get("ENTNAME").getAsString());
                record.setContentUrl(String.format(page.meta("detail"), obj.get("NOTICEID").getAsString(), obj.get("NOTICETYPE").getAsString()));
                record.setDepartment(obj.get("JUDAUTH_CN").getAsString());
                record.setDate(obj.get("NOTICEDATE").getAsString());
                record.setProvince(page.meta("province"));
                records.add(record);

                CrawlDatum datum = new CrawlDatum();
                datum.url(record.getContentUrl());
                datum.type("detail");
                datum.meta("province", "江西");
                next.add(datum);

            }
            GsjIllegalApp.createBatch(records);

        }else if(page.crawlDatum().matchType("detail")){
            GsjIllegal record = new GsjIllegal();
            String content = page.select("div[class=spot]").first().text();
            record.setContentUrl(page.url());
            record.setContent(content);
            GsjIllegalApp.update(page.url(),content );
        }
    }/**
     * 四川解析
     * @param page
     * @param next
     */
    public void parserSC(Page page, CrawlDatums next){
        if(page.crawlDatum().matchType("list")) {
            List<GsjIllegal> records = new ArrayList<GsjIllegal>();
            JsonObject object = page.jsonObject();
            JsonObject result = object.getAsJsonObject("result");
            JsonArray data = result.getAsJsonArray("data");
            for(int i = 0; i< data.size(); i++){
                JsonObject obj = data.get(i).getAsJsonObject();
                GsjIllegal record = new GsjIllegal();
                record.setContentUrl(String.format(page.meta("detail"), obj.get("link").getAsString()));
                record.setTitle(obj.get("etpName").getAsString());
                record.setDepartment(obj.get("orgName").getAsString());
                record.setDate(obj.get("date").getAsString());
                record.setProvince(page.meta("province"));
                records.add(record);

                CrawlDatum datum = new CrawlDatum();
                datum.url(record.getContentUrl());
                datum.type("detail");
                datum.meta("province", "四川");
                next.add(datum);

            }
            GsjIllegalApp.createBatch(records);

        }else if(page.crawlDatum().matchType("detail")){
            GsjIllegal record = new GsjIllegal();
            String content = page.select("div[class=jjyc_word]").first().text();
            record.setContentUrl(page.url());
            record.setContent(content);
            GsjIllegalApp.update(page.url(),content );
        }
    }
    /**
     * 江苏解析
     * @param page
     * @param next
     */
    public void parserJS(Page page, CrawlDatums next){
        if(page.crawlDatum().matchType("list")) {
            List<GsjIllegal> records = new ArrayList<GsjIllegal>();

            JsonObject object = page.jsonObject();
            JsonArray data = object.getAsJsonArray("data");
            for(int i = 0; i< data.size(); i++){
                JsonObject obj = data.get(i).getAsJsonObject();
                GsjIllegal record = new GsjIllegal();
                record.setContentUrl(String.format(page.meta("detail"), obj.get("ORG").getAsString(), obj.get("ID").getAsString(), obj.get("SEQ_ID").getAsString(), obj.get("TYPE").getAsString(), obj.get("MARK_TYPE").getAsString()));
                record.setCname(obj.get("CORP_NAME").getAsString());
                record.setRegisNO(obj.get("REG_NO").getAsString());
                record.setTitle(obj.get("TITLE").getAsString());
                record.setDepartment(obj.get("MARKORGNAME").getAsString());
                record.setDate(obj.get("MARK_DATE").getAsString());
                record.setProvince(page.meta("province"));
                records.add(record);

                CrawlDatum datum = new CrawlDatum();
                datum.url(record.getContentUrl());
                datum.type("detail");
                datum.meta("province", "江苏");
                next.add(datum);

            }
            GsjIllegalApp.createBatch(records);

        }else if(page.crawlDatum().matchType("detail")){
            GsjIllegal record = new GsjIllegal();
            JsonObject object = page.jsonObject();
            String content = object.get("context").getAsString();
            record.setContentUrl(page.url());
            record.setContent(content);
            GsjIllegalApp.update(page.url(),content );
        }
    }
    /**
     * 贵州解析
     * @param page
     * @param next
     */
    public void parserGZ(Page page, CrawlDatums next){
        if(page.crawlDatum().matchType("list")) {
            List<GsjIllegal> records = new ArrayList<GsjIllegal>();

            Elements elements = page.select("table").first().select("tr");
//            JsonArray data = object.getAsJsonArray("data");
            for(Element element : elements){

                GsjIllegal record = new GsjIllegal();
                if(element.getElementById("A3") != null){
                    record.setContentUrl(String.format(page.meta("detail"), element.getElementById("A3").attr("href")));
                    record.setTitle(element.getElementById("A3").text());
                    record.setDepartment(element.getElementById("A5").text());
                    record.setProvince(page.meta("province"));
                    records.add(record);
                    CrawlDatum datum = new CrawlDatum();
                    datum.url(record.getContentUrl());
                    datum.type("detail");
                    datum.meta("province", "贵州");
                    next.add(datum);
                }

            }
            GsjIllegalApp.createBatch(records);

        }else if(page.crawlDatum().matchType("detail")){
            GsjIllegal record = new GsjIllegal();
            String content = page.select("div[id=content]").first().text();
            record.setContentUrl(page.url());
            record.setContent(content);
            GsjIllegalApp.update(page.url(),content );
        }
    }
    /**
     * 陕西解析
     * @param page
     * @param next
     */
    public void parserSXWest(Page page, CrawlDatums next){
        if(page.crawlDatum().matchType("list")) {
            List<GsjIllegal> records = new ArrayList<GsjIllegal>();
            //添加页连接
            Elements elements1 = page.select("div[class=page]").first().getElementsByClass("number");
            String total = elements1.get(1).text();
            String curPage = page.select("html").first().getElementById("currentPageNo").val();
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isTotalNum = pattern.matcher(total);
            Matcher iscurPageNum = pattern.matcher(curPage);
            if(isTotalNum.matches()&&iscurPageNum.matches()){
                int curPageInt = Integer.valueOf(curPage);
                int totalInt = Integer.valueOf(total);
                for(int i = curPageInt + 1 ; i <= totalInt; i++){
                    CrawlDatum datum = new CrawlDatum();
                    datum.url(String.format("http://sn.gsxt.gov.cn/xxcx.do?method=xxggList&random=1511949609569&xxlx=2&status=2&djjg=&entname=&xh=&glxh=&geetest_challenge=&geetest_validate=&geetest_seccode=&geetest_challenge=&geetest_validate=&geetest_seccode=&page.currentPageNo=%d", i));
                    datum.type("list");
                    datum.meta("province", "陕西");
                    datum.meta("detail",page.meta("detail"));
                    next.add(datum);
                }
            }
            Elements elements = page.select("ul[class=xxgg_list]").first().select("li");
//            JsonArray data = object.getAsJsonArray("data");
            for(Element element : elements) {

                GsjIllegal record = new GsjIllegal();
                String text = element.getElementsByClass("title").first().attr("onclick");
                text =text.replace("openDetail(","").replace("'","").replace(")","").replace(";","");
                String[] texts = text.split(",");
                record.setContentUrl(String.format(page.meta("detail"), texts[0], texts[1], texts[2], texts[3],curPage));
                record.setTitle(element.getElementsByClass("title").first().text());
                record.setDepartment(element.getElementsByClass("depar").first().text());
                record.setDate(element.getElementsByClass("date").first().text());
                record.setProvince(page.meta("province"));
                records.add(record);
                CrawlDatum datum = new CrawlDatum();
                datum.url(record.getContentUrl());
                datum.type("detail");
                datum.meta("province", "陕西");
                next.add(datum);
            }

            GsjIllegalApp.createBatch(records);



        }else if(page.crawlDatum().matchType("detail")){
            GsjIllegal record = new GsjIllegal();

            String content = null;
            if( page.select("div[class=part]").size() == 0){
                LOG.info("陕西 detail error");
                LOG.info(page.select("html").first().text());

            }else {
                content = page.select("div[class=part]").first().text();
            }

            record.setContentUrl(page.url());
            record.setContent(content);
            GsjIllegalApp.update(page.url(),content );
        }
    }

    /**
     * 浙江解析
     * @param page
     * @param next
     */
    public void parserZJ(Page page, CrawlDatums next){
        if(page.crawlDatum().matchType("list")) {
            List<GsjIllegal> records = new ArrayList<GsjIllegal>();

            JsonObject object = page.jsonObject();
            JsonArray data = object.getAsJsonArray("data");
            if(data != null&& data.size() > 0){
                LOG.info("号外，号外。。。浙江有严重违法新数据了！！！！！！！！！！！");
            }
            GsjIllegalApp.createBatch(records);

        }else if(page.crawlDatum().matchType("detail")){

        }
    }
    /**
     * 新疆解析
     * @param page
     * @param next
     */
    public void parserXJ(Page page, CrawlDatums next){
        if(page.crawlDatum().matchType("list")) {
            List<GsjIllegal> records = new ArrayList<GsjIllegal>();
            String content = page.select("div[class=noticelist]").text();
            if(content.indexOf("暂无严重违法失信企业名单列入公告") == -1){
                LOG.info("号外，号外。。。新疆有严重违法新数据了！！！！！！！！！！！");
            }
            GsjIllegalApp.createBatch(records);

        }else if(page.crawlDatum().matchType("detail")){

        }
    }
    /**
     * 辽宁解析
     * @param page
     * @param next
     */
    public void parserLN(Page page, CrawlDatums next){
        if(page.crawlDatum().matchType("list")) {
            List<GsjIllegal> records = new ArrayList<GsjIllegal>();
            String content = page.html();
            if(content.indexOf("严重违法失信企业名单列入公告") == -1){
                LOG.info("号外，号外。。。辽宁有严重违法新数据了！！！！！！！！！！！");
            }
            GsjIllegalApp.createBatch(records);

        }else if(page.crawlDatum().matchType("detail")){

        }
    }
    /**
     * 宁夏解析
     * @param page
     * @param next
     */
    public void parserNX(Page page, CrawlDatums next){
        if(page.crawlDatum().matchType("list")) {
            List<GsjIllegal> records = new ArrayList<GsjIllegal>();
            String conent = page.select("ul[class=news_li]").first().text();
            if(conent.indexOf("暂无严重违法失信企业名单列入公告") == -1){
                LOG.info("号外，号外。。。宁夏有严重违法新数据了！！！！！！！！！！！");
            }
            GsjIllegalApp.createBatch(records);

        }else if(page.crawlDatum().matchType("detail")){

        }
    }
    /**
     * 西藏解析
     * @param page
     * @param next
     */
    public void parserXZ(Page page, CrawlDatums next){
        if(page.crawlDatum().matchType("list")) {
            List<GsjIllegal> records = new ArrayList<GsjIllegal>();
            CrawlDatum datum = new CrawlDatum();
            String text = page.select("p").first().text();
            if( text.indexOf("暂无列入严重违法失信企业名单公告") == -1 ){
                LOG.info("号外，号外。。。西藏有严重违法新数据了！！！！！！！！！！！");
            }
            GsjIllegalApp.createBatch(records);

        }else if(page.crawlDatum().matchType("detail")){
        }
    }
    /**
     * 青海解析
     * @param page
     * @param next
     */
    public void parserQH(Page page, CrawlDatums next){
        if(page.crawlDatum().matchType("list")) {
            List<GsjIllegal> records = new ArrayList<GsjIllegal>();
            CrawlDatum datum = new CrawlDatum();
            String text = page.select("p").first().text();
            if( text.indexOf("暂无列入严重违法失信企业名单公告") == -1 ){
                LOG.info("号外，号外。。。青海有严重违法新数据了！！！！！！！！！！！");
            }
            GsjIllegalApp.createBatch(records);

        }else if(page.crawlDatum().matchType("detail")){
        }
    }
    /**
     * 云南解析
     * @param page
     * @param next
     */
    public void parserYN(Page page, CrawlDatums next){
        if(page.crawlDatum().matchType("list")) {
            List<GsjIllegal> records = new ArrayList<GsjIllegal>();

            JsonObject object = page.jsonObject();
            JsonObject result = object.getAsJsonObject("result");
            JsonArray data = result.getAsJsonArray("data");
            if(data != null&& data.size() > 0){
                LOG.info("号外，号外。。。云南有严重违法新数据了！！！！！！！！！！！");
            }
            GsjIllegalApp.createBatch(records);

        }else if(page.crawlDatum().matchType("detail")){

        }
    }

    /**
     * 天津
     * @param page
     * @param next
     */
    public void parserTJ(Page page, CrawlDatums next){
        if(page.crawlDatum().matchType("list")) {
            List<GsjIllegal> records = new ArrayList<GsjIllegal>();
            CrawlDatum datum = new CrawlDatum();
            JsonObject object = page.jsonObject();
            JsonArray data = object.getAsJsonArray("data");
            if(data != null&& data.size()>0 ){
                LOG.info("号外，号外。。。天津有严重违法新数据了！！！！！！！！！！！");
            }
        }else if(page.crawlDatum().matchType("detail")){
        }
    }
    /**
     * 北京
     * @param page
     * @param next
     */
    public void parserBJ(Page page, CrawlDatums next){
        if(page.crawlDatum().matchType("list")) {
            List<GsjIllegal> records = new ArrayList<GsjIllegal>();
            CrawlDatum datum = new CrawlDatum();
            int size = page.select("table[class=noticelist-t]").first().children().size();
            if( size > 0 ){
                LOG.info("号外，号外。。。北京有严重违法新数据了！！！！！！！！！！！");
            }
            GsjIllegalApp.createBatch(records);

        }else if(page.crawlDatum().matchType("detail")){
        }
    }
    /**
     * 重庆
     * @param page
     * @param next
     */
    public void parserCQ(Page page, CrawlDatums next){
        if(page.crawlDatum().matchType("list")) {
            List<GsjIllegal> records = new ArrayList<GsjIllegal>();
            CrawlDatum datum = new CrawlDatum();
            String content = page.select("table").text();
            if( content.indexOf("暂无严重违法失信企业名单列入公告") == -1 ){
                LOG.info("号外，号外。。。重庆有严重违法新数据了！！！！！！！！！！！");
            }
            GsjIllegalApp.createBatch(records);

        }else if(page.crawlDatum().matchType("detail")){
        }
    }
    /**
     * 广西
     * @param page
     * @param next
     */
    public void parserGX(Page page, CrawlDatums next){
        if(page.crawlDatum().matchType("list")) {
            List<GsjIllegal> records = new ArrayList<GsjIllegal>();
            CrawlDatum datum = new CrawlDatum();
            String text = page.select("p").first().text();
            if( text.indexOf("暂无严重违法失信企业名单列入公告") == -1 ){
                LOG.info("号外，号外。。。广西有严重违法新数据了！！！！！！！！！！！");
            }
            GsjIllegalApp.createBatch(records);

        }else if(page.crawlDatum().matchType("detail")){
        }
    }

    /**
     * 河北
     * @param page
     * @param next
     */
    public void parserHB(Page page, CrawlDatums next){
        if(page.crawlDatum().matchType("list")) {
            List<GsjIllegal> records = new ArrayList<GsjIllegal>();
            JsonObject object = page.jsonObject();
            JsonObject result = object.getAsJsonObject("result");
            JsonArray data = result.getAsJsonArray("data");
            if(data != null&& data.size()>0 ){
                LOG.info("号外，号外。。。河北有严重违法新数据了！！！！！！！！！！！");
            }

        }else if(page.crawlDatum().matchType("detail")){
        }
    }
    /**
     * 福建
     * @param page
     * @param next
     */
    public void parserFJ(Page page, CrawlDatums next){
        if(page.crawlDatum().matchType("list")) {
            List<GsjIllegal> records = new ArrayList<GsjIllegal>();
            JsonObject object = page.jsonObject();
            JsonObject result = object.getAsJsonObject("result");
            JsonArray data = result.getAsJsonArray("data");
            if(data != null&& data.size()>0 ){
                LOG.info("号外，号外。。。福建有严重违法新数据了！！！！！！！！！！！");
            }

        }else if(page.crawlDatum().matchType("detail")){
        }
    }
    /**
     * 山西
     * @param page
     * @param next
     */
    public void parserSX(Page page, CrawlDatums next){
        if(page.crawlDatum().matchType("list")) {
            List<GsjIllegal> records = new ArrayList<GsjIllegal>();
            String text = page.select("p").first().text();
            if( text.indexOf("暂无列入严重违法失信企业名单公告") == -1  ){
                LOG.info("号外，号外。。。山西有严重违法新数据了！！！！！！！！！！！");
            }
            GsjIllegalApp.createBatch(records);

        }else if(page.crawlDatum().matchType("detail")){
        }
    }/**
     * 安徽
     * @param page
     * @param next
     */
    public void parserAH(Page page, CrawlDatums next){
        if(page.crawlDatum().matchType("list")) {
            List<GsjIllegal> records = new ArrayList<GsjIllegal>();
            String text = page.select("p").first().text();
            if( text.indexOf("暂无列入严重违法失信企业名单公告") == -1  ){
                LOG.info("号外，号外。。。安徽有严重违法新数据了！！！！！！！！！！！");
            }
            GsjIllegalApp.createBatch(records);

        }else if(page.crawlDatum().matchType("detail")){
        }
    }
    /**
     * 吉林
     * @param page
     * @param next
     */
    public void parserJL(Page page, CrawlDatums next){
        if(page.crawlDatum().matchType("list")) {
            List<GsjIllegal> records = new ArrayList<GsjIllegal>();
            JsonObject object = page.jsonObject();
            JsonArray data = object.get("data").getAsJsonArray();
            if( data.size() > 0  ){
                LOG.info("号外，号外。。。吉林有严重违法新数据了！！！！！！！！！！！");
            }
            GsjIllegalApp.createBatch(records);

        }else if(page.crawlDatum().matchType("detail")){
        }
    }

    /**
     * 黑龙江
     * @param page
     * @param next
     */
    public void parserHLJ(Page page, CrawlDatums next){
        if(page.crawlDatum().matchType("list")) {
            List<GsjIllegal> records = new ArrayList<GsjIllegal>();
            String text = page.select("p").first().text();
            if( text.indexOf("暂无列入严重违法失信企业名单公告") == -1  ){
                LOG.info("号外，号外。。。黑龙江有严重违法新数据了！！！！！！！！！！！");
            }
            GsjIllegalApp.createBatch(records);

        }else if(page.crawlDatum().matchType("detail")){
        }
    }
    /**
     * 海南
     * @param page
     * @param next
     */
    public void parserHN(Page page, CrawlDatums next){
        if(page.crawlDatum().matchType("list")) {
            List<GsjIllegal> records = new ArrayList<GsjIllegal>();
            String text = page.select("p").first().text();
            if( text.indexOf("暂无列入严重违法失信企业名单公告") == -1  ){
                LOG.info("号外，号外。。。海南江有严重违法新数据了！！！！！！！！！！！");
            }
            GsjIllegalApp.createBatch(records);

        }else if(page.crawlDatum().matchType("detail")){
        }
    }
    /**
     * 湖北
     * @param page
     * @param next
     */
    public void parserHuB(Page page, CrawlDatums next){
        if(page.crawlDatum().matchType("list")) {
            List<GsjIllegal> records = new ArrayList<GsjIllegal>();
            String text = page.select("p").first().text();
            if( text.indexOf("暂无列入严重违法失信企业名单公告") == -1  ){
                LOG.info("号外，号外。。。湖北江有严重违法新数据了！！！！！！！！！！！");
            }
            GsjIllegalApp.createBatch(records);

        }else if(page.crawlDatum().matchType("detail")){
        }
    }
    /**
     * 河南
     * @param page
     * @param next
     */
    public void parserHeN(Page page, CrawlDatums next){
        if(page.crawlDatum().matchType("list")) {
            List<GsjIllegal> records = new ArrayList<GsjIllegal>();
            String text = page.select("p").first().text();
            if( text.indexOf("暂无列入严重违法失信企业名单公告") == -1  ){
                LOG.info("号外，号外。。。河南江有严重违法新数据了！！！！！！！！！！！");
            }
            GsjIllegalApp.createBatch(records);

        }else if(page.crawlDatum().matchType("detail")){
        }
    }
    public static void main(String[] args) throws Exception{
        GsjIllegalCrawler crawler = new GsjIllegalCrawler("crawl", true);
        crawler.setThreads(2);
        crawler.start(2);
    }
}
