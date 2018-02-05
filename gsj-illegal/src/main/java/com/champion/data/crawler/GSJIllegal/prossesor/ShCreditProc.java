package com.champion.data.crawler.GSJIllegal.prossesor;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.util.PropertiesUtils;
import com.champion.data.crawler.GSJIllegal.model.*;
import com.champion.data.crawler.GSJIllegal.pipline.ShCreditApp;
import com.champion.data.crawler.GSJIllegal.util.PropertiesUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by champion_xu on 2018/1/17.
 */
public class ShCreditProc extends BreadthCrawler {

    /**
     * 构造一个基于伯克利DB的爬虫
     * 伯克利DB文件夹为crawlPath，crawlPath中维护了历史URL等信息
     * 不同任务不要使用相同的crawlPath
     * 两个使用相同crawlPath的爬虫并行爬取会产生错误
     *
     * @param crawlPath 伯克利DB使用的文件夹
     * @param autoParse 是否根据设置的正则自动探测新URL
     */
    static String CREDIT_CODE_URL = "http://www.shcredit.gov.cn/credit/f/credit/query/?model=tyshxydm&page=%d&keywords=%s";
    static String KEEP_WORD_URL = "http://www.shcredit.gov.cn/credit/f/credit/query/?model=sxmdcx&page=%d&keywords=%s";
    static String FOCUS_URL = "http://www.shcredit.gov.cn/credit/f/credit/query/?model=zdgzmd&page=%d&keywords=%s";
    static String BROKEN_PROMISES_URL = "http://www.shcredit.gov.cn/credit/f/credit/query/?model=sxmdc&page=%d&keywords=%s";
    static String CUSTOMS_CERTIFICATE_URL = "http://www.shcredit.gov.cn/credit/f/credit/query/?model=hggjqyrzmdcx&page=%d&keywords=%s";
    static String TAX_A_URL = "http://www.shcredit.gov.cn/credit/f/credit/query/?model=swajnsrmd&page=%d&keywords=%s";
    static String EXCEPTION_BUSINESS_URL = "http://www.shcredit.gov.cn/credit/f/credit/query/?model=ycjyml&page=%d&keywords=%s";
    static String ILLEGAL_FOUNDS_RAISE_URL = "http://www.shcredit.gov.cn/credit/f/credit/query/?model=ffjjyjmd&page=%d&keywords=%s";
    static String EXECUTED_BROKEN_PROMISES_URL = "http://www.shcredit.gov.cn/credit/f/credit/query/?model=sxbzxr&page=%d&keywords=%s";
    static String ILLEGAL_TAX_URL = "http://www.shcredit.gov.cn/credit/f/credit/query/?model=zdsswfaj&page=%d&keywords=%s";
    static String GOV_ILLEGAL_URL = "http://www.shcredit.gov.cn/credit/f/credit/query/?model=zfcgyzwfsxmd&page=%d&keywords=%s";

    static HashMap MAP = new HashMap<String, String >();
    static {
        MAP.put("creditCode", CREDIT_CODE_URL);
        MAP.put("keepWord", KEEP_WORD_URL);
        MAP.put("focus", FOCUS_URL);
        MAP.put("brokenPromises", BROKEN_PROMISES_URL);
        MAP.put("customsCertificate", CUSTOMS_CERTIFICATE_URL);
        MAP.put("taxA", TAX_A_URL);
        MAP.put("exceptionBusiness", EXCEPTION_BUSINESS_URL);
        MAP.put("illegalFoundsRaise", ILLEGAL_FOUNDS_RAISE_URL);
        MAP.put("executedBrokenPromises", EXECUTED_BROKEN_PROMISES_URL);
        MAP.put("illegalTax", ILLEGAL_TAX_URL);
        MAP.put("govIllegal", GOV_ILLEGAL_URL);
    }
    public ShCreditProc(String crawlPath, boolean autoParse) throws IOException {
        super(crawlPath, autoParse);
        String keywordPath = PropertiesUtil.getPeroperty("sh_credit_keyword");
        String typesStr = PropertiesUtil.getPeroperty("sh_credit_types");
        String[] types = null;
        if(typesStr != null){
            types = typesStr.split(";");
        }
        File file = new File(keywordPath);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        while ( (line = reader.readLine()) != null ){
            if(types == null||types.length==0){
                break;
            }
            for(String type : types){
                String url = String.format(MAP.get(type).toString(), 1, URLEncoder.encode(line,"UTF-8"));
                addSeed(new CrawlDatum(url)
                        .meta("keyword", line)
                        .type(type));
            }
        }
    }
    @Override
    public void visit(Page page, CrawlDatums next) throws UnsupportedEncodingException {
        String type = page.crawlDatum().type();
        if("creditCode".equals(type)){
            Elements trs = page.select("div[class=creditcode-table]>table[class=table]>tbody>tr");
            List<ShCreditCode> creditCodeList = new ArrayList<ShCreditCode>();
            for(Element tr : trs){
                ShCreditCode creditCode = new ShCreditCode();
                Elements tds = tr.select("td");
                if(tds.size()>3){
                    creditCode.setCname(tds.first().text());
                    creditCode.setCreditCode(tds.get(1).text());
                    creditCode.setRegisCode(tds.get(2).text());
                    creditCode.setOrgCode(tds.get(3).text());
                    creditCode.setTaxCdoe(tds.get(4).text());
                    creditCodeList.add(creditCode);
                }
            }
            ShCreditApp.insertShCreditCodeBatch(creditCodeList);
            Elements lis = page.select("ul[class=pagination]>li");
            if(lis.size()>1&&lis.first().hasClass("active")){
                for(int i = 2; i <= lis.size()-1 ; i++){
                    String url = String.format(CREDIT_CODE_URL,i,URLEncoder.encode(page.crawlDatum().meta("keyword"),"UTF-8"));
                    next.add(new CrawlDatum(url).type("creditCode")
                            .meta("keyword", page.crawlDatum().meta("keyword")));
                }
            }
        }else if("keepWord".equals(type)){

            Elements trs = page.select("div[class=creditcode-table]>table[class=table]>tbody>tr");
            List<ShCreditKeepWord> shCreditKeepWords = new ArrayList<ShCreditKeepWord>();
            for(Element tr : trs){
                ShCreditKeepWord keepWord = new ShCreditKeepWord();
                Elements tds = tr.select("td");
                if(tds.size()>=3){
                    keepWord.setCname(tds.first().text());
                    keepWord.setCreditCode(tds.get(1).text());
                    keepWord.setKeepWordCount(tds.get(2).text());
                    shCreditKeepWords.add(keepWord);
                }

            }
            ShCreditApp.insertShCreditKeepWordBatch(shCreditKeepWords);
            Elements lis = page.select("ul[class=pagination]>li");
            if(lis.size()>1&&lis.first().hasClass("active")){
                for(int i = 2; i <= lis.size()-1 ; i++){
                    String url = String.format(MAP.get(type).toString(),i,URLEncoder.encode(page.crawlDatum().meta("keyword"),"UTF-8"));
                    next.add(new CrawlDatum(url).type("keepWord")
                            .meta("keyword", page.crawlDatum().meta("keyword")));
                }
            }

        }else if("focus".equals(type)){
            Elements trs = page.select("div[class=creditcode-table]>table[class=table]>tbody>tr");
            List<ShCreditFocus> shCreditFoucs = new ArrayList<ShCreditFocus>();
            for(Element tr : trs){
                ShCreditFocus creditFoucs = new ShCreditFocus();
                Elements tds = tr.select("td");
                if(tds.size()>=3){
                    creditFoucs.setCname(tds.first().text());
                    creditFoucs.setCreditCode(tds.get(1).text());
                    creditFoucs.setTypeCount(tds.get(2).text());
                    shCreditFoucs.add(creditFoucs);
                }

            }
            ShCreditApp.insertShCreditFocusBatch(shCreditFoucs);
            Elements lis = page.select("ul[class=pagination]>li");
            if(lis.size()>1&&lis.first().hasClass("active")){
                for(int i = 2; i <= lis.size()-1 ; i++){
                    String url = String.format(MAP.get(type).toString(),i,URLEncoder.encode(page.crawlDatum().meta("keyword")));
                    next.add(new CrawlDatum(url).type("focus")
                            .meta("keyword", page.crawlDatum().meta("keyword")));
                }
            }

        }else if("brokenPromises".equals(type)){
            Elements trs = page.select("div[class=creditcode-table]>table[class=table]>tbody>tr");
            List<ShCreditBrokenPromises> shCreditBrokenPromiseses = new ArrayList<ShCreditBrokenPromises>();
            for(Element tr : trs){
                ShCreditBrokenPromises creditBrokenPromises = new ShCreditBrokenPromises();
                Elements tds = tr.select("td");
                if(tds.size()>=3){
                    creditBrokenPromises.setCname(tds.first().text());
                    creditBrokenPromises.setCreditCode(tds.get(1).text());
                    creditBrokenPromises.setTypeCount(tds.get(2).text());
                    shCreditBrokenPromiseses.add(creditBrokenPromises);
                }
            }
            ShCreditApp.insertShCreditBrokenPromisesBatch(shCreditBrokenPromiseses);
            Elements lis = page.select("ul[class=pagination]>li");
            if(lis.size()>1&&lis.first().hasClass("active")){
                for(int i = 2; i <= lis.size()-1 ; i++){
                    String url = String.format(MAP.get(type).toString(),i,URLEncoder.encode(page.crawlDatum().meta("keyword")));
                    next.add(new CrawlDatum(url).type("brokenPromises")
                            .meta("keyword", page.crawlDatum().meta("keyword")));
                }
            }

        }else if("customsCertificate".equals(type)){

            Elements trs = page.select("div[class=creditcode-table]>table[class=table]>tbody>tr");
            List<ShCreditCustomsCertificate> creditCustomsCertificates = new ArrayList<ShCreditCustomsCertificate>();
            for(Element tr : trs){
                ShCreditCustomsCertificate creditCustomsCertificate = new ShCreditCustomsCertificate();
                Elements tds = tr.select("td");
                if(tds.size()>=3){
                    creditCustomsCertificate.setCname(tds.first().text());
                    creditCustomsCertificate.setCreditCode(tds.get(1).text());
                    creditCustomsCertificate.setLevelDate(tds.get(2).text());
                    creditCustomsCertificates.add(creditCustomsCertificate);
                }
            }
            ShCreditApp.insertShCreditCustomsCertificateBatch(creditCustomsCertificates);
            Elements lis = page.select("ul[class=pagination]>li");
            if(lis.size()>1&&lis.first().hasClass("active")){
                for(int i = 2; i <= lis.size()-1 ; i++){
                    String url = String.format(MAP.get(type).toString(),i,URLEncoder.encode(page.crawlDatum().meta("keyword")));
                    next.add(new CrawlDatum(url).type("customsCertificate")
                            .meta("keyword", page.crawlDatum().meta("keyword")));
                }
            }

        }else if("taxA".equals(type)){

            Elements trs = page.select("div[class=creditcode-table]>table[class=table]>tbody>tr");
            List<ShCreditTaxA> creditTaxAs = new ArrayList<ShCreditTaxA>();
            for(Element tr : trs){
                ShCreditTaxA creditTaxA = new ShCreditTaxA();
                Elements tds = tr.select("td");
                if(tds.size()>=3){
                    creditTaxA.setCname(tds.first().text());
                    creditTaxA.setCreditCode(tds.get(1).text());
                    creditTaxA.setYear(tds.get(2).text());
                    creditTaxAs.add(creditTaxA);
                }
            }
            ShCreditApp.insertShCreditTaxABatch(creditTaxAs);
            Elements lis = page.select("ul[class=pagination]>li");
            if(lis.size()>1&&lis.first().hasClass("active")){
                for(int i = 2; i <= lis.size()-1 ; i++){
                    String url = String.format(MAP.get(type).toString(),i,URLEncoder.encode(page.crawlDatum().meta("keyword")));
                    next.add(new CrawlDatum(url).type("taxA")
                            .meta("keyword", page.crawlDatum().meta("keyword")));
                }
            }

        }else if("exceptionBusiness".equals(type)){
            Elements trs = page.select("div[class=creditcode-table]>table[class=table]>tbody>tr");
            List<ShCreditExceptionBusiness> creditExceptionBusinesses = new ArrayList<ShCreditExceptionBusiness>();
            for(Element tr : trs){
                ShCreditExceptionBusiness creditExceptionBusiness = new ShCreditExceptionBusiness();
                Elements tds = tr.select("td");
                if(tds.size()>=3){
                    creditExceptionBusiness.setCname(tds.first().text());
                    creditExceptionBusiness.setCreditCode(tds.get(1).text());
                    creditExceptionBusiness.setInTime(tds.get(2).text());
                    creditExceptionBusinesses.add(creditExceptionBusiness);
                }
            }
            ShCreditApp.insertShCreditExceptionBusinessBatch(creditExceptionBusinesses);
            Elements lis = page.select("ul[class=pagination]>li");
            if(lis.size()>1&&lis.first().hasClass("active")){
                for(int i = 2; i <= lis.size()-1 ; i++){
                    String url = String.format(MAP.get(type).toString(),i,URLEncoder.encode(page.crawlDatum().meta("keyword")));
                    next.add(new CrawlDatum(url).type("exceptionBusiness")
                            .meta("keyword", page.crawlDatum().meta("keyword")));
                }
            }

        }else if("illegalFoundsRaise".equals(type)){
            Elements trs = page.select("div[class=creditcode-table]>table[class=table]>tbody>tr");
            List<ShCreditIllegalFoundsRaise> creditIllegalFoundsRaises = new ArrayList<ShCreditIllegalFoundsRaise>();
            for(Element tr : trs){
                ShCreditIllegalFoundsRaise creditIllegalFoundsRaise = new ShCreditIllegalFoundsRaise();
                Elements tds = tr.select("td");
                if(tds.size()>=3){
                    creditIllegalFoundsRaise.setCname(tds.first().text());
                    creditIllegalFoundsRaise.setCreditCode(tds.get(1).text());
                    creditIllegalFoundsRaise.setProvince(tds.get(2).text());
                    creditIllegalFoundsRaises.add(creditIllegalFoundsRaise);
                }
            }
            ShCreditApp.insertShCreditIllegalFoundsRaiseBatch(creditIllegalFoundsRaises);
            Elements lis = page.select("ul[class=pagination]>li");
            if(lis.size()>1&&lis.first().hasClass("active")){
                for(int i = 2; i <= lis.size()-1 ; i++){
                    String url = String.format(MAP.get(type).toString(),i,URLEncoder.encode(page.crawlDatum().meta("keyword")));
                    next.add(new CrawlDatum(url).type("illegalFoundsRaise")
                            .meta("keyword", page.crawlDatum().meta("keyword")));
                }
            }
        }else if("executedBrokenPromises".equals(type)){
            Elements trs = page.select("div[class=creditcode-table]>table[class=table]>tbody>tr");
            List<ShCreditExecutedBrokenPromises> creditExecutedBrokenPromiseses = new ArrayList<ShCreditExecutedBrokenPromises>();
            for(Element tr : trs){
                ShCreditExecutedBrokenPromises creditExecutedBrokenPromises = new ShCreditExecutedBrokenPromises();
                Elements tds = tr.select("td");
                if(tds.size()>=3){
                    creditExecutedBrokenPromises.setCname(tds.first().text());
                    creditExecutedBrokenPromises.setCreditCode(tds.get(1).text());
                    creditExecutedBrokenPromises.setCaseNO(tds.get(2).text());
                    creditExecutedBrokenPromiseses.add(creditExecutedBrokenPromises);
                }
            }
            ShCreditApp.insertShCreditExecutedBrokenPromisesBatch(creditExecutedBrokenPromiseses);
            Elements lis = page.select("ul[class=pagination]>li");
            if(lis.size()>1&&lis.first().hasClass("active")){
                for(int i = 2; i <= lis.size()-1 ; i++){
                    String url = String.format(MAP.get(type).toString(),i,URLEncoder.encode(page.crawlDatum().meta("keyword")));
                    next.add(new CrawlDatum(url).type("executedBrokenPromises")
                            .meta("keyword", page.crawlDatum().meta("keyword")));
                }
            }

        }else if("illegalTax".equals(type)){
            Elements trs = page.select("div[class=creditcode-table]>table[class=table]>tbody>tr");
            List<ShCreditIllegalTax> creditIllegalTaxes = new ArrayList<ShCreditIllegalTax>();
            for(Element tr : trs){
                ShCreditIllegalTax creditIllegalTax = new ShCreditIllegalTax();
                Elements tds = tr.select("td");
                if(tds.size()>=3){
                    creditIllegalTax.setCname(tds.first().text());
                    creditIllegalTax.setCreditCode(tds.get(1).text());
                    creditIllegalTax.setCaseStatus(tds.get(2).text());
                    creditIllegalTaxes.add(creditIllegalTax);
                }
            }
            ShCreditApp.insertShCreditIllegalTaxBatch(creditIllegalTaxes);
            Elements lis = page.select("ul[class=pagination]>li");
            if(lis.size()>1&&lis.first().hasClass("active")){
                for(int i = 2; i <= lis.size()-1 ; i++){
                    String url = String.format(MAP.get(type).toString(),i,URLEncoder.encode(page.crawlDatum().meta("keyword")));
                    next.add(new CrawlDatum(url).type("illegalTax")
                            .meta("keyword", page.crawlDatum().meta("keyword")));
                }
            }

        }else if("govIllegal".equals(type)){
            Elements trs = page.select("div[class=creditcode-table]>table[class=table]>tbody>tr");
            List<ShCreditGovIllegal> creditGovIllegals = new ArrayList<ShCreditGovIllegal>();
            for(Element tr : trs){
                ShCreditGovIllegal creditGovIllegal = new ShCreditGovIllegal();
                Elements tds = tr.select("td");
                if(tds.size()>=3){
                    creditGovIllegal.setCname(tds.first().text());
                    creditGovIllegal.setCreditCode(tds.get(1).text());
                    creditGovIllegal.setInReason(tds.get(2).text());
                    creditGovIllegals.add(creditGovIllegal);
                }
            }
            ShCreditApp.insertShCreditGovIllegalBatch(creditGovIllegals);
            Elements lis = page.select("ul[class=pagination]>li");
            if(lis.size()>1&&lis.first().hasClass("active")){
                for(int i = 2; i <= lis.size()-1 ; i++){
                    String url = String.format(MAP.get(type).toString(),i,URLEncoder.encode(page.crawlDatum().meta("keyword")));
                    next.add(new CrawlDatum(url).type("govIllegal")
                            .meta("keyword", page.crawlDatum().meta("keyword")));
                }
            }
        }
    }
    public static void main(String[] args) throws Exception{
        ShCreditProc crawler = new ShCreditProc("SHCreditCode", true);
        int threads = 1;
        if((threads = PropertiesUtil.getPeropertyAsInt("sh_credit_threads")) == PropertiesUtil.ERROR_CODE){
            threads = 1;
        }
        crawler.resumable=true;
        crawler.setThreads(threads);
        crawler.start(100);
    }
}
