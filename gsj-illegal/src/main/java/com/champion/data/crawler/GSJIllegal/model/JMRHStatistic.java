package com.champion.data.crawler.GSJIllegal.model;

/**
 * Created by champion_xu on 2018/1/25.
 */
public class JMRHStatistic {
    String keyword;
    String total;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
    public String toString(){
        return "关键词：" + this.getKeyword() + " 共抓取数量：" + this.getTotal();
    }
}
