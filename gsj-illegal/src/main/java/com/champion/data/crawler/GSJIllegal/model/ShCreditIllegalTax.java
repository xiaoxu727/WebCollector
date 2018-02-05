package com.champion.data.crawler.GSJIllegal.model;

/**
 * Created by champion_xu on 2018/1/18.
 * 重大税收违法案件查询(重大税收违法案件当事人名单查询)
 */
public class ShCreditIllegalTax {
//    名称
    String cname;
    //    统一社会信用代码
    String creditCode;
    //   案件性质
    String caseStatus;

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

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String toString(){
        return "名称：" + this.getCname() + " 统一社会信用代码：" + this.getCreditCode() + " 案件性质："+ this.getCaseStatus();
    }
}
