package com.champion.data.crawler.GSJIllegal.model;

/**
 * Created by champion_xu on 2018/1/10.
 */
public class DepartmentList {
//    单位名称
    String cname ;
//    单位详情地址
    String detailUrl;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String toString(){
        String str = "cname:" + this.getCname() +" detailUrl:" + this.getDetailUrl();
        return str;
    }


}
