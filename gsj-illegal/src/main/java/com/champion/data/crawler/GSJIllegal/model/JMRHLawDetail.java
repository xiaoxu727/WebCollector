package com.champion.data.crawler.GSJIllegal.model;

/**
 * Created by champion_xu on 2018/1/23.
 */
public class JMRHLawDetail {
    String PID;
    /*法律状态公告日*/
    String lawPublicDate;
    /*法律状态*/
    String lawStatus;
    /*法律状态信息*/
    String lawStatusInfo;

    public String getLawPublicDate() {
        return lawPublicDate;
    }

    public void setLawPublicDate(String lawPublicDate) {
        this.lawPublicDate = lawPublicDate;
    }

    public String getLawStatus() {
        return lawStatus;
    }

    public void setLawStatus(String lawStatus) {
        this.lawStatus = lawStatus;
    }

    public String getLawStatusInfo() {
        return lawStatusInfo;
    }

    public void setLawStatusInfo(String lawStatusInfo) {
        this.lawStatusInfo = lawStatusInfo;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }
    public String toString(){
        return "法律信息 PID：" + this.getPID() + " 法律状态公告日:" + this.getLawPublicDate() + " 法律状态：" + this.getLawStatus() + "法律状态信息：" + this.getLawStatusInfo();
    }
}
