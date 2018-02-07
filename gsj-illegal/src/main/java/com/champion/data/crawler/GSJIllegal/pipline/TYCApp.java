package com.champion.data.crawler.GSJIllegal.pipline;

import com.champion.data.crawler.GSJIllegal.model.TYCCompanyBrief;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by champion_xu on 2018/2/6.
 */
public class TYCApp {
    static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    static TYCJDBCTemplate tycjdbcTemplate = (TYCJDBCTemplate) applicationContext.getBean("tycJDBCTemplate");

    public static void insertTYCCompanyBrief(TYCCompanyBrief companyBrief){
        if(companyBrief != null){
            tycjdbcTemplate.insertTYCCompanyBrief(companyBrief);
        }
    }

    public static void insertTYCCompanyBriefBatch(List<TYCCompanyBrief> companyBriefs){
        if(companyBriefs != null && companyBriefs.size() > 0){
            tycjdbcTemplate.insertTYCCompanyBriefBatch(companyBriefs);
        }
    }



}
