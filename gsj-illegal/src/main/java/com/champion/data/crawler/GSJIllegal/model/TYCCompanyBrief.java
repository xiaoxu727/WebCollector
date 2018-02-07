package com.champion.data.crawler.GSJIllegal.model;

/**
 * Created by champion_xu on 2018/2/6.
 */
public class TYCCompanyBrief {
    String parent;
    String cname;
    String status;
    String legalPerson;
    String regisCaptial;
    String regisDate;
    String phone;
    String province;
    String score;
    String sholder;
    String detailUrl;
    String root;
    int depth;

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public int getDepth() {

        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRegisCaptial() {
        return regisCaptial;
    }

    public void setRegisCaptial(String regisCaptial) {
        this.regisCaptial = regisCaptial;
    }

    public String getRegisDate() {
        return regisDate;
    }

    public void setRegisDate(String regisDate) {
        this.regisDate = regisDate;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSholder() {
        return sholder;
    }

    public void setSholder(String sholder) {
        this.sholder = sholder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String  toString(){
        return "查询关键词："+ this.getParent() + " 公司名：" + this.getCname() + " 注册时间" + this.getRegisDate() +"...";
    }

}
