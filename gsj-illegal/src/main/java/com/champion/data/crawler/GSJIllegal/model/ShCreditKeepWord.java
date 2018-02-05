package com.champion.data.crawler.GSJIllegal.model;

/**
 * Created by champion_xu on 2018/1/17.
 */
public class ShCreditKeepWord {
//    名称
    String cname;
//    统一社会信用代码
    String creditCode;
//    守信类型数量
    String KeepWordCount;

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getKeepWordCount() {
        return KeepWordCount;
    }

    public void setKeepWordCount(String keepWordCount) {
        KeepWordCount = keepWordCount;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String toString(){
        return "名称：" + this.getCname() + " 统一社会信用代码：" + this.getCreditCode() + " 守信数量："+ this.getKeepWordCount();
    }
}
