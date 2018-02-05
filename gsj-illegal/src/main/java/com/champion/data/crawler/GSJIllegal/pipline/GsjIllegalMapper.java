package com.champion.data.crawler.GSJIllegal.pipline;

import com.champion.data.crawler.GSJIllegal.model.GsjIllegal;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by champion_xu on 2017/11/28.
 */
public class GsjIllegalMapper implements RowMapper<GsjIllegal> {

    @Override
    public GsjIllegal mapRow(ResultSet resultSet, int i) throws SQLException {
        GsjIllegal gsjIllegal = new GsjIllegal();
        gsjIllegal.setCname(resultSet.getString("cname"));
        gsjIllegal.setRegisNO(resultSet.getString("regisNO"));
        gsjIllegal.setTitle(resultSet.getString("title"));
        gsjIllegal.setDepartment(resultSet.getString("department"));
        gsjIllegal.setProvince(resultSet.getString("province"));
        gsjIllegal.setDate(resultSet.getString("date"));
        gsjIllegal.setContentUrl(resultSet.getString("contentUrl"));
        gsjIllegal.setContent(resultSet.getString("content"));
        return  gsjIllegal;
    }
}
