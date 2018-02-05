package com.champion.data.crawler.GSJIllegal.pipline;

import com.champion.data.crawler.GSJIllegal.model.GsjIllegal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import sun.reflect.generics.reflectiveObjects.LazyReflectiveObjectGenerator;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by champion_xu on 2017/11/28.
 */
public class GsjIllegalJDBCTempalte implements GsjIllegalDAO {
    public static final Logger LOG = LoggerFactory.getLogger(GsjIllegalJDBCTempalte.class);
    DataSource dataSource ;
    private JdbcTemplate jdbcTemplate ;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void create(GsjIllegal obj) {
        String sql = "insert into gsj_illegal(cname,regisNO,title,department,date,province,contentUrl,content) VALUE (?,?,?,?,?,?,?,?) ";
//        String sql = "insert into gsj_illegal(title,department,date,province,contentUrl,content) VALUE (?,?,?,?,?,?) ";
        this.jdbcTemplate.update(sql, obj.getTitle(), obj.getDepartment(), obj.getDate(), obj.getProvince(), obj.getContentUrl(), obj.getContent());
        LOG.info("成功插入数据："+obj.toString());
    }

    @Override
    public void update(String contentUrl, String content) {
        String sql = "update gsj_illegal set content = ? where contentUrl = ?";
        this.jdbcTemplate.update(sql,content,contentUrl);
        LOG.info("成功获取详情：" +content);
    }

    @Override
    public void createBatch(List<GsjIllegal> gsjIllegalList) {
        final List<GsjIllegal> list = gsjIllegalList;
        String sql = "insert into gsj_illegal(title,department,date,province,contentUrl,content,cname,regisNO) VALUE (?,?,?,?,?,?,?,?) ";
        this.jdbcTemplate.batchUpdate(sql,new BatchPreparedStatementSetter(){

            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
              preparedStatement.setString(1,list.get(i).getTitle());
              preparedStatement.setString(2,list.get(i).getDepartment());
              preparedStatement.setString(3,list.get(i).getDate());
              preparedStatement.setString(4,list.get(i).getProvince());
              preparedStatement.setString(5,list.get(i).getContentUrl());
              preparedStatement.setString(6,list.get(i).getContent());
              preparedStatement.setString(7,list.get(i).getCname());
              preparedStatement.setString(8,list.get(i).getRegisNO());
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
        for (GsjIllegal gsjIllegal : gsjIllegalList){
            LOG.info("插入数据成功：" + gsjIllegal.toString());
        }

    }
}
