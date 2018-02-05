package com.champion.data.crawler.GSJIllegal.pipline;

import com.champion.data.crawler.GSJIllegal.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by champion_xu on 2018/1/10.
 */
public class DepatmentJDBCTemplate implements DepartmentDAO {
    public static final Logger LOG = LoggerFactory.getLogger(DepatmentJDBCTemplate.class);
    DataSource dataSource ;
    private JdbcTemplate jdbcTemplate ;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void insertDepartmentList(DepartmentList departmentList) {
        String sql = "insert into department_list(cname, detailUrl) value(?,?)";
        this.jdbcTemplate.update(sql,departmentList.getCname(),departmentList.getDetailUrl());
    }

    @Override
    public void insertDepartmentListBatch(List<DepartmentList> departmentListList){
        final List<DepartmentList> list = departmentListList;
        String sql = "insert into department_list(cname, detailUrl) value(?,?)";
        this.jdbcTemplate.batchUpdate(sql,new BatchPreparedStatementSetter(){

            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1,list.get(i).getCname());
                preparedStatement.setString(2,list.get(i).getDetailUrl());
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
        for (DepartmentList departmentList : departmentListList){
            LOG.info("插入数据成功：" + departmentList.toString());
        }
    }

    @Override
    public void insertDepartmentReportListBatch(List<DepartmentReportList> departmentReportLists){
        final List<DepartmentReportList> list = departmentReportLists;
        String sql = "insert into department_report_list(cname, year, isPublic, url, detailUrl) value(?, ?, ?, ?, ?)";
        this.jdbcTemplate.batchUpdate(sql,new BatchPreparedStatementSetter(){

            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1,list.get(i).getCname());
                preparedStatement.setString(2,list.get(i).getYear());
                preparedStatement.setString(3,list.get(i).getIsPublic());
                preparedStatement.setString(4,list.get(i).getUrl());
                preparedStatement.setString(5,list.get(i).getDetailUrl());
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
        for (DepartmentReportList departmentReportList : departmentReportLists){
            LOG.info("插入数据成功：" + departmentReportList.toString());
        }
    }

    @Override
    public void insertDepartmentInfo(DepartmentInfo departmentInfo) {
        String sql = "insert into department_info(cname, creditNO, bussinessScope, addr, legalPerson, economicSorce, founds, organizer, preOrgNo, url) value(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        this.jdbcTemplate.update(sql,departmentInfo.getCname(),departmentInfo.getCreditCode(),departmentInfo.getBussinessScope(),departmentInfo.getAddr(),departmentInfo.getLegalPerson(),departmentInfo.getEconomicSorce(),departmentInfo.getFounds(),departmentInfo.getOrganizer(),departmentInfo.getPreOrgNo(),departmentInfo.getUrl());


    }

    @Override
    public void insertDepartmentReportList(DepartmentReportList departmentReportList) {
        String sql = "insert into department_list(cname, year, isPublic, url, detailUrl) value(?, ?, ?, ?, ?)";
        this.jdbcTemplate.update(departmentReportList.getCname(),departmentReportList.getYear(),departmentReportList.getIsPublic(),departmentReportList.getUrl(),departmentReportList.getDetailUrl());
    }

    @Override
    public void insertDepartmentReportInfo(DepartmentReportInfo departmentReportInfo) {
        String sql = "insert into department_report_info(cname, creditNO, bussinessScope, addr, legalPerson, economicSorce, founds, organizer, preOrgNo, url, year, changeStr, bussinessStatus, certificate, yearBegin, yearEnd, rewardPunishment, donated) value(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        this.jdbcTemplate.update(sql,departmentReportInfo.getCname(),departmentReportInfo.getCreditCode(),departmentReportInfo.getBussinessScope(),departmentReportInfo.getAddr(),departmentReportInfo.getLegalPerson(),departmentReportInfo.getEconomicSorce(),departmentReportInfo.getFounds(),departmentReportInfo.getOrganizer(),
                departmentReportInfo.getPreOrgNo(),departmentReportInfo.getUrl(),departmentReportInfo.getYear(),departmentReportInfo.getChange(),departmentReportInfo.getBussinessStatus(),departmentReportInfo.getCertificate(),departmentReportInfo.getYearBegin(),departmentReportInfo.getYearEnd(),departmentReportInfo.getRewardPunishment(),
                departmentReportInfo.getDonated());
    }
}
