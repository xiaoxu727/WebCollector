package com.champion.data.crawler.GSJIllegal.model;

/**
 * Created by champion_xu on 2018/1/19.
 */
public class JMRHErrorListUrl {
    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toString(){
        return "抓取失败网页：" + this.getUrl();
    }
}
