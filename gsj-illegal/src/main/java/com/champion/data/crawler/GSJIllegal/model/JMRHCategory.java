package com.champion.data.crawler.GSJIllegal.model;

/**
 * Created by champion_xu on 2018/1/18.
 * 专利类型统计
 */
public class JMRHCategory {
    /*名称*/
    String cname;
    /*类型*/
    String type;
    /*值*/
    String value;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public String toString(){
        return "企业名称：" + this.getCname() + " 类型：" + this.getType() + " 数量:" + this.getValue();
    }
}
