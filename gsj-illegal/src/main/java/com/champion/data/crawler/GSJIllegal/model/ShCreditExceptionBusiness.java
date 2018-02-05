package com.champion.data.crawler.GSJIllegal.model;

/**
 * Created by champion_xu on 2018/1/18.
 * 经营异常名录
 */
public class ShCreditExceptionBusiness {
//    名称
    String cname;
    //    统一社会信用代码
    String creditCode;
    //   纳入时间
    String inTime;

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

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String toString(){
        return "名称：" + this.getCname() + " 统一社会信用代码：" + this.getCreditCode() + " 纳入时间：" + this.getInTime();
    }
}
