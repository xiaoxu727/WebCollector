package com.champion.data.crawler.GSJIllegal.pipline;

import com.champion.data.crawler.GSJIllegal.model.*;
import com.champion.data.crawler.GSJIllegal.util.PropertiesUtil;
import jdk.internal.dynalink.beans.StaticClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by champion_xu on 2018/1/18.
 */
public class JMRHJDBCTemplate implements JMRHDAO {
    public static final Logger LOG = LoggerFactory.getLogger(JMRHJDBCTemplate.class);
    DataSource dataSource ;
    private JdbcTemplate jdbcTemplate ;
    static String  PATENT_TABLE = "jmrh_patent";
    static {
        if(PropertiesUtil.getPeroperty("jmrh_patent_table")!= null)
        {
            PATENT_TABLE = PropertiesUtil.getPeroperty("jmrh_patent_table");
        }
    }

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void insertJMRHCategory(JMRHCategory jmrhCategory) {
        String sql = "insert into jmrh_category(cname, type, value) value(?,?,?)";
        this.jdbcTemplate.update(sql,jmrhCategory.getCname(),jmrhCategory.getType(),jmrhCategory.getValue());
        LOG.info("插入专利统计信息：" + jmrhCategory.toString());
    }

    @Override
    public void insertJMRHCategoryBatch(List<JMRHCategory> jmrhCategories) {
        List<JMRHCategory> list = jmrhCategories;
        String sql = "insert into jmrh_category(cname, type, value) value(?,?,?)";
        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1, list.get(i).getCname());
                preparedStatement.setString(2, list.get(i).getType());
                preparedStatement.setString(3, list.get(i).getValue());
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
        for(JMRHCategory category : list){
            LOG.info("插入专利统计信息：" + category.toString());
        }

    }

    @Override
    public void insertJMRHPatent(JMRHPatent patent) {
        String sql = "insert into "+PATENT_TABLE+"(keyword,linkPath,PID,TIC ,TIE ,TIO,INO,INC,INE,ANO,AD,PD,PNO,APO,APE,APC,IPCQ,IPC,PRNO ,PRN ,LC ,VU ,ABSO,ABSE ,ABSC ,EFC ,SFC,IMGTITLE ,IMGNAME ,LSSC,PDT,PDTC,MCLE,CIGC ,TFO ,TBO ,ISO ,SEO ,DDO ,CLO ,CLN,DEBEC ,DEBEO ,DEBEE ,IMGO ,PDFEXIST,PNS,SFPNS ,ASO,ASC_S" +
                ",ASE,DRAP,eYears,ABS_IMG,PT ,PERT ,CRN ,BAJDO ,PCYMD ,RLYMD ,TRO ,TRPO ,TT ,EYMD ,PASO ,AASO ,ASPO ,ILSSC ,ILSAYMD ,LSBCN ,PPT ,PPRS ,PPEYMD ,PPYMD ,PPRYMD ,PPOO ,PDBC,PPPO) value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        this.jdbcTemplate.update(sql,patent.getKeyWord(),
                patent.getLinkPath(),patent.getPID(),patent.getTIC (),patent.getTIE (),patent.getTIO(),patent.getINO(),patent.getINC(),patent.getINE(),patent.getANO(),patent.getAD(),patent.getPD(),patent.getPNO(),patent.getAPO(),patent.getAPE(),patent.getAPC(),patent.getIPCQ(),patent.getIPC(),patent.getPRNO (),patent.getPRN (),patent.getLC (),patent.getVU (),patent.getABSO(),patent.getABSE (),patent.getABSC (),patent.getEFC (),patent.getSFC(),patent.getIMGTITLE (),patent.getIMGNAME (),patent.getLSSC(),patent.getPDT(),patent.getPDTC(),patent.getMCLE(),patent.getCIGC (),patent.getTFO (),patent.getTBO (),patent.getISO (),patent.getSEO (),patent.getDDO (),patent.getCLO (),patent.getCLN(),patent.getDEBEC (),patent.getDEBEO (),patent.getDEBEE (),patent.getIMGO (),patent.getPDFEXIST(),patent.getPNS(),patent.getSFPNS (),patent.getASO(),patent.getASC(),patent.getASE(),patent.getDRAP(),patent.geteYears(),patent.getABS_IMG(),patent.getPT (),patent.getPERT (),patent.getCRN (),patent.getBAJDO (),patent.getPCYMD (),patent.getRLYMD (),patent.getTRO (),patent.getTRPO (),patent.getTT (),patent.getEYMD (),patent.getPASO (),patent.getAASO (),patent.getASPO (),patent.getILSSC (),patent.getILSAYMD (),patent.getLSBCN (),patent.getPPT (),patent.getPPRS (),patent.getPPEYMD (),patent.getPPYMD (),patent.getPPRYMD (),patent.getPPOO (),patent.getPDBC(),patent.getPPPO ());
        LOG.info("插入专利列表信息：" + patent.toString());
    }

    @Override
    public void insertJMRHPatentBatch(List<JMRHPatent> patents) {
        List<JMRHPatent> list = patents;

        String sql = "insert into "+PATENT_TABLE+"(keyword,linkPath,PID,TIC ,TIE ,TIO,INO,INC,INE,ANO,AD,PD,PNO,APO,APE,APC,IPCQ,IPC,PRNO ,PRN ,LC ,VU ,ABSO,ABSE ,ABSC ,EFC ,SFC,IMGTITLE ,IMGNAME ,LSSC,PDT,PDTC,MCLE,CIGC ,TFO ,TBO ,ISO ,SEO ,DDO ,CLO ,CLN,DEBEC ,DEBEO ,DEBEE ,IMGO ,PDFEXIST,PNS,SFPNS ,ASO,ASC_S" +
                ",ASE,DRAP,eYears,ABS_IMG,PT ,PERT ,CRN ,BAJDO ,PCYMD ,RLYMD ,TRO ,TRPO ,TT ,EYMD ,PASO ,AASO ,ASPO ,ILSSC ,ILSAYMD ,LSBCN ,PPT ,PPRS ,PPEYMD ,PPYMD ,PPRYMD ,PPOO ,PDBC,PPPO) value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1,list.get(i).getKeyWord());
                preparedStatement.setString(2,list.get(i).getLinkPath());
                preparedStatement.setString(3,list.get(i).getPID());
                preparedStatement.setString(4,list.get(i).getTIC ());
                preparedStatement.setString(5,list.get(i).getTIE ());
                preparedStatement.setString(6,list.get(i).getTIO());
                preparedStatement.setString(7,list.get(i).getINO());
                preparedStatement.setString(8,list.get(i).getINC());
                preparedStatement.setString(9,list.get(i).getINE());
                preparedStatement.setString(10,list.get(i).getANO());
                preparedStatement.setString(11,list.get(i).getAD());
                preparedStatement.setString(12,list.get(i).getPD());
                preparedStatement.setString(13,list.get(i).getPNO());
                preparedStatement.setString(14,list.get(i).getAPO());
                preparedStatement.setString(15,list.get(i).getAPE());
                preparedStatement.setString(16,list.get(i).getAPC());
                preparedStatement.setString(17,list.get(i).getIPCQ());
                preparedStatement.setString(18,list.get(i).getIPC());
                preparedStatement.setString(19,list.get(i).getPRNO ());
                preparedStatement.setString(20,list.get(i).getPRN ());
                preparedStatement.setString(21,list.get(i).getLC ());
                preparedStatement.setString(22,list.get(i).getVU ());
                preparedStatement.setString(23,list.get(i).getABSO());
                preparedStatement.setString(24,list.get(i).getABSE ());
                preparedStatement.setString(25,list.get(i).getABSC ());
                preparedStatement.setString(26,list.get(i).getEFC ());
                preparedStatement.setString(27,list.get(i).getSFC());
                preparedStatement.setString(28,list.get(i).getIMGTITLE ());
                preparedStatement.setString(29,list.get(i).getIMGNAME ());
                preparedStatement.setString(30,list.get(i).getLSSC());
                preparedStatement.setString(31,list.get(i).getPDT());
                preparedStatement.setString(32,list.get(i).getPDTC());
                preparedStatement.setString(33,list.get(i).getMCLE());
                preparedStatement.setString(34,list.get(i).getCIGC ());
                preparedStatement.setString(35,list.get(i).getTFO ());
                preparedStatement.setString(36,list.get(i).getTBO ());
                preparedStatement.setString(37,list.get(i).getISO ());
                preparedStatement.setString(38,list.get(i).getSEO ());
                preparedStatement.setString(39,list.get(i).getDDO ());
                preparedStatement.setString(40,list.get(i).getCLO ());
                preparedStatement.setString(41,list.get(i).getCLN());
                preparedStatement.setString(42,list.get(i).getDEBEC ());
                preparedStatement.setString(43,list.get(i).getDEBEO ());
                preparedStatement.setString(44,list.get(i).getDEBEE ());
                preparedStatement.setString(45,list.get(i).getIMGO ());
                preparedStatement.setString(46,list.get(i).getPDFEXIST());
                preparedStatement.setString(47,list.get(i).getPNS());
                preparedStatement.setString(48,list.get(i).getSFPNS ());
                preparedStatement.setString(49,list.get(i).getASO());
                preparedStatement.setString(50,list.get(i).getASC());
                preparedStatement.setString(51,list.get(i).getASE());
                preparedStatement.setString(52,list.get(i).getDRAP());
                preparedStatement.setString(53,list.get(i).geteYears());
                preparedStatement.setString(54,list.get(i).getABS_IMG());
                preparedStatement.setString(55,list.get(i).getPT ());
                preparedStatement.setString(56,list.get(i).getPERT ());
                preparedStatement.setString(57,list.get(i).getCRN ());
                preparedStatement.setString(58,list.get(i).getBAJDO ());
                preparedStatement.setString(59,list.get(i).getPCYMD ());
                preparedStatement.setString(60,list.get(i).getRLYMD ());
                preparedStatement.setString(61,list.get(i).getTRO ());
                preparedStatement.setString(62,list.get(i).getTRPO ());
                preparedStatement.setString(63,list.get(i).getTT ());
                preparedStatement.setString(64,list.get(i).getEYMD ());
                preparedStatement.setString(65,list.get(i).getPASO ());
                preparedStatement.setString(66,list.get(i).getAASO ());
                preparedStatement.setString(67,list.get(i).getASPO ());
                preparedStatement.setString(68,list.get(i).getILSSC ());
                preparedStatement.setString(69,list.get(i).getILSAYMD ());
                preparedStatement.setString(70,list.get(i).getLSBCN ());
                preparedStatement.setString(71,list.get(i).getPPT ());
                preparedStatement.setString(72,list.get(i).getPPRS ());
                preparedStatement.setString(73,list.get(i).getPPEYMD ());
                preparedStatement.setString(74,list.get(i).getPPYMD ());
                preparedStatement.setString(75,list.get(i).getPPRYMD ());
                preparedStatement.setString(76,list.get(i).getPPOO ());
                preparedStatement.setString(77,list.get(i).getPDBC());
                preparedStatement.setString(78,list.get(i).getPPPO ());
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
        for(JMRHPatent patent : patents){
            LOG.info("插入专利列表信息：" + patent.toString());
        }
    }

    @Override
    public void insertJMRHListUrl(JMRHListUrl url) {
        String sql = "insert into jmrh_list_url(url,count) value(?,?)";
        this.jdbcTemplate.update(sql,url.getUrl(),url.getCount());
        LOG.info("插入专利网址信息：" + url.toString());

    }

    @Override
    public void insertJMRHErrorListUrl(JMRHErrorListUrl url) {
        String sql = "insert into jmrh_error_list_url(url) value(?)";
        this.jdbcTemplate.update(sql,url.getUrl());
        LOG.info("专利列表爬取失败：" + url.toString());

    }

    @Override
    public void insertJMRHPatentDetail(JMRHPatentDetail patentDetail) {
        String sql = "insert into jmrh_patent_detail(PID,title,lawStatus,patentType,applyNo,publicNo,applyDate,publicDate,applyPerson,inventionPerson,applyAddress,applyAreaCode,patentee,LC,IPC,CPC,patentAgency,priority,agent,inspector,internationalApplication,internationalPublic,enterDate,divisionApplication) value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        this.jdbcTemplate.update(sql,patentDetail.getPID(),	patentDetail.getTitle(),	patentDetail.getLawStatus(),	patentDetail.getPatentType(),	patentDetail.getApplyNo(),	patentDetail.getPublicNo(),	patentDetail.getApplyDate(),	patentDetail.getPublicDate(),	patentDetail.getApplyPerson(),	patentDetail.getInventionPerson(),	patentDetail.getApplyAddress(),	patentDetail.getApplyAreaCode(),	patentDetail.getPatentee(),	patentDetail.getLC(),	patentDetail.getIPC(),	patentDetail.getCPC(),	patentDetail.getPatentAgency(),	patentDetail.getPriority(),	patentDetail.getAgent(),	patentDetail.getInspector(),	patentDetail.getInternationalApplication(),	patentDetail.getInternationalPublic(),	patentDetail.getEnterDate(),	patentDetail.getDivisionApplication());
        LOG.info("专利详情：" + patentDetail.toString());
    }

    @Override
    public void insertJMRHLawDetail(JMRHLawDetail lawDetail) {
        String sql = "insert into jmrh_law_detail(PID,lawPublicDate,lawStatus,lawStatusInfo) value(?,?,?,?)";
        this.jdbcTemplate.update(sql,lawDetail.getPID(),lawDetail.getLawPublicDate(),lawDetail.getLawStatus(),lawDetail.getLawStatusInfo());
        LOG.info(lawDetail.toString());
    }

    @Override
    public void insertJMRHLawDetailBatch(List<JMRHLawDetail> lawDetails) {
        List<JMRHLawDetail> list = lawDetails;
        String sql = "insert into jmrh_law_detail(PID,lawPublicDate,lawStatus,lawStatusInfo) value(?,?,?,?)";
        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1, list.get(i).getPID());
                preparedStatement.setString(2, list.get(i).getLawPublicDate());
                preparedStatement.setString(3, list.get(i).getLawStatus());
                preparedStatement.setString(4, list.get(i).getLawStatusInfo());
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
        for(JMRHLawDetail lawDetail :list){
            LOG.info(lawDetail.toString());
        }
    }
    @Override
    public void insertJMRHStatistic(JMRHStatistic statistic){
        String sql ="insert into jmrh_statistic(keyword, total) value(?,?)";
        this.jdbcTemplate.update(sql, statistic.getKeyword(), statistic.getTotal());
        LOG.info(statistic.toString());

    }

    @Override
    public void insertJMRHFetchedPage(JMRHFetchedPage page) {
        String sql = "insert into jmrh_fetched_page(keyword,start,pageNum,step,total) value(?,?,?,?,?)";
        this.jdbcTemplate.update(sql,page.getKeyword(),page.getStart(),page.getPageNum(),page.getStep(),page.getTotal());
        LOG.info("爬取结果： " + page.toString());
    }

}
