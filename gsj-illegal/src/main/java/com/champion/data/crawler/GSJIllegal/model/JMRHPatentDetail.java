package com.champion.data.crawler.GSJIllegal.model;

/**
 * Created by champion_xu on 2018/1/23.
 * 专利详情
 */
public class JMRHPatentDetail {
    String PID;
    String title;
//    法律状态
    String lawStatus;
//    专利类型
    String patentType;
//    申请号
    String applyNo;
    /*公开号*/
    String publicNo;
    /*申请日*/
    String applyDate;
    /*公开日期*/
    String publicDate;
    /*申请人*/
    String applyPerson;
    /*发明人*/
    String inventionPerson;
    /*申请人地址*/
    String applyAddress;
    /*申请人区域号码*/
    String applyAreaCode;
    /*专利权人*/
    String patentee;
    /*洛迦诺分类*/
    String LC;
    /*IPC*/
    String IPC;
    String CPC;
    /*专利代理机构*/
    String patentAgency;
    /*优先权*/
    String priority;
    /*代理人*/
    String agent;
    /*审查员*/
    String inspector;
    /*国际申请*/
    String internationalApplication;
    /*国际公开*/
    String internationalPublic;
    /*进入国家日*/
    String enterDate;
    /*分案申请*/
    String divisionApplication;

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getApplyAddress() {
        return applyAddress;
    }

    public void setApplyAddress(String applyAddress) {
        this.applyAddress = applyAddress;
    }

    public String getApplyAreaCode() {
        return applyAreaCode;
    }

    public void setApplyAreaCode(String applyAreaCode) {
        this.applyAreaCode = applyAreaCode;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public String getApplyPerson() {
        return applyPerson;
    }

    public void setApplyPerson(String applyPerson) {
        this.applyPerson = applyPerson;
    }

    public String getCPC() {
        return CPC;
    }

    public void setCPC(String CPC) {
        this.CPC = CPC;
    }

    public String getDivisionApplication() {
        return divisionApplication;
    }

    public void setDivisionApplication(String divisionApplication) {
        this.divisionApplication = divisionApplication;
    }

    public String getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(String enterDate) {
        this.enterDate = enterDate;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public String getInternationalApplication() {
        return internationalApplication;
    }

    public void setInternationalApplication(String internationalApplication) {
        this.internationalApplication = internationalApplication;
    }

    public String getInternationalPublic() {
        return internationalPublic;
    }

    public void setInternationalPublic(String internationalPublic) {
        this.internationalPublic = internationalPublic;
    }

    public String getInventionPerson() {
        return inventionPerson;
    }

    public void setInventionPerson(String inventionPerson) {
        this.inventionPerson = inventionPerson;
    }

    public String getIPC() {
        return IPC;
    }

    public void setIPC(String IPC) {
        this.IPC = IPC;
    }

    public String getLawStatus() {
        return lawStatus;
    }

    public void setLawStatus(String lawStatus) {
        this.lawStatus = lawStatus;
    }

    public String getLC() {
        return LC;
    }

    public void setLC(String LC) {
        this.LC = LC;
    }

    public String getPatentAgency() {
        return patentAgency;
    }

    public void setPatentAgency(String patentAgency) {
        this.patentAgency = patentAgency;
    }

    public String getPatentee() {
        return patentee;
    }

    public void setPatentee(String patentee) {
        this.patentee = patentee;
    }

    public String getPatentType() {
        return patentType;
    }

    public void setPatentType(String patentType) {
        this.patentType = patentType;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(String publicDate) {
        this.publicDate = publicDate;
    }

    public String getPublicNo() {
        return publicNo;
    }

    public void setPublicNo(String publicNo) {
        this.publicNo = publicNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String toString(){
        return "PID:"+ this.getPID() + " 名称：" + this.getTitle() +"....";
    }
}
