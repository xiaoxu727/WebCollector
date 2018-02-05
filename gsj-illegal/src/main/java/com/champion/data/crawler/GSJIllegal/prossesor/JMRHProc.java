package com.champion.data.crawler.GSJIllegal.prossesor;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpClientPostRequest;
import cn.edu.hfut.dmic.webcollector.net.HttpClientRequest;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.net.Proxys;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import com.champion.data.crawler.GSJIllegal.model.*;
import com.champion.data.crawler.GSJIllegal.pipline.JMRHApp;
import com.champion.data.crawler.GSJIllegal.pipline.MongoApp;
import com.champion.data.crawler.GSJIllegal.util.PropertiesUtil;
import jdk.nashorn.internal.ir.CatchNode;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.jsoup.nodes.Element;

import org.jsoup.select.Elements;

import java.io.*;
import java.net.Proxy;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.SignStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by champion_xu on 2018/1/18.
 * http://www.jmrhip.com/
 * 中国军民融合
 */
public class JMRHProc extends BreadthCrawler {

    /**
     * 构造一个基于伯克利DB的爬虫
     * 伯克利DB文件夹为crawlPath，crawlPath中维护了历史URL等信息
     * 不同任务不要使用相同的crawlPath
     * 两个使用相同crawlPath的爬虫并行爬取会产生错误
     *
     * @param crawlPath 伯克利DB使用的文件夹
     * @param autoParse 是否根据设置的正则自动探测新URL
     */
    static String CATEGORY_URL = "http://so.iptrm.com/txnOneCategoryIndex.ajax?select-key:categoryWords=%s&select-key:express=%s&select-key:express2=&select-key:categoryIndex=%s&select-key:patentLib=&select-key:patentType=&select-key:categoryNum=15&select-key:categoryStart=0";
    static String PATENT_DATA_LIST_URL = "http://so.iptrm.com/txnPatentData01.ajax?secondKeyWord=%s&secondkeyWordVal=%s&secondSearchType=AND&express2=&express=%s&isFamily=&categoryIndex=%s&selectedCategory=&patentLib=&patentType=&order=&pdbt=&attribute-node:patent_cache-flag=false&attribute-node:patent_start-row=%d&attribute-node:patent_page-row=%d&attribute-node:patent_sort-column=ano&attribute-node:patent_page=%d";
    static String PATENT_DATA_LIST_KEYWORD_URL ="http://so.iptrm.com/txnPatentData01.ajax?secondKeyWord=%s&secondkeyWordVal=%s&secondSearchType=NOT&express2=&express=%s&isFamily=&categoryIndex=%s&selectedCategory=&patentLib=&patentType=&order=&pdbt=&attribute-node:patent_cache-flag=false&attribute-node:patent_start-row=%d&attribute-node:patent_page-row=%d&attribute-node:patent_sort-column=ano&attribute-node:patent_page=%d";
    static String PATENT_DETAIL_URL = "http://so.iptrm.com/app/patentdetail?isNewWindow=yes&pid=%s&patentType=&patentLib=&_sessionID=%s";
    static String PATENT_LAW_URL = "http://so.iptrm.com/app/lawdetail?isNewWindow=yes&pid=%s&patentType=patent2&_sessionID=%s";
    static HashMap<String,String> URL_MAP = new HashMap<String, String>();
    static {
        URL_MAP.put("keyword_category", CATEGORY_URL);
        URL_MAP.put("no_param_list", PATENT_DATA_LIST_URL);
        URL_MAP.put("no_param_list_detail", PATENT_DATA_LIST_URL);
        URL_MAP.put("keyword_list", PATENT_DATA_LIST_KEYWORD_URL);
        URL_MAP.put("keyword_list_detail", PATENT_DATA_LIST_KEYWORD_URL);
        URL_MAP.put("parent_detail", PATENT_DETAIL_URL);
        URL_MAP.put("law_detail", PATENT_LAW_URL);
    }
    static int MAX_RECORDS_SIZE = 100000;

    static int INTERVAL=1000;
    static String SECOND_KEY_WORD =PropertiesUtil.getPeroperty("SECOND_KEY_WORD");
    static String CATEGORY_WORDS =PropertiesUtil.getPeroperty("CATEGORY_WORDS");
    static String KEY_EXPRESS = PropertiesUtil.getPeroperty("KEY_EXPRESS");
    static String SECOND_KEY_WORD_DATE =PropertiesUtil.getPeroperty("SECOND_KEY_WORD_DATE");
    static String KEY_EXPRESS_DATE = PropertiesUtil.getPeroperty("KEY_EXPRESS_DATE");
    static HashMap<String, String> SECOND_KEY_MAP = new HashMap<String,String>();
    static Proxys proxies = new Proxys();
    static {
        SECOND_KEY_MAP.put("no_param_list", SECOND_KEY_WORD_DATE);
        SECOND_KEY_MAP.put("no_param_list_detail", SECOND_KEY_WORD_DATE);
        SECOND_KEY_MAP.put("keyword_list", SECOND_KEY_WORD);
        SECOND_KEY_MAP.put("keyword_list_detail", SECOND_KEY_WORD);
    }
    static HashMap<String, String> KEY_EXPRESS_MAP = new HashMap<String,String>();
    static {
        KEY_EXPRESS_MAP.put("no_param_list", KEY_EXPRESS_DATE);
        KEY_EXPRESS_MAP.put("no_param_list_detail", KEY_EXPRESS_DATE);
        KEY_EXPRESS_MAP.put("keyword_list", KEY_EXPRESS);
        KEY_EXPRESS_MAP.put("keyword_list_detail", KEY_EXPRESS);
        KEY_EXPRESS_MAP.put("keyword_category", KEY_EXPRESS);
    }

    static int PATENT_DATA_START = Integer.valueOf(PropertiesUtil.getPeroperty("jmnr_patent_data_start"));
    static int PATENT_DATA_STEP = Integer.valueOf(PropertiesUtil.getPeroperty("jmnr_patent_data_step"));
    static int PATENT_DATA_TOTAL = Integer.valueOf(PropertiesUtil.getPeroperty("jmnr_patent_data_total"));
    static String SESSION_ID = "sAeaZ3tZ6CKmbM68";
    static String CATEGORY_INDEX = PropertiesUtil.getPeroperty("jmnr_category_index");

    static HashMap<String, String> CATEGORY_INDEX_MAP = new HashMap<String,String>();
    static {
        CATEGORY_INDEX_MAP.put("no_param_list", "");
        CATEGORY_INDEX_MAP.put("no_param_list_detail", "");
        CATEGORY_INDEX_MAP.put("keyword_list", CATEGORY_INDEX);
        CATEGORY_INDEX_MAP.put("keyword_list_detail", CATEGORY_INDEX);
        CATEGORY_INDEX_MAP.put("keyword_category", CATEGORY_INDEX);
    }

    static String START_DATE = PropertiesUtil.getPeroperty("jmnr_start_date");
    static String END_DATE = PropertiesUtil.getPeroperty("jmnr_end_date");
    static boolean IS_PROXY = PropertiesUtil.getPeroperty("jmnr_is_proxy").equals("true");
    static String PROXY_KEY="proxy:Set:ProxySet";

    public JMRHProc(String crawlPath, boolean autoParse) throws IOException, ParseException {
        super(crawlPath, autoParse);

        getConf().setExecuteInterval(INTERVAL);
        if(IS_PROXY){
            proxies.addFromRedis(PROXY_KEY, threads);
        }

        String keywordPath = PropertiesUtil.getPeroperty("jmnr_keyword");
        String detailUrlPath = PropertiesUtil.getPeroperty("jmnr_detail_url");

        String typesStr = PropertiesUtil.getPeroperty("jmnr_type");

        String[]types = typesStr.split(";");
        String url = null;
        for(String type : types){
            if("keyword_list".equals(type)||"keyword_category".equals(type)||"keyword_list_detail".equals(type)){
                File keywordFile = new File(keywordPath);
                BufferedReader kewordReader = new BufferedReader(new FileReader(keywordFile));
                String keyword = null;
                while ( (keyword = kewordReader.readLine()) != null ){
                    if("keyword_list".equals(type)||"keyword_list_detail".equals(type)){
                        url = String.format(URL_MAP.get(type),UrlEncode(SECOND_KEY_MAP.get(type)),UrlEncode(keyword), UrlEncode(String.format(KEY_EXPRESS_MAP.get(type), keyword)),UrlEncode(CATEGORY_INDEX_MAP.get(type)), PATENT_DATA_START, PATENT_DATA_STEP,1);
                    }else if("keyword_category".equals(type))
                    {
                        url = String.format(URL_MAP.get(type), UrlEncode(CATEGORY_WORDS),UrlEncode(String.format(KEY_EXPRESS_MAP.get(type), keyword)),UrlEncode(CATEGORY_INDEX_MAP.get(type)));
                    }
                    addSeed(new CrawlDatum(url)
                            .type(type)
                            .meta("keyword",keyword)
                            .meta("page", 1)
                            .meta("step", PATENT_DATA_STEP)
                            .meta("start", 1));
                }
            }else if("parent_detail".equals(type)||"law_detail".equals(type)){
                File detailUrlFile = new File(keywordPath);
                BufferedReader detailUrlReader = new BufferedReader(new FileReader(detailUrlFile));
                String line = null;
                while ( (line =detailUrlReader.readLine()) != null ) {
                    addSeed(new CrawlDatum(line)
                            .type(type));
                }
            }else if("no_param_list".equals(type)||"no_param_list_detail".equals(type)){
                String keyword = null;
//                Date d=new Date();
                SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
                Date startDate = df.parse(START_DATE);
                Date endDate = df.parse(END_DATE);

                while (startDate.after(endDate) ){

                    keyword  = df.format(startDate);
                    url = String.format(URL_MAP.get(type),UrlEncode(SECOND_KEY_MAP.get(type)),UrlEncode(keyword), UrlEncode(String.format(KEY_EXPRESS_MAP.get(type), keyword)),UrlEncode(CATEGORY_INDEX_MAP.get(type)), PATENT_DATA_START, PATENT_DATA_STEP,1);
                    addSeed(new CrawlDatum(url)
                            .meta("keyword", keyword)
                            .meta("page", 1)
                            .meta("step", PATENT_DATA_STEP)
                            .meta("start", 1)
                            .type(type));
                    startDate = new Date(startDate.getTime() - 1 * 24 * 60 * 60 * 1000);
                }
            }
        }
    }
    @Override
    public Page getResponse(CrawlDatum crawlDatum) throws Exception {

        Proxy proxy = null;
        Page page = null;
        HttpClientRequest request = new HttpClientRequest(crawlDatum);
        if(IS_PROXY){
            if(proxies.size() == 0){
                proxies.addFromRedis(PROXY_KEY,threads);
            }
            proxy = proxies.pop();
            request.setProxy(proxy);
            page = request.responsePage();
            if(page != null){
                proxies.add(proxy);
            }
        }else {
            page = request.responsePage();
        }
        return page;
    }
    @Override
    public void visit(Page page, CrawlDatums next) throws DocumentException {
//        try {
            if (page.matchType("keyword_category")) {
                Elements records = page.select("record");
                List<JMRHCategory> categories = new ArrayList<JMRHCategory>();
                if (records.size() == 0) {
                    return;
                }
                for (Element record : records) {
                    JMRHCategory category = new JMRHCategory();
                    category.setCname(page.meta("keyword"));
                    if (record.select("key").size() > 0 && record.select("value").size() > 0) {
                        category.setType(record.select("key").first().text());
                        category.setValue(record.select("value").first().text());
                        categories.add(category);
                    }
                    {
                        categories.add(category);
                    }
                }
                JMRHApp.insertJMRHCategoryBatch(categories);
            } else {
                if (page.matchType("keyword_list") || page.matchType("no_param_list") || page.matchType("keyword_list_detail") || page.matchType("no_param_list_detail")) {
//            Elements patentNodes = page.select("patent");
//                    List<JMRHPatent> patents = new ArrayList<JMRHPatent>();
                    List<Object> patents = new ArrayList<Object>();
                    org.dom4j.Document doc = DocumentHelper.parseText(page.html());
                    org.dom4j.Element rootEle = doc.getRootElement();
                    List<org.dom4j.Element> patentNodes = rootEle.elements("patent");

                    for (org.dom4j.Element patentNode : patentNodes) {
                        JMRHPatent patent = new JMRHPatent();
                        if (page.meta().has("keyword")) {
                            patent.setKeyWord(page.meta("keyword"));
                        }
                        if (patentNode.element("linkPath") != null) {
                            patent.setLinkPath(patentNode.element("linkPath").getText());
                        }
                        if (patentNode.element("PID") != null) {
                            patent.setPID(patentNode.element("PID").getText());
                        }
                        if (patentNode.element("TIC") != null) {
                            patent.setTIC(patentNode.element("TIC").getText());
                        }
                        if (patentNode.element("TIE") != null) {
                            patent.setTIE(patentNode.element("TIE").getText());
                        }
                        if (patentNode.element("TIO") != null) {
                            patent.setTIO(patentNode.element("TIO").getText());
                        }
                        if (patentNode.element("INO") != null) {
                            patent.setINO(patentNode.element("INO").getText());
                        }
                        if (patentNode.element("INC") != null) {
                            patent.setINC(patentNode.element("INC").getText());
                        }
                        if (patentNode.element("INE") != null) {
                            patent.setINE(patentNode.element("INE").getText());
                        }
                        if (patentNode.element("ANO") != null) {
                            patent.setANO(patentNode.element("ANO").getText());
                        }
                        if (patentNode.element("AD") != null) {
                            patent.setAD(patentNode.element("AD").getText());
                        }
                        if (patentNode.element("PD") != null) {
                            patent.setPD(patentNode.element("PD").getText());
                        }
                        if (patentNode.element("PNO") != null) {
                            patent.setPNO(patentNode.element("PNO").getText());
                        }
                        if (patentNode.element("APO") != null) {
                            patent.setAPO(patentNode.element("APO").getText());
                        }
                        if (patentNode.element("APE") != null) {
                            patent.setAPE(patentNode.element("APE").getText());
                        }
                        if (patentNode.element("APC") != null) {
                            patent.setAPC(patentNode.element("APC").getText());
                        }
                        if (patentNode.element("IPCQ") != null) {
                            patent.setIPCQ(patentNode.element("IPCQ").getText());
                        }
                        if (patentNode.element("IPC") != null) {
                            patent.setIPC(patentNode.element("IPC").getText());
                        }
                        if (patentNode.element("PRNO") != null) {
                            patent.setPRNO(patentNode.element("PRNO").getText());
                        }
                        if (patentNode.element("PRN") != null) {
                            patent.setPRN(patentNode.element("PRN").getText());
                        }
                        if (patentNode.element("LC") != null) {
                            patent.setLC(patentNode.element("LC").getText());
                        }
                        if (patentNode.element("VU") != null) {
                            patent.setVU(patentNode.element("VU").getText());
                        }
                        if (patentNode.element("ABSO") != null) {
                            patent.setABSO(patentNode.element("ABSO").getText());
                        }
                        if (patentNode.element("ABSE") != null) {
                            patent.setABSE(patentNode.element("ABSE").getText());
                        }
                        if (patentNode.element("ABSC") != null) {
                            patent.setABSC(patentNode.element("ABSC").getText());
                        }
                        if (patentNode.element("EFC") != null) {
                            patent.setEFC(patentNode.element("EFC").getText());
                        }
                        if (patentNode.element("SFC") != null) {
                            patent.setSFC(patentNode.element("SFC").getText());
                        }
                        if (patentNode.element("IMGTITLE") != null) {
                            patent.setIMGTITLE(patentNode.element("IMGTITLE").getText());
                        }
                        if (patentNode.element("IMGNAME") != null) {
                            patent.setIMGNAME(patentNode.element("IMGNAME").getText());
                        }
                        if (patentNode.element("LSSC") != null) {
                            patent.setLSSC(patentNode.element("LSSC").getText());
                        }
                        if (patentNode.element("PDT") != null) {
                            patent.setPDT(patentNode.element("PDT").getText());
                        }
                        if (patentNode.element("PDTC") != null) {
                            patent.setPDTC(patentNode.element("PDTC").getText());
                        }
                        if (patentNode.element("MCLE") != null) {
                            patent.setMCLE(patentNode.element("MCLE").getText());
                        }
                        if (patentNode.element("CIGC") != null) {
                            patent.setCIGC(patentNode.element("CIGC").getText());
                        }
                        if (patentNode.element("TFO") != null) {
                            patent.setTFO(patentNode.element("TFO").getText());
                        }
                        if (patentNode.element("TBO") != null) {
                            patent.setTBO(patentNode.element("TBO").getText());
                        }
                        if (patentNode.element("ISO") != null) {
                            patent.setISO(patentNode.element("ISO").getText());
                        }
                        if (patentNode.element("SEO") != null) {
                            patent.setSEO(patentNode.element("SEO").getText());
                        }
                        if (patentNode.element("DDO") != null) {
                            patent.setDDO(patentNode.element("DDO").getText());
                        }
                        if (patentNode.element("CLO") != null) {
                            patent.setCLO(patentNode.element("CLO").getText());
                        }
                        if (patentNode.element("CLN") != null) {
                            patent.setCLN(patentNode.element("CLN").getText());
                        }
                        if (patentNode.element("DEBEC") != null) {
                            patent.setDEBEC(patentNode.element("DEBEC").getText());
                        }
                        if (patentNode.element("DEBEO") != null) {
                            patent.setDEBEO(patentNode.element("DEBEO").getText());
                        }
                        if (patentNode.element("DEBEE") != null) {
                            patent.setDEBEE(patentNode.element("DEBEE").getText());
                        }
                        if (patentNode.element("IMGO") != null) {
                            patent.setIMGO(patentNode.element("IMGO").getText());
                        }
                        if (patentNode.element("PDFEXIST") != null) {
                            patent.setPDFEXIST(patentNode.element("PDFEXIST").getText());
                        }
                        if (patentNode.element("PNS") != null) {
                            patent.setPNS(patentNode.element("PNS").getText());
                        }
                        if (patentNode.element("SFPNS") != null) {
                            patent.setSFPNS(patentNode.element("SFPNS").getText());
                        }
                        if (patentNode.element("ASO") != null) {
                            patent.setASO(patentNode.element("ASO").getText());
                        }
                        if (patentNode.element("ASC_S") != null) {
                            patent.setASC(patentNode.element("ASC").getText());
                        }
                        if (patentNode.element("ASE") != null) {
                            patent.setASE(patentNode.element("ASE").getText());
                        }
                        if (patentNode.element("DRAP") != null) {
                            patent.setDRAP(patentNode.element("DRAP").getText());
                        }
                        if (patentNode.element("eYears") != null) {
                            patent.seteYears(patentNode.element("eYears").getText());
                        }
                        if (patentNode.element("ABS_IMG") != null) {
                            patent.setABS_IMG(patentNode.element("ABS_IMG").getText());
                        }
                        if (patentNode.element("PT") != null) {
                            patent.setPT(patentNode.element("PT").getText());
                        }
                        if (patentNode.element("PERT") != null) {
                            patent.setPERT(patentNode.element("PERT").getText());
                        }
                        if (patentNode.element("CRN") != null) {
                            patent.setCRN(patentNode.element("CRN").getText());
                        }
                        if (patentNode.element("BAJDO") != null) {
                            patent.setBAJDO(patentNode.element("BAJDO").getText());
                        }
                        if (patentNode.element("PCYMD") != null) {
                            patent.setPCYMD(patentNode.element("PCYMD").getText());
                        }
                        if (patentNode.element("RLYMD") != null) {
                            patent.setRLYMD(patentNode.element("RLYMD").getText());
                        }
                        if (patentNode.element("TRO") != null) {
                            patent.setTRO(patentNode.element("TRO").getText());
                        }
                        if (patentNode.element("TRPO") != null) {
                            patent.setTRPO(patentNode.element("TRPO").getText());
                        }
                        if (patentNode.element("TT") != null) {
                            patent.setTT(patentNode.element("TT").getText());
                        }
                        if (patentNode.element("EYMD") != null) {
                            patent.setEYMD(patentNode.element("EYMD").getText());
                        }
                        if (patentNode.element("PASO") != null) {
                            patent.setPASO(patentNode.element("PASO").getText());
                        }
                        if (patentNode.element("AASO") != null) {
                            patent.setAASO(patentNode.element("AASO").getText());
                        }
                        if (patentNode.element("ASPO") != null) {
                            patent.setASPO(patentNode.element("ASPO").getText());
                        }
                        if (patentNode.element("ILSSC") != null) {
                            patent.setILSSC(patentNode.element("ILSSC").getText());
                        }
                        if (patentNode.element("ILSAYMD") != null) {
                            patent.setILSAYMD(patentNode.element("ILSAYMD").getText());
                        }
                        if (patentNode.element("LSBCN") != null) {
                            patent.setLSBCN(patentNode.element("LSBCN").getText());
                        }
                        if (patentNode.element("PPT") != null) {
                            patent.setPPT(patentNode.element("PPT").getText());
                        }
                        if (patentNode.element("PPRS") != null) {
                            patent.setPPRS(patentNode.element("PPRS").getText());
                        }
                        if (patentNode.element("PPEYMD") != null) {
                            patent.setPPEYMD(patentNode.element("PPEYMD").getText());
                        }
                        if (patentNode.element("PPYMD") != null) {
                            patent.setPPYMD(patentNode.element("PPYMD").getText());
                        }
                        if (patentNode.element("PPRYMD") != null) {
                            patent.setPPRYMD(patentNode.element("PPRYMD").getText());
                        }
                        if (patentNode.element("PPOO") != null) {
                            patent.setPPOO(patentNode.element("PPOO").getText());
                        }
                        if (patentNode.element("PDBC") != null) {
                            patent.setPDBC(patentNode.element("PDBC").getText());
                        }
                        if (patentNode.element("PPPO") != null) {
                            patent.setPPPO(patentNode.element("PPPO").getText());
                        }

                        patents.add(patent);
//                        if (patents.size() >= 100) {
//                            JMRHApp.insertJMRHPatentBatch(patents);
//                            patents.clear();
//                        }

                        if (page.matchType("keyword_list_detail") || page.matchType("no_param_list_detail")) {
                            String detailUrl = String.format(PATENT_DETAIL_URL, patent.getPID(), SESSION_ID);
                            String lawUrl = String.format(PATENT_LAW_URL, patent.getPID(), SESSION_ID);
                            CrawlDatum crawlDatum = new CrawlDatum(detailUrl).type("parent_detail");
                            CrawlDatum crawlDatum2 = new CrawlDatum(lawUrl).type("law_detail");
                            next.add(crawlDatum);
                            next.add(crawlDatum2);
                        }
                    }
//                    JMRHApp.insertJMRHPatentBatch(patents);
                    MongoApp.insertBatch(patents);
                    JMRHApp.insertJMRHFetchedPage(page, patentNodes.size());
                    if (page.matchType("keyword_list") || page.matchType("keyword_list_detail") || page.matchType("no_param_list") || page.matchType("no_param_list_detail")) {
                        if ("1".equals(rootEle.element("attribute-node").element("patent_start-row").getText())) {
                            int start = Integer.valueOf(rootEle.element("attribute-node").element("patent_page-row").getText()) + 1;
                            int total = Integer.valueOf(rootEle.element("attribute-node").element("patent_record-number").getText());
                            String type = page.crawlDatum().type();
                            String keyword = page.meta("keyword");
                            JMRHStatistic statistic = new JMRHStatistic();
                            statistic.setKeyword(keyword);
                            statistic.setTotal(String.valueOf(total));
                            JMRHApp.insertJMRHStatistic(statistic);
                            for(int page_num = Math.floorDiv(start, PATENT_DATA_STEP) + 1; start < total; start += PATENT_DATA_STEP, page_num++) {
                                String url = String.format(URL_MAP.get(type), UrlEncode(SECOND_KEY_MAP.get(type)), UrlEncode(keyword), UrlEncode(String.format(KEY_EXPRESS_MAP.get(type), keyword)), UrlEncode(CATEGORY_INDEX_MAP.get(type)), start, PATENT_DATA_STEP, page_num);
                                CrawlDatum crawlDatum = new CrawlDatum(url)
                                        .type(page.crawlDatum().type())
                                        .meta("keyword", keyword)
                                        .meta("page",page_num)
                                        .meta("step",PATENT_DATA_STEP)
                                        .meta("start",start);
                                next.add(crawlDatum);
                                if (start >= MAX_RECORDS_SIZE){
                                    start = MAX_RECORDS_SIZE - 1;
                                    page_num = Math.floorDiv(start, PATENT_DATA_STEP) + 1;
                                    int step = total - start + 1;
                                    url = String.format(URL_MAP.get(type), UrlEncode(SECOND_KEY_MAP.get(type)), UrlEncode(keyword), UrlEncode(String.format(KEY_EXPRESS_MAP.get(type), keyword)), UrlEncode(CATEGORY_INDEX_MAP.get(type)), start, step, page_num);
                                    CrawlDatum crawlDatum1 = new CrawlDatum(url)
                                            .type(page.crawlDatum().type())
                                            .meta("keyword", keyword);
                                    next.add(crawlDatum1);
                                    break;
                                }
                            }
                        }
                    }
                } else if (page.matchType("parent_detail")) {
                    JMRHPatentDetail patentDetail = new JMRHPatentDetail();
                    if (page.select("input[id=_id]") == null) {
                        return;
                    }
                    patentDetail.setPID(page.select("input[id=_id]").first().attr("value"));
                    patentDetail.setTitle(page.select("div[class=jsxq_tit]").first().text());
                    patentDetail.setLawStatus(page.select("div[class=jsclass]>div[class=floatl]").first().text());
                    patentDetail.setPatentType(page.select("div[class=jsclass]>div[class=floatl mgleft20]").first().text());
                    Elements trs = page.select("table[class=qwb_box_tab]>tbody>tr");
                    for (Element tr : trs) {
                        String th = tr.select("th").first().text();
                        String td = tr.select("td").first().text();
                        if ("申请号：".equals(th)) {
                            patentDetail.setApplyNo(td);
                        } else if ("公开(公告)号：".equals(td)) {
                            patentDetail.setPublicNo(td);
                        } else if ("申请日：".equals(th)) {
                            patentDetail.setApplyDate(td);
                        } else if ("公开(公告)日：".equals(th)) {
                            patentDetail.setPublicDate(td);
                        } else if ("申请人：".equals(th)) {
                            patentDetail.setApplyPerson(td);
                        } else if ("发明人：".equals(th)) {
                            patentDetail.setInventionPerson(td);
                        } else if ("申请人地址：".equals(th)) {
                            patentDetail.setApplyAddress(td);
                        } else if ("申请人区域代码：".equals(th)) {
                            patentDetail.setApplyAreaCode(td);
                        } else if ("专利权人：".equals(th)) {
                            patentDetail.setPatentee(td);
                        } else if ("洛迦诺分类：".equals(th)) {
                            patentDetail.setLC(td);
                        } else if ("IPC：".equals(th)) {
                            patentDetail.setIPC(td);
                        } else if ("CPC：".equals(th)) {
                            patentDetail.setCPC(td);
                        } else if ("优先权：".equals(th)) {
                            patentDetail.setPriority(td);
                        } else if ("专利代理机构：".equals(th)) {
                            patentDetail.setPatentAgency(td);
                        } else if ("代理人：".equals(th)) {
                            patentDetail.setAgent(td);
                        } else if ("审查员：".equals(th)) {
                            patentDetail.setInspector(td);
                        } else if ("国际申请：".equals(th)) {
                            patentDetail.setInternationalApplication(td);
                        } else if ("国际公开（公告）：".equals(th)) {
                            patentDetail.setInternationalPublic(td);
                        } else if ("进入国家日期：".equals(th)) {
                            patentDetail.setEnterDate(td);
                        } else if ("分案申请：".equals(th)) {
                            patentDetail.setDivisionApplication(td);
                        }

                    }
                    JMRHApp.insertJMRHPatentDetail(patentDetail);
                } else if (page.matchType("law_detail")) {
                    List<JMRHLawDetail> lawDetails = new ArrayList<JMRHLawDetail>();
                    if (page.select("input[id=_id]") == null ||  page.select("input[id=_id]").size()==0 ) {
                        return;
                    }
                    String pid = page.select("input[id=_id]").first().attr("value");

                    Elements trs = page.select("div[class=fvztzt]");
                    for (Element tr : trs) {
                        JMRHLawDetail lawDetail = new JMRHLawDetail();
                        Elements tds = tr.select("table>tbody>tr>td");
                        if (tds.size() >= 3) {
                            String publicDate = tds.first().text();
                            String lawStatus = tds.get(1).text();
                            String lawStatusInfo = tds.get(2).text();
                            lawDetail.setPID(pid);
                            lawDetail.setLawPublicDate(publicDate);
                            lawDetail.setLawStatus(lawStatus);
                            lawDetail.setLawStatusInfo(lawStatusInfo);
                        }
                        lawDetails.add(lawDetail);
                    }
                    JMRHApp.insertJMRHLawDetailBatch(lawDetails);
                }
            }
//        }catch (Exception e){
//            e.printStackTrace();
//            LOG.error("爬取失败"+ page.url());
//            JMRHErrorListUrl url = new JMRHErrorListUrl();
//            url.setUrl(page.url());
//            JMRHApp.insertJMRHErrorListUrl(url);
//        }
    }
    public String UrlEncode(String str)   {
       String strEcode = null;
        try{
            if(str != null){
                strEcode = URLEncoder.encode(str, "UTF-8");
            }
            
        } catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }finally {
            return  strEcode;
        }

    }

    public static void main(String[] args) throws Exception {
        JMRHProc crawler = new JMRHProc("JMRH", true);
        int threads = 1;
        if((threads = PropertiesUtil.getPeropertyAsInt("jmrh_threads")) == PropertiesUtil.ERROR_CODE){
            threads = 1;
        }
        crawler.resumable =false;
        crawler.setThreads(threads);
        crawler.start(100);
    }
}
