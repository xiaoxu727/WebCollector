package com.champion.data.crawler.GSJIllegal.model;

/**
 * Created by champion_xu on 2018/1/16.
 */
public class ShCreditPublishInfo extends ShCreditPublishList {
    /*处罚决定日期*/
    String cfjdrq;
    String cfjguan;
    /*处罚类别*/
    String cflb;
    /*处罚事由*/
    String cfsy;
    /*处罚依据*/
    String cfyj;

    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getCfjdrq() {
        return cfjdrq;
    }

    public void setCfjdrq(String cfjdrq) {
        this.cfjdrq = cfjdrq;
    }

    public String getCfjguan() {
        return cfjguan;
    }

    public void setCfjguan(String cfjguan) {
        this.cfjguan = cfjguan;
    }

    public String getCflb() {
        return cflb;
    }

    public void setCflb(String cflb) {
        this.cflb = cflb;
    }

    public String getCfsy() {
        return cfsy;
    }

    public void setCfsy(String cfsy) {
        this.cfsy = cfsy;
    }

    public String getCfyj() {
        return cfyj;
    }

    public void setCfyj(String cfyj) {
        this.cfyj = cfyj;
    }

    public String toString(){
        return  super.toString();
    }
}
