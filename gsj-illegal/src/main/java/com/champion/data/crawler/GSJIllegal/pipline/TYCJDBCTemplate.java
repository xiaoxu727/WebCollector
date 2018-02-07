package com.champion.data.crawler.GSJIllegal.pipline;

import com.champion.data.crawler.GSJIllegal.model.TYCCompanyBrief;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by champion_xu on 2018/2/6.
 */
public class TYCJDBCTemplate implements TYCDAO {
    public static final Logger LOG = LoggerFactory.getLogger(TYCJDBCTemplate.class);
    DataSource dataSource ;
    private JdbcTemplate jdbcTemplate ;


    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void insertTYCCompanyBrief(TYCCompanyBrief companyBrief) {
        String sql = "insert into company_brief(patent,cname,status,legalPerson,regisCaptial,regisDate,phone,province,score,sholder,detailUrl,root,depth) value(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        this.jdbcTemplate.update(sql,companyBrief.getParent(),companyBrief.getCname(),companyBrief.getStatus(),companyBrief.getLegalPerson(),companyBrief.getRegisCaptial(),companyBrief.getRegisDate(),
                companyBrief.getPhone(),companyBrief.getProvince(),companyBrief.getScore(),companyBrief.getSholder(),companyBrief.getDetailUrl(),companyBrief.getRoot(),companyBrief.getDepth());
        LOG.info("插入成功：" + companyBrief.toString());
    }

    @Override
    public void insertTYCCompanyBriefBatch(List<TYCCompanyBrief> companyBriefs) {
        List<TYCCompanyBrief> list = companyBriefs;
        String sql = "insert into company_brief(patent,cname,status,legalPerson,regisCaptial,regisDate,phone,province,score,sholder,detailUrl,root,depth) value(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1, list.get(i).getParent());
                preparedStatement.setString(2, list.get(i).getCname());
                preparedStatement.setString(3, list.get(i).getStatus());
                preparedStatement.setString(4, list.get(i).getLegalPerson());
                preparedStatement.setString(5, list.get(i).getRegisCaptial());
                preparedStatement.setString(6, list.get(i).getRegisDate());
                preparedStatement.setString(7, list.get(i).getPhone());
                preparedStatement.setString(8, list.get(i).getProvince());
                preparedStatement.setString(9, list.get(i).getScore());
                preparedStatement.setString(10, list.get(i).getSholder());
                preparedStatement.setString(11, list.get(i).getDetailUrl());
                preparedStatement.setString(12, list.get(i).getRoot());
                preparedStatement.setInt(13, list.get(i).getDepth());
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
        for(TYCCompanyBrief companyBrief : list){
            LOG.info("插入成功：" + companyBrief.toString());
        }
    }

}
