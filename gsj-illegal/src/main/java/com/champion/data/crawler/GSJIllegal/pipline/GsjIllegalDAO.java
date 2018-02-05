package com.champion.data.crawler.GSJIllegal.pipline;

import com.champion.data.crawler.GSJIllegal.model.GsjIllegal;

import java.util.List;
import javax.sql.DataSource;

/**
 * Created by champion_xu on 2017/11/28.
 */
public interface GsjIllegalDAO {

    public void setDataSource(DataSource dataSource);

    public void create(GsjIllegal obj);

    public void update(String contentUrl, String content);

    public void createBatch(List<GsjIllegal> gsjIllegalList);
}
