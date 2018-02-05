package cn.edu.hfut.dmic.webcollector.model;

import com.google.gson.Gson;
import com.sun.javafx.binding.StringFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by champion_xu on 2017/12/15.
 */
public class WebSite {

    private String key;

    private String siteName;
//    url
    private String url;
//    深度
    private int maxDepth = 1;
//    插入时间
    private Date inputDate = new Date();
//    最后一次抓取时间
    private Date lasteDate = new Date();
//    周期 单位分
    private long cycle = 60;
//    过滤规则
    private String negativeFilter;
//   通过规则
    private String positiveFilter;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    public Date getLasteDate() {
        return lasteDate;
    }

    public void setLasteDate(Date lasteDate) {
        this.lasteDate = lasteDate;
    }

    public long getCycle() {
        return cycle;
    }

    public void setCycle(long cycle) {
        this.cycle = cycle;
    }

    public String getNegativeFilter() {
        return negativeFilter;
    }

    public void setNegativeFilter(String negativeFilter) {
        this.negativeFilter = negativeFilter;
    }

    public String getPositiveFilter() {
        return positiveFilter;
    }

    public void setPositiveFilter(String positiveFilter) {
        this.positiveFilter = positiveFilter;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static String websiteToStr(WebSite webSite){
        return new Gson().toJson(webSite);
    }

    public boolean isValid(){
        Date now  = new Date();
        if(this.lasteDate != null && cycle != 0) {
            if ((now.getTime() - this.lasteDate.getTime()) / (1000 * 60) > cycle) {
                return true;
            }
        }
        return false;
    }

    public static WebSite strToWebsite(String str){
        WebSite webSite = new Gson().fromJson(str, WebSite.class);
        return webSite;
    }
    public  static WebSite websiteFromCrawDautm(CrawlDatum datum){
        WebSite webSite = null;
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            webSite = new WebSite();
            webSite.setKey(datum.key());
            webSite.setUrl(datum.url());
            webSite.setSiteName(datum.meta("siteName"));
            webSite.setMaxDepth(datum.metaAsInt("maxDepth"));
            webSite.setCycle(datum.metaAsInt("cycle"));
            webSite.setNegativeFilter(datum.meta("negativeFilter"));
            webSite.setPositiveFilter(datum.meta("positiveFilter"));
            webSite.setInputDate(formatter.parse(datum.meta("inputDate")));
            webSite.setLasteDate(formatter.parse(datum.meta("lasteDate")));
        }catch (Exception e){

        }
       return  webSite;
    }

    public CrawlDatum toCrawDatum(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CrawlDatum datum = new CrawlDatum();
        datum.key(this.key);
        datum.url(this.url);
        datum.meta("siteName", this.siteName);
        datum.meta("maxDepth", this.getMaxDepth());
        datum.meta("cycle", this.cycle);
        datum.meta("negativeFilter",this.negativeFilter);
        datum.meta("positiveFilter",this.positiveFilter);
        datum.meta("inputDate", formatter.format(this.inputDate));
        datum.meta("lasteDate", formatter.format(this.lasteDate));
        return datum;
    }

    public static void main(String[] args) throws ParseException, InterruptedException {
        WebSite webSite = new WebSite();
        while (true){
            webSite.setCycle(60);
            webSite.lasteDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-12-19 15:32:40");
            System.out.println(webSite.isValid());
            Thread.sleep(10000);
        }
    }
}
