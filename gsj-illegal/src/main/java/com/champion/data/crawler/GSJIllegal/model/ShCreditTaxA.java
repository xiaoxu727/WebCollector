package com.champion.data.crawler.GSJIllegal.model;

/**
 * Created by champion_xu on 2018/1/18.
 * 税务A级纳税人名单查询
 */
public class ShCreditTaxA {
//    名称
    String cname;
    //    统一社会信用代码
    String creditCode;
    //   评定年度
    String year;

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
    public String toString(){
        return "名称：" + this.getCname() + " 统一社会信用代码：" + this.getCreditCode() + " 评定年度："+ this.getYear();
    }
}
