package com.champion.data.crawler.GSJIllegal.model;

/**
 * Created by champion_xu on 2018/1/10.
 */
public class DepartmentReportInfo extends DepartmentInfo{
//    年度
    String year ;

//    《条例》、《细则》、《规定》有关变更登记规定执行情况:
    String change;

//    开展业务活动情况:
    String bussinessStatus;

//    相关资质认可或执业许可证明文件及有效期:
    String certificate;

//    资产损益情况:年初数（万元）
    String yearBegin;

//    资产损益情况:期末数（万元）
    String yearEnd;

//    受奖惩评估及诉讼投诉情况:
    String rewardPunishment;

//     接受捐赠资助及其使用情况:
    String donated;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getBussinessStatus() {
        return bussinessStatus;
    }

    public void setBussinessStatus(String bussinessStatus) {
        this.bussinessStatus = bussinessStatus;
    }

    public String getCertificate() {
        return certificate;
    }


    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getYearBegin() {
        return yearBegin;
    }

    public void setYearBegin(String yearBegin) {
        this.yearBegin = yearBegin;
    }

    public String getYearEnd() {
        return yearEnd;
    }

    public void setYearEnd(String yearEnd) {
        this.yearEnd = yearEnd;
    }

    public String getRewardPunishment() {
        return rewardPunishment;
    }

    public void setRewardPunishment(String rewardPunishment) {
        this.rewardPunishment = rewardPunishment;
    }

    public String getDonated() {
        return donated;
    }

    public void setDonated(String donated) {
        this.donated = donated;
    }
}
