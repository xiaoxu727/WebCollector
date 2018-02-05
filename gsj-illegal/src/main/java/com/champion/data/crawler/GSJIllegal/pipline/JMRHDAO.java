package com.champion.data.crawler.GSJIllegal.pipline;

import com.champion.data.crawler.GSJIllegal.model.*;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by champion_xu on 2018/1/18.
 */
public interface JMRHDAO {
    public void setDataSource(DataSource dataSource);

    public void insertJMRHCategory(JMRHCategory jmrhCategory);

    public void insertJMRHCategoryBatch(List<JMRHCategory> jmrhCategories);

    public void insertJMRHPatent(JMRHPatent patent);

    public void insertJMRHPatentBatch(List<JMRHPatent> patents);

    public void insertJMRHListUrl(JMRHListUrl url);

    public void insertJMRHErrorListUrl(JMRHErrorListUrl url);

    public void insertJMRHPatentDetail(JMRHPatentDetail patentDetail);

    public void insertJMRHLawDetail(JMRHLawDetail lawDetail);

    public void insertJMRHLawDetailBatch(List<JMRHLawDetail> lawDetails);

    public void insertJMRHStatistic(JMRHStatistic statistic);

    public void insertJMRHFetchedPage(JMRHFetchedPage page);



}
