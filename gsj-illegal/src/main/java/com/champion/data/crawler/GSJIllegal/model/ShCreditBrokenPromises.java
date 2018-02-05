package com.champion.data.crawler.GSJIllegal.model;

/**
 * Created by champion_xu on 2018/1/18.
 */
public class ShCreditBrokenPromises {
//    名称
    String cname;
    //    统一社会信用代码
    String creditCode;
    //   失信数量
    String typeCount;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getTypeCount() {
        return typeCount;
    }

    public void setTypeCount(String typeCount) {
        this.typeCount = typeCount;
    }
    public String toString(){
        return "名称：" + this.getCname() + " 统一社会信用代码：" + this.getCreditCode() + " 失信数量："+ this.getTypeCount();
    }
}
