package com.champion.data.crawler.GSJIllegal.pipline;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.Page;
import com.champion.data.crawler.GSJIllegal.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by champion_xu on 2018/1/18.
 */
public class JMRHApp {

    static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    static JMRHJDBCTemplate jmrhjdbcTemplate = (JMRHJDBCTemplate) applicationContext.getBean("jmrhjdbcTemplate");

    public static void insertJMRHCategory(JMRHCategory jmrhCategory){
        if(jmrhCategory == null){
            return;
        }
        jmrhjdbcTemplate.insertJMRHCategory(jmrhCategory);
    }

    public static void insertJMRHCategoryBatch(List<JMRHCategory> jmrhCategories){
        if(jmrhCategories.size() == 0){
            return;
        }
        jmrhjdbcTemplate.insertJMRHCategoryBatch(jmrhCategories);
    }

    public static void insertJMRHPatent(JMRHPatent patent){
        if(patent == null){
            return;
        }
        jmrhjdbcTemplate.insertJMRHPatent(patent);

    }

    public static void insertJMRHPatentBatch(List<JMRHPatent> patents){
        if(patents == null ||patents.size() == 0){
            return;
        }
        jmrhjdbcTemplate.insertJMRHPatentBatch(patents);

    }

    public static void insertJMRHListUrl(JMRHListUrl url){
        if(url == null){
            return;
        }
        jmrhjdbcTemplate.insertJMRHListUrl(url);
    }

    public static void insertJMRHListUrl(Page page,int size){
        if(page == null || page.url() == null){
            return;
        }
        JMRHListUrl url = new JMRHListUrl();
        url.setUrl(page.url());
        url.setCount(String.valueOf(size));
        jmrhjdbcTemplate.insertJMRHListUrl(url);
    }


    public static void insertJMRHErrorListUrl(JMRHErrorListUrl url){
        if(url == null){
            return;
        }
        jmrhjdbcTemplate.insertJMRHErrorListUrl(url);
    }

    public static void insertJMRHPatentDetail(JMRHPatentDetail patentDetail){
        if(patentDetail == null){
            return;
        }
        jmrhjdbcTemplate.insertJMRHPatentDetail(patentDetail);
    }
    public static void insertJMRHLawDetail(JMRHLawDetail lawDetail){
        if(lawDetail == null){
            return;
        }

        jmrhjdbcTemplate.insertJMRHLawDetail(lawDetail);
    }
    public static void insertJMRHLawDetailBatch(List<JMRHLawDetail> lawDetails){
        if(lawDetails == null || lawDetails.size() ==0){
            return;
        }
        jmrhjdbcTemplate.insertJMRHLawDetailBatch(lawDetails);
    }
    public static void insertJMRHStatistic(JMRHStatistic statistic){
        if(statistic == null){
            return;
        }
        jmrhjdbcTemplate.insertJMRHStatistic(statistic);
    }

    public static void insertJMRHFetchedPage(JMRHFetchedPage page) {
        if(page == null){
            return;
        }
        jmrhjdbcTemplate.insertJMRHFetchedPage(page);
    }

    public static void insertJMRHFetchedPage(Page page,int size) {
        if(page == null){
            return;
        }
        CrawlDatum datum = page.crawlDatum();
        if(datum.meta().has("keyword")&&datum.meta().has("page")&&datum.meta().has("step")&&datum.meta().has("start")){
            JMRHFetchedPage fetchedPage = new JMRHFetchedPage();
            fetchedPage.setKeyword(datum.meta("keyword"));
            fetchedPage.setPageNum(datum.metaAsInt("page"));
            fetchedPage.setStep(datum.metaAsInt("step"));
            fetchedPage.setStart(datum.metaAsInt("start"));
            fetchedPage.setTotal(size);
            jmrhjdbcTemplate.insertJMRHFetchedPage(fetchedPage);
        }

    }

}
