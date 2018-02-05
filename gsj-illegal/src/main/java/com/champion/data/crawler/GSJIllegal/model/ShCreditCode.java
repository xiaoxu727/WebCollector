package com.champion.data.crawler.GSJIllegal.model;

/**
 * Created by champion_xu on 2018/1/17.
 */
public class ShCreditCode {
//    机构名称
    private String cname;
    /*注册号*/
    private String regisCode;
//    社会信用统一代码
    private String creditCode;
//    组织机构代码
    private String orgCode;
//    纳税识别号
    private String taxCdoe;

    public String getTaxCdoe() {
        return taxCdoe;
    }

    public void setTaxCdoe(String taxCdoe) {
        this.taxCdoe = taxCdoe;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getRegisCode() {
        return regisCode;
    }

    public void setRegisCode(String regisCode) {
        this.regisCode = regisCode;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String toString(){
        return "企业名:" + this.getCname() +" 统一社会信用代码:"+this.getCreditCode() + " 工商注册号："+ this.getRegisCode() + " 组织机构代码：" + this.getOrgCode() + " 纳入识别号："+ this.getTaxCdoe();
    }
}
