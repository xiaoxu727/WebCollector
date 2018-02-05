package com.champion.data.crawler.GSJIllegal.model;

/**
 * Created by champion_xu on 2018/1/19.
 */
public class JMRHListUrl {
    String url;
    String count;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
    public String toString(){
        return "专利列表url:" + this.getUrl() + " 数量："+ this.getCount();
    }
}
