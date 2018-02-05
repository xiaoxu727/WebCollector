package com.champion.data.crawler.GSJIllegal.model;

/**
 * Created by champion_xu on 2018/1/16.
 */
public class ShCreditPublishList {
//    处罚ID
    String cfid;
//    处罚名称
    String cfmc;
    /*处罚文书号*/
    String cfwsh;
    /*行政相对人*/
    String xzxdr;
    /**/
    String detailUrl;

    public String getCfid() {
        return cfid;
    }

    public void setCfid(String cfid) {
        this.cfid = cfid;
    }

    public String getCfmc() {
        return cfmc;
    }

    public void setCfmc(String cfmc) {
        this.cfmc = cfmc;
    }

    public String getCfwsh() {
        return cfwsh;
    }

    public void setCfwsh(String cfwsh) {
        this.cfwsh = cfwsh;
    }

    public String getXzxdr() {
        return xzxdr;
    }

    public void setXzxdr(String xzxdr) {
        this.xzxdr = xzxdr;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String toString(){
        return "处罚ID：" + this.getCfid() +" 处罚名称:" + this.getCfmc() + " 处罚文书号：" +this.getCfwsh() + " 行政相对人:" + this.getXzxdr() +" 详情地址：" + this.getDetailUrl();
    }
}
