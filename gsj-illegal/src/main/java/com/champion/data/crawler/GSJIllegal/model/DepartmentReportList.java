package com.champion.data.crawler.GSJIllegal.model;

/**
 * Created by champion_xu on 2018/1/10.
 */
public class DepartmentReportList {
//    名称
    String cname;
//    年度
    String year ;
//    是否公开
    String  isPublic;
//    url
    String url;
//    详情url
    String detailUrl;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String toString(){
        return "cname:" + this.getCname() +" year:"+ this.getYear() +" isPublis" + this.getIsPublic() + " Url" + this.getUrl() + " detialUrl" + this.getDetailUrl();
    }
}
