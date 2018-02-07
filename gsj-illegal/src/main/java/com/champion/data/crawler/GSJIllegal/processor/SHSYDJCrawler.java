package com.champion.data.crawler.GSJIllegal.processor;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpClientPostRequest;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.champion.data.crawler.GSJIllegal.model.DepartmentInfo;
import com.champion.data.crawler.GSJIllegal.model.DepartmentList;
import com.champion.data.crawler.GSJIllegal.model.DepartmentReportInfo;
import com.champion.data.crawler.GSJIllegal.model.DepartmentReportList;
import com.champion.data.crawler.GSJIllegal.pipline.DepartmentApp;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by champion_xu on 2018/1/10.
 * 上海事业单位爬虫
 */
public class SHSYDJCrawler extends BreadthCrawler {
    static String PARAM="pageIndex=%d&keyword=%s&type=12&geo_code=";
    static String URL = "http://www.sydjsh.cn/search.do";
    static String HOME_URL = "http://www.sydjsh.cn";

    public SHSYDJCrawler(String crawlPath, boolean autoParse) throws UnsupportedEncodingException {
        super(crawlPath, autoParse);
//            addSeed(new CrawlDatum(URL)
//                    .meta("method", "POST")
//                    .type("list")
//                    .meta("keyword", URLEncoder.encode("上", "UTF-8"))
//                    .meta("param", String.format(PARAM, 1, URLEncoder.encode("上", "UTF-8"))));

        String[] keywords={"上","部","厂","场","城","池","处","地","店","队","宫","馆","会","界","局","社","室","署","司","所","台","堂","厅","团","校","心","学","学","寓","园","苑","院","站","中","舟"};
        for(String keyword: keywords){
            String param = String.format(PARAM, 1, URLEncoder.encode(keyword, "UTF-8"));
            addSeed(new CrawlDatum(URL+"?"+param)
                    .meta("method", "POST")
                    .type("list")
                    .meta("keyword", URLEncoder.encode(keyword, "UTF-8"))
                    .meta("param", param));
        }
//        String[] urls = {"http://www.sydjsh.cn/ndbgDetail.do?unit_id=13733&ndbg_id=2CAC77C8CA5119B6E050A8C0503C7782&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=13733&ndbg_id=0E4D7ABAA995CF56E050A8C0503C3A73&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=3660&ndbg_id=4AE3391D7DCB0B62E050A8C0503C7839&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=3660&ndbg_id=2D1AA3607878A9CCE050A8C0503C519C&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=3660&ndbg_id=10807D926C5F8DC4E050A8C0503C6E7A&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=4258&ndbg_id=29D3AB89E3D0F199E050A8C0503C1B5E&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=4258&ndbg_id=0D1154F448A7C5F5E050A8C0503C0681&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=7236&ndbg_id=48B871647C6C8F85E050A8C0503C49CB&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=7236&ndbg_id=2ED6DF0509F4AE0AE050A8C0503C5A4E&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=7236&ndbg_id=10D9D351A7A4863EE050A8C0503C3807&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=7304&ndbg_id=115F0ED6228DC525E050A8C0503C5FA3&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=7430&ndbg_id=4985F47F7CE51E54E050A8C0503C10B8&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=7430&ndbg_id=2D830E10B5F3CE6EE050A8C0503C3BAF&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=7430&ndbg_id=0E4377585E849486E050A8C0503C39DD&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=7443&ndbg_id=4620F550D09CD59EE050A8C0503C2411&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=7443&ndbg_id=2D83D1BE387D0268E050A8C0503C3BFD&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=7443&ndbg_id=1206C3F0FCE714DBE050A8C0503C5B50&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=7468&ndbg_id=29D3672DC2B48C0EE050A8C0503C19D2&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=7468&ndbg_id=0D88433686F1009EE050A8C0503C1371&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=7695&ndbg_id=108071D69E714D83E050A8C0503C6E64&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=7889&ndbg_id=2D82E0BC2695EB5DE050A8C0503C3C87&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=7889&ndbg_id=1100DCEF2766B6CFE050A8C0503C149C&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=8044&ndbg_id=2AF2800D26E56BF1E050A8C0503C6CA3&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=8044&ndbg_id=10096980B169C52EE050A8C0503C7FB3&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=8179&ndbg_id=2AFC27C810783E66E050A8C0503C6BFB&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=8179&ndbg_id=110125D2DAA4F804E050A8C0503C149A&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=8318&ndbg_id=119C4C12F750C7F5E050A8C0503C725C&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=9516&ndbg_id=4AE336DA92B51C11E050A8C0503C783B&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=9516&ndbg_id=33A0DEDBDE50A4A5E050A8C0503C1FE4&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=9516&ndbg_id=119BA02C33365401E050A8C0503C6F4E&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=9539&ndbg_id=2C5CDF6BBDF13F8AE050A8C0503C46AB&type=12",
//                "http://www.sydjsh.cn/ndbgDetail.do?unit_id=9539&ndbg_id=105AFBDB8D89C073E050A8C0503C2BF6&type=12"};
//        for(String url : urls ){
//            CrawlDatum crawlDatum = new CrawlDatum(url);
//            crawlDatum.type("reportDetail");
//            addSeed(crawlDatum);
//        }

//        addSeed(new CrawlDatum("http://www.sydjsh.cn/searchDetail.do?unit_id=319&type=12")
////                    .meta("method", "POST")
//                    .type("detail"));
//                    .meta("keyword", URLEncoder.encode("上", "UTF-8"))
//                    .meta("param", String.format(PARAM, 1, URLEncoder.encode("上", "UTF-8"))));
//    addSeed(new CrawlDatum("http://www.sydjsh.cn/ndbgDetail.do?unit_id=319&ndbg_id=46D166BACED9180AE050A8C0503C37B2&type=12")
////                    .meta("method", "POST")
//                    .type("reportDetail"));
//                    .meta("keyword", URLEncoder.encode("上", "UTF-8"))
//                    .meta("param", String.format(PARAM, 1, URLEncoder.encode("上", "UTF-8"))));

    }
    @Override
    public Page getResponse(CrawlDatum crawlDatum) throws Exception {
        if("list".equals(crawlDatum.type())){
            crawlDatum.url(URL);
        }
        HttpRequest request = new HttpClientPostRequest(crawlDatum);
        return request.responsePage();
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        if("list".equals(page.crawlDatum().type())){
            Elements els = page.select("form[id=pageForm]");
            if(els.size()>0){
                Element ele = els.first();
                String pageContent = ele.text();
                String toalPageStr = pageContent.substring(pageContent.indexOf("/")+1,pageContent.indexOf('首')).replace(" ", "");
                Integer toalPage = Integer.valueOf(toalPageStr);
                String currentPageStr= pageContent.substring(pageContent.indexOf("前")+1,pageContent.indexOf('/')).trim();
                Integer currentPage = Integer.valueOf(currentPageStr);

                for(int i = currentPage; i <= toalPage; i++){
                    String keyword = page.crawlDatum().meta("keyword");
                    String param = String.format(PARAM, i, keyword );
                    CrawlDatum crawlDatum = new CrawlDatum(page.url()+"?"+param);
                    crawlDatum.meta("method", "POST")
                            .type("list")
                            .meta("keyword", keyword)
                            .meta("param", param);
                    next.add(crawlDatum);
                }
            }
            Elements elements = page.select("tr[class=cursor]");
            List<DepartmentList> departmentListList = new ArrayList<DepartmentList>();
            for(Element ele :elements){
                DepartmentList departmentList = new DepartmentList();
                String cname = ele.select("td").first().text();
                String detail = HOME_URL + "/" + ele.select("td>a").attr("href");
                CrawlDatum crawlDatum = new CrawlDatum(detail);
                crawlDatum.type("detail");
                next.add(crawlDatum);
                departmentList.setCname(cname);
                departmentList.setDetailUrl(detail);
                departmentListList.add(departmentList);
            }
            DepartmentApp.insertDepartmentListBatch(departmentListList);

        }else if("detail".equals(page.crawlDatum().type())){
//            解析基本信息
            DepartmentInfo departmentInfo = new DepartmentInfo();
            departmentInfo.setUrl(page.url());
            Elements eles = page.select("table[class=table02]>tbody>tr");
            if(eles != null) {
                for (Element ele : eles) {
                    Elements tds = ele.select("td");
                    for (int i = 0; i < tds.size(); i += 2) {
                        String title = tds.get(i).text();
                        String content = tds.get(i + 1).text();
                        if ("统一社会信用代码：".equals(title)) {
                            departmentInfo.setCreditCode(content);
                        } else if ("名       称：".equals(title)) {
                            departmentInfo.setCname(content);
                        } else if ("宗旨和业务范围：".equals(title)) {
                            departmentInfo.setBussinessScope(content);
                        } else if ("住       所：".equals(title)) {
                            departmentInfo.setAddr(content);
                        } else if ("法定代表人：".equals(title)) {
                            departmentInfo.setLegalPerson(content);
                        } else if ("经费来源：".equals(title)) {
                            departmentInfo.setEconomicSorce(content);
                        } else if ("开办资金：".equals(title)) {
                            departmentInfo.setFounds(content);
                        } else if ("举办单位：".equals(title)) {
                            departmentInfo.setOrganizer(content);
                        } else if ("原事证号：".equals(title)) {
                            departmentInfo.setPreOrgNo(content);
                        }
                    }
                }
            }
            DepartmentApp.insertDepartmentInfo(departmentInfo);
            Elements reportEls = page.select("table[class=table01 margin_b20]>tbody>tr");
            if(reportEls != null){
                List<DepartmentReportList> list = new ArrayList<DepartmentReportList>();
                for(Element trEle : reportEls){
                    DepartmentReportList departmentReportList = new DepartmentReportList();
                    departmentReportList.setUrl(page.url());
                    Elements tds = trEle.select("td");
                    if(tds!=null && tds.size()>0){
                        String year = tds.get(0).text();
                        String isPublic = tds.get(1).text();
                        if("年度".equals(year)){
                            continue;
                        }else{
                            departmentReportList.setCname(departmentInfo.getCname());
                            departmentReportList.setYear(year);
                            departmentReportList.setIsPublic(isPublic);
                            if(trEle.hasAttr("onclick")){
                                String[] splits = trEle.attr("onclick").split("'");
                                String detailUrl = HOME_URL + "/"+  splits[3];
                                departmentReportList.setDetailUrl(detailUrl);
                                CrawlDatum crawlDatum = new CrawlDatum(detailUrl);
                                crawlDatum.type("reportDetail");
                                next.add(crawlDatum);
                            }
                            list.add(departmentReportList);


                        }

                    }
                }
                DepartmentApp.insertDepartmentReportListBatch(list);
            }

        }else if ("reportDetail".equals(page.crawlDatum().type())){
            DepartmentReportInfo reportInfo = new DepartmentReportInfo();
            reportInfo.setUrl(page.url());
            Elements els = page.select("div[class=form_title]>div");
            if(els.size()==2){
                String[] certicodeStrs = els.first().text().split(":");
                String certicodeStr = null;
                String yearStr = null;
                if(certicodeStrs.length >=2) {
                    certicodeStr =certicodeStrs[1];
                }
                String[] yearStrs =  els.get(1).text().split(":");
                if(yearStrs.length >= 2){
                    yearStr = yearStrs[1];
                }
                reportInfo.setCreditCode(certicodeStr);
                reportInfo.setYear(yearStr);
            }
            Elements eles = page.select("table[class=table02]>tbody>tr");
            if(eles != null && eles.size()>0) {
                for (Element ele : eles) {
                    Elements tds = ele.select("td");
                    for (int i = 0; i < tds.size(); i += 2) {
                        String title = tds.get(i).text();
                        String content = tds.get(i + 1).text();
                        if ("名称：".equals(title)) {
                            reportInfo.setCname(content);
                        } else if ("宗旨和业务范围：".equals(title)) {
                            reportInfo.setBussinessScope(content);
                        } else if ("住所：".equals(title)) {
                            reportInfo.setAddr(content);
                        } else if ("法定代表人：".equals(title)) {
                            reportInfo.setLegalPerson(content);
                        } else if ("经费来源：".equals(title)) {
                            reportInfo.setEconomicSorce(content);
                        } else if ("开办资金：".equals(title)) {
                            reportInfo.setFounds(content);
                        } else if ("举办单位：".equals(title)) {
                            reportInfo.setOrganizer(content);
                        } else if ("《条例》、《细则》、《规定》有关变更登记规定执行情况:".equals(title)) {
                            reportInfo.setChange(content);
                        } else if ("开展业务活动情况:".equals(title)) {
                            reportInfo.setBussinessStatus(content);
                        } else if ("相关资质认可或执业许可证明文件及有效期:".equals(title)) {
                            reportInfo.setCertificate(content);
                        } else if ("受奖惩评估及诉讼投诉情况:".equals(title)) {
                            reportInfo.setRewardPunishment(content);
                        } else if ("接受捐赠资助及其使用情况:".equals(title)) {
                            reportInfo.setDonated(content);
                        } else if ("资产损益情况:".equals(title)) {
                            Elements assertEles = ele.select("td>table>tbody>tr");
                            if(assertEles.size() == 3){
                                String beginStr = assertEles.get(2).select("td").get(0).text();
                                String endStr = assertEles.get(2).select("td").get(1).text();
                                reportInfo.setYearBegin(beginStr);
                                reportInfo.setYearEnd(endStr);
                            }
                            break;
                        }
                    }
                }
            }
            DepartmentApp.insertDepartmentReportInfo(reportInfo);
        }
    }
    public static void main(String[] args) throws Exception{
        SHSYDJCrawler crawler = new SHSYDJCrawler("SHDepartment", true);
        crawler.setThreads(1);
        crawler.start(1000);
    }
}
