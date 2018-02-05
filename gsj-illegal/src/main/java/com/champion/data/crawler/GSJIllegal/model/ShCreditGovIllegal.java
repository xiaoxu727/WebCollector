package com.champion.data.crawler.GSJIllegal.model;

/**
 * Created by champion_xu on 2018/1/18.
 * 政府采购严重违法失信名单查询
 */
public class ShCreditGovIllegal {
//    名称
    String cname;
    //    统一社会信用代码
    String creditCode;
    //   关注类型数量
    String inReason;

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

    public String getInReason() {
        return inReason;
    }

    public void setInReason(String inReason) {
        this.inReason = inReason;
    }
    public String toString(){
        return "名称：" + this.getCname() + " 统一社会信用代码：" + this.getCreditCode() + " 类型数量："+ this.getInReason();
    }
}
