package com.champion.data.crawler.GSJIllegal.model;

/**
 * Created by champion_xu on 2017/11/27.
 */
public class GsjIllegal {

    //
    private String cname;

    private String regisNO;

    private String title ;

    private String department;

    private String date;

    private String content;

    private String contentUrl;


    private String province;

    public String getRegisNO() {
        return regisNO;
    }

    public void setRegisNO(String regisNO) {
        this.regisNO = regisNO;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String toString(){
        return "cname" + this.getCname()+"regisNo"+this.getRegisNO()+ "title:" + this.getTitle()+ "; department:" + this.getDepartment() + "; date:" + this.getDate() + "; province:" +this.getProvince();

    }

}
