package com.champion.data.crawler.GSJIllegal.processor;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpClientRequest;
import cn.edu.hfut.dmic.webcollector.net.Proxys;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import com.champion.data.crawler.GSJIllegal.model.TYCCompanyBrief;
import com.champion.data.crawler.GSJIllegal.pipline.TYCApp;
import com.champion.data.crawler.GSJIllegal.util.Tools;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by champion_xu on 2018/2/6.
 */
public class TYCProc extends BreadthCrawler {

    static String PROXY_KEY = "proxy:Set:ProxySet";
    Proxys proxies = new Proxys(PROXY_KEY, 30);
    Proxys validProxies = new Proxys();
    static int MAX_DEPTH = 10;

    static boolean IS_PROXY = true;
    static String URL ="https://www.tianyancha.com/search?key=%s&searchType=human" ;
    /**
     * 构造一个基于伯克利DB的爬虫
     * 伯克利DB文件夹为crawlPath，crawlPath中维护了历史URL等信息
     * 不同任务不要使用相同的crawlPath
     * 两个使用相同crawlPath的爬虫并行爬取会产生错误
     *
     * @param crawlPath 伯克利DB使用的文件夹
     * @param autoParse 是否根据设置的正则自动探测新URL
     */

    public TYCProc(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        String keyword = "上海国际集团有限公司";
        addSeed(new CrawlDatum(String.format("https://www.tianyancha.com/search?key=%s&searchType=human",Tools.UrlEncode(keyword)))
                .meta("parent", keyword)
                .meta("root", keyword)
                .meta("depth", 0));
    }

    @Override
    public Page getResponse(CrawlDatum crawlDatum) throws Exception {
        Proxy proxy = null;
        Page page = null;
        HttpClientRequest request = new HttpClientRequest(crawlDatum);
        request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        request.setHeader("Accept-Encoding", "gzip, deflate, sdch, br");
        request.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        request.setHeader("Host", "www.tianyancha.com");
        request.setHeader("Upgrade-Insecure-Requests", "1");
        request.setHeader("Upgrade-Insecure-Requests", "1");
        if(IS_PROXY){
            if(validProxies.size() > 0 ){
                proxy = validProxies.pop();
            }else {
                proxy = proxies.pop();
            }
            request.setProxy(proxy);
            page = request.responsePage();
            if( page.select("div[class=mt10 pt20 b10 pb50 f14 b-c-white]>div[id=hideSearching]").size()>0 || page.select("div[class=b-c-white search_result_container]>div[class=search_result_single search-2017 pb25 pt25 pl30 pr30 ]").size() > 0){
                validProxies.add(proxy);
            }

        }else {
            page = request.responsePage();
        }
        return page;
    }
    @Override
    public void visit(Page page, CrawlDatums next) throws Exception {
        Elements bank = page.select("div[class=mt10 pt20 b10 pb50 f14 b-c-white]>div[id=hideSearching]");
        Elements expire= page.select("div[class=vipcontentBody vipMask]");
        if(bank.size() > 0|| expire.size()>0){
            return;
        }

        Elements companys = page.select("div[class=b-c-white search_result_container]>div[class=search_result_single search-2017 pb25 pt25 pl30 pr30 ]");
        List<TYCCompanyBrief> companyBriefList = new ArrayList<TYCCompanyBrief>();

        companys.get(0);
        for(Element company : companys){
            TYCCompanyBrief companyBrief = new TYCCompanyBrief();
            String cname = company.select("div[class=search_right_item ml10]>div>a").first().text();
            String detailUrl = company.select("div[class=search_right_item ml10]>div>a").first().attr("href");
            String status = "";
            String legalPerson = "";
            String regisCapital = "";
            String regisDate = "";
            String province = "";
            String score = "";
            String phone = "";
            String sholder = "";
            Elements statusEls = company.select("div[class=search_right_item ml10]>div>div[class=statusTypeNor in-block f12 in-block vertical-middle ml10 statusType1]");

            if(statusEls.size() > 0){
                status = statusEls.first().text();
            }
            Elements legalPersonEls = company.select("div[class=search_right_item ml10]>div[class=search_row_new pt20]>div>div[class=title overflow-width]>a");
            if(legalPersonEls.size() > 0){
                legalPerson = legalPersonEls.first().text();
            }
            Elements regisCapitalEls = company.select("div[class=search_right_item ml10]>div[class=search_row_new pt20]>div>div[class=title overflow-width]>span");
            if(regisCapitalEls.size() > 0 ){
                regisCapital = regisCapitalEls.first().text();
            }
            Elements regisDateEls = company.select("div[class=search_right_item ml10]>div[class=search_row_new pt20]>div>div[class=title overflow-width]>span");
            if(regisDateEls.size() > 0){
                regisDate = regisDateEls.get(1).text();
            }

            Elements provinceEls = company.select("div[class=search_right_item ml10]>div[class=search_row_new pt20]>div>div[class=in-block vertical-middle float-right lh-1em]>span[class=pr30]");
            if(provinceEls.size() > 0){
                province=  provinceEls.first().text();
            }

            Elements scoreEls =  company.select("div[class=search_right_item ml10]>div[class=search_row_new pt20]>div>div[class=in-block vertical-middle float-right lh-1em]>span[class=c9 f20]");
            if(scoreEls.size() > 0 ){
                score = scoreEls.first().text();
            }

            Elements phoneEls =  company.select("> div[class=search_right_item ml10]>div[class=search_row_new pt20]>div>div[class=add pb5]");
            if(phoneEls.size() > 0){
                phone = phoneEls.first().text();
            }
            Elements sholderEls = company.select("div[class=search_right_item ml10]>div[class=search_row_new pt20]>div>div[class=add]");
            if(sholderEls.size() > 0 ){
                sholder = sholderEls.first().text();
            }
            companyBrief.setParent(page.meta("parent"));
            companyBrief.setRoot(page.meta("root"));
            companyBrief.setDepth(page.metaAsInt("depth"));
            companyBrief.setCname(cname);
            companyBrief.setDetailUrl(detailUrl);
            companyBrief.setStatus(status);
            companyBrief.setRegisDate(regisDate);
            companyBrief.setLegalPerson(legalPerson);
            companyBrief.setRegisCaptial(regisCapital);
            companyBrief.setProvince(province);
            companyBrief.setScore(score);
            companyBrief.setPhone(phone);
            companyBrief.setSholder(sholder);
            companyBriefList.add(companyBrief);
            if(page.metaAsInt("depth") < MAX_DEPTH){
                CrawlDatum newDatum = new CrawlDatum (String.format(URL, Tools.UrlEncode(companyBrief.getCname())))
                        .meta("root",companyBrief.getRoot())
                        .meta("parent",companyBrief.getCname())
                        .meta("depth",page.metaAsInt("depth")+1);
                next.add(newDatum);
            }
        }
        TYCApp.insertTYCCompanyBriefBatch(companyBriefList);
        Elements pageEls = page.select("div[class=col-9 search-2017-2 pr15 pl0]>div[class=b-c-white clearfix position-rel]>div>div[class=search_pager human_pager in-block]>ul[class=pagination-sm pagination pt20]>li[class=pagination-page ng-scope]>a");
        for(Element pageEl : pageEls){
            CrawlDatum newDatum = new CrawlDatum (pageEl.attr("href"))
                    .meta("root",page.meta("root"))
                    .meta("parent",page.meta("parent"))
                    .meta("depth",page.metaAsInt("depth"));
            next.add(newDatum);
        }
    }


    public static void main(String[] args) throws Exception {
        TYCProc crawler = new TYCProc("tianyancha", true);
        int threads = 30;
//        if((threads = PropertiesUtil.getPeropertyTianyanchaProcAsInt("jmrh_threads")) == PropertiesUtil.ERROR_CODE){
//            threads = 1;
//        }
        crawler.resumable =true;
        crawler.setThreads(threads);
        crawler.start(100);
    }

}
