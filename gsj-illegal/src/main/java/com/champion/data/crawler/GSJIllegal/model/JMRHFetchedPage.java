package com.champion.data.crawler.GSJIllegal.model;

/**
 * Created by champion_xu on 2018/1/25.
 */
public class JMRHFetchedPage {
    String keyword;
    int start;
    int pageNum;
    int step;
    int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String toString(){
        return "关键词：" + this.getKeyword() + " 开始条数：" + this.getStart() +" 每页条数：" + this.getStep() + " 页码：" + this.getPageNum() + "爬取条数：" +this.getTotal();
    }
}
