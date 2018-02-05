package com.champion.data.crawler.GSJIllegal.model;

/**
 * Created by champion_xu on 2018/1/16.
 */
public class ShCreditPermissionList {

    String xkid;
//    许可文书号
    String xkwsh;
//    项目名称
    String xmmc;
//    行政相对人
    String xzxdr;
//    详情地址
    String detailUrl;

    public String getXkid() {
        return xkid;
    }

    public void setXkid(String xkid) {
        this.xkid = xkid;
    }

    public String getXkwsh() {
        return xkwsh;
    }

    public void setXkwsh(String xkwsh) {
        this.xkwsh = xkwsh;
    }

    public String getXmmc() {
        return xmmc;
    }

    public void setXmmc(String xmmc) {
        this.xmmc = xmmc;
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
        return "许可文书号:" + this.getXkwsh() + " 项目名称" + this.getXmmc() + " 行政相对人" + this.getXzxdr();
    }
}
