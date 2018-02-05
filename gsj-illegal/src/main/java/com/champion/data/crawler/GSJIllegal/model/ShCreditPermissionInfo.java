package com.champion.data.crawler.GSJIllegal.model;

/**
 * 信用上海——行政许可
 * Created by champion_xu on 2017/12/8.
 */
public class ShCreditPermissionInfo {
    /*许可id*/
    private String xfid;
//    审批类别
    private String splb;
//    许可决定日期
    private String xkjdrq;
    //    许可机关
    private String xkjg;
    //   许可截止日期
    private String xkjzrq;
    //  许可内容
    private String xknr;
    //    许可文书号
    private String xkwsh;
    //    许可项目名称
    private String xmmc;
    //    行政相对人
    private String xzxdr;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getXfid() {
        return xfid;
    }
    public void setXfid(String xfid) {
        this.xfid = xfid;
    }

    public String getSplb() {
        return splb;
    }

    public void setSplb(String splb) {
        this.splb = splb;
    }

    public String getXkjdrq() {
        return xkjdrq;
    }

    public void setXkjdrq(String xkjdrq) {
        this.xkjdrq = xkjdrq;
    }

    public String getXkjg() {
        return xkjg;
    }

    public void setXkjg(String xkjg) {
        this.xkjg = xkjg;
    }

    public String getXkjzrq() {
        return xkjzrq;
    }

    public void setXkjzrq(String xkjzrq) {
        this.xkjzrq = xkjzrq;
    }

    public String getXknr() {
        return xknr;
    }

    public void setXknr(String xknr) {
        this.xknr = xknr;
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

    public String toString(){
        return "许可文书号:" + this.getXkwsh() + " 项目名称" + this.getXmmc() + " 行政相对人" + this.getXzxdr();
    }


}
