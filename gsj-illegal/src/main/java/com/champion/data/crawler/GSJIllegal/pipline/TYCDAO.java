package com.champion.data.crawler.GSJIllegal.pipline;

import com.champion.data.crawler.GSJIllegal.model.TYCCompanyBrief;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by champion_xu on 2018/2/6.
 */
public interface TYCDAO {

    public void setDataSource(DataSource dataSource);
    public void insertTYCCompanyBrief(TYCCompanyBrief companyBrief);
    public void insertTYCCompanyBriefBatch(List<TYCCompanyBrief> companyBriefs);


}
