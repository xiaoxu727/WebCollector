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
 * Created by champion_xu on 2017/12/8.
 */
class ShCreditJDBCTemplate implements ShCreditDAO {
    public static final Logger LOG = LoggerFactory.getLogger(ShCreditJDBCTemplate.class);
    DataSource dataSource ;
    private JdbcTemplate jdbcTemplate ;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void insertShCreditPermissionList(ShCreditPermissionList permissionList) {
        String sql = "insert into credit_sh_permission_list(xkid,xkwsh,xmmc,xzxdr,detailUrl) value(?,?,?,?,?)";
        this.jdbcTemplate.update(permissionList.getXkid(),permissionList.getXkwsh(),permissionList.getXmmc(),permissionList.getXzxdr(),permissionList.getDetailUrl());
        LOG.info("插入数据成功：" + permissionList.toString());
    }

    @Override
    public void insertShCreditPermissionListBatch(List<ShCreditPermissionList> permissionLists) {
        List<ShCreditPermissionList> list = permissionLists;
        String sql = "insert into credit_sh_permission_list(xkid,xkwsh,xmmc,xzxdr,detailUrl) value(?,?,?,?,?)";
        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1,list.get(i).getXkid());
                preparedStatement.setString(2,list.get(i).getXkwsh());
                preparedStatement.setString(3,list.get(i).getXmmc());
                preparedStatement.setString(4,list.get(i).getXzxdr());
                preparedStatement.setString(5,list.get(i).getDetailUrl());
            }

            @Override
            public int getBatchSize() {
               return list.size();
            }
        });
        for(ShCreditPermissionList obj : permissionLists){
            LOG.info("插入数据成功：" + obj.toString());
        }


    }
    @Override
    public void insertShCreditPermissionInfo(ShCreditPermissionInfo creditSHPermission) {
        String sql = "insert into credit_sh_permission_info(xkid,xkwsh,xmmc,xzxdr,url,splb,xkjdrq,xkjg,xkjzrq,xknr) value(?,?,?,?,?,?,?,?,?,?)";
        this.jdbcTemplate.update(sql,creditSHPermission.getXfid(), creditSHPermission.getXkwsh(), creditSHPermission.getXmmc(),creditSHPermission.getXzxdr(),creditSHPermission.getUrl(),
                creditSHPermission.getSplb(),creditSHPermission.getXkjdrq(),creditSHPermission.getXkjg(),creditSHPermission.getXkjzrq(),creditSHPermission.getXknr());
        LOG.info("插入数据成功：" + creditSHPermission.toString());
    }

    @Override
    public void insertShCreditPublishList(ShCreditPublishList publishList) {
        String sql ="insert into credit_sh_publish_list(cfid,cfmc,cfwsh,xzxdr,deatilUrl) value (?,?,?,?,?)";
        this.jdbcTemplate.update(sql,publishList.getCfid(),publishList.getCfmc(),publishList.getCfwsh(),publishList.getXzxdr(),publishList.getDetailUrl());
        LOG.info("插入数据成功：" + publishList.toString());
    }

    @Override
    public void insertShCreditPublishListBatch(List<ShCreditPublishList> publishLists) {
        List<ShCreditPublishList> list = publishLists;
        String sql ="insert into credit_sh_publish_list(cfid,cfmc,cfwsh,xzxdr,deatilUrl) value (?,?,?,?,?)";
        this.jdbcTemplate.batchUpdate(sql,new BatchPreparedStatementSetter(){
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1,list.get(i).getCfid());
                preparedStatement.setString(2,list.get(i).getCfmc());
                preparedStatement.setString(3,list.get(i).getCfwsh());
                preparedStatement.setString(4,list.get(i).getXzxdr());
                preparedStatement.setString(5,list.get(i).getDetailUrl());
            }
            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
        for (ShCreditPublishList publishList : list){
            LOG.info("插入数据成功：" + publishList.toString());
        }

    }
    @Override
    public void insertCreditSHPublishInfo(ShCreditPublishInfo publishInfo) {
        String sql = "insert into credit_sh_publish_info(cfid,cfmc,cfwsh,xzxdr,deatilUrl,cfjdrq,cfjguan,cflb,cfsy,cfyj,url) value(?,?,?,?,?,?,?,?,?,?,?)";
        this.jdbcTemplate.update(sql,publishInfo.getCfid(),publishInfo.getCfmc(),publishInfo.getCfwsh(),publishInfo.getXzxdr(),publishInfo.getDetailUrl(),
                publishInfo.getCfjdrq(),publishInfo.getCfjguan(),publishInfo.getCflb(),publishInfo.getCfsy(),publishInfo.getCfyj(),publishInfo.getUrl());
    }

    @Override
    public void insertShCreditCode(ShCreditCode creditCode) {
        String sql = "insert into credit_sh_code(cname,regisCode,creditCode,orgCode,taxCdoe) value(?,?,?,?,?)";
        this.jdbcTemplate.update(sql,creditCode.getCname(),creditCode.getRegisCode(),creditCode.getCreditCode(),creditCode.getOrgCode(),creditCode.getTaxCdoe());
        LOG.info("统一社会信用代码插入数据成功：" + creditCode.toString());
    }

    @Override
    public void insertShCreditCodeBatch(List<ShCreditCode> creditCodes) {
        List<ShCreditCode> list = creditCodes;
        String sql = "insert into credit_sh_code(cname,regisCode,creditCode,orgCode,taxCdoe) value(?,?,?,?,?)";
        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1,list.get(i).getCname());
                preparedStatement.setString(2,list.get(i).getRegisCode());
                preparedStatement.setString(3,list.get(i).getCreditCode());
                preparedStatement.setString(4,list.get(i).getOrgCode());
                preparedStatement.setString(5,list.get(i).getTaxCdoe());
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
        for(ShCreditCode creditCode : list){
            LOG.info("统一社会信用代码插入数据成功：" + creditCode.toString());
        }
    }

    @Override
    public void insertShCreditKeepWord(ShCreditKeepWord creditKeepWord) {
        String sql = "insert into  credit_sh_keep_word(cname, creditCode, KeepWordCount) value(?,?,?)";
        this.jdbcTemplate.update(sql,creditKeepWord.getCname(),creditKeepWord.getCreditCode(),creditKeepWord.getKeepWordCount());
        LOG.info("守信名单代码插入数据成功：" + creditKeepWord.toString());
    }

    @Override
    public void insertShCreditKeepWordBatch(List<ShCreditKeepWord> creditKeepWords) {
        List<ShCreditKeepWord> list = creditKeepWords;
        String sql = "insert into  credit_sh_keep_word(cname, creditCode, KeepWordCount) value(?,?,?)";
        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1,list.get(i).getCname());
                preparedStatement.setString(2,list.get(i).getCreditCode());
                preparedStatement.setString(3,list.get(i).getKeepWordCount());


            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
        for(ShCreditKeepWord keepWord : list){
            LOG.info("守信名单插入数据成功：" + keepWord.toString());
        }

    }

    @Override
    public void insertShCreditFocus(ShCreditFocus creditFocus) {
        String sql = "insert into  credit_sh_focus (cname, creditCode, typeCount) value(?,?,?)";
        this.jdbcTemplate.update(sql, creditFocus.getCname(), creditFocus.getCreditCode(), creditFocus.getTypeCount());
        LOG.info("重点关注单插入数据成功：" + creditFocus.toString());
    }

    @Override
    public void insertShCreditFocusBatch(List<ShCreditFocus> creditFocuses) {
        List<ShCreditFocus> list = creditFocuses;
        String sql = "insert into  credit_sh_focus (cname, creditCode, typeCount) value(?,?,?)";
        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1,list.get(i).getCname());
                preparedStatement.setString(2,list.get(i).getCreditCode());
                preparedStatement.setString(3,list.get(i).getTypeCount());
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });

        for(ShCreditFocus creditFocus : list) {
            LOG.info("重点关注单插入数据成功：" + creditFocus.toString());
        }

    }

    @Override
    public void insertShCreditBrokenPromises(ShCreditBrokenPromises creditBrokenPromises) {
        String sql = "insert into  credit_sh_broken_promises (cname, creditCode, typeCount) value(?,?,?)";
        this.jdbcTemplate.update(sql, creditBrokenPromises.getCname(), creditBrokenPromises.getCreditCode(), creditBrokenPromises.getTypeCount());
        LOG.info("失信插入数据成功：" + creditBrokenPromises.toString());
    }

    @Override
    public void insertShCreditBrokenPromisesBatch(List<ShCreditBrokenPromises> creditBrokenPromiseses) {
        List<ShCreditBrokenPromises> list = creditBrokenPromiseses;
        String sql = "insert into  credit_sh_broken_promises (cname, creditCode, typeCount) value(?,?,?)";
        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1,list.get(i).getCname());
                preparedStatement.setString(2,list.get(i).getCreditCode());
                preparedStatement.setString(3,list.get(i).getTypeCount());
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });

        for(ShCreditBrokenPromises creditBrokenPromises : list) {
            LOG.info("失信插入数据成功：" + creditBrokenPromises.toString());
        }
    }

    @Override
    public void insertShCreditCustomsCertificate(ShCreditCustomsCertificate creditCustomsCertificate) {
        String sql = "insert into  credit_sh_customs_certificate (cname, creditCode, levelDate) value(?,?,?)";
        this.jdbcTemplate.update(sql, creditCustomsCertificate.getCname(), creditCustomsCertificate.getCreditCode(), creditCustomsCertificate.getLevelDate());
        LOG.info("海关高级企业认证插入数据成功：" + creditCustomsCertificate.toString());

    }

    @Override
    public void insertShCreditCustomsCertificateBatch(List<ShCreditCustomsCertificate> creditCustomsCertificates) {
        List<ShCreditCustomsCertificate> list = creditCustomsCertificates;
        String sql = "insert into  credit_sh_customs_certificate (cname, creditCode, levelDate) value(?,?,?)";
        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1,list.get(i).getCname());
                preparedStatement.setString(2,list.get(i).getCreditCode());
                preparedStatement.setString(3,list.get(i).getLevelDate());
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });

        for(ShCreditCustomsCertificate creditCustomsCertificate : list) {
            LOG.info("海关高级企业认证插入数据成功：" + creditCustomsCertificate.toString());
        }
    }

    @Override
    public void insertShCreditTaxA(ShCreditTaxA creditTaxA) {
        String sql = "insert into  credit_sh_taxA (cname, creditCode, year) value(?,?,?)";
        this.jdbcTemplate.update(sql, creditTaxA.getCname(), creditTaxA.getCreditCode(), creditTaxA.getYear());
        LOG.info("税务A级纳税人插入数据成功：" + creditTaxA.toString());
    }

    @Override
    public void insertShCreditTaxABatch(List<ShCreditTaxA> creditTaxAs) {
        List<ShCreditTaxA> list = creditTaxAs;
        String sql = "insert into  credit_sh_taxA (cname, creditCode, year) value(?,?,?)";
        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1,list.get(i).getCname());
                preparedStatement.setString(2,list.get(i).getCreditCode());
                preparedStatement.setString(3,list.get(i).getYear());
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });

        for(ShCreditTaxA creditTaxA : list) {
            LOG.info("税务A级纳税人插入数据成功：" + creditTaxA.toString());
        }
    }

    @Override
    public void insertShCreditExceptionBusiness(ShCreditExceptionBusiness creditExceptionBusiness) {
        String sql = "insert into credit_sh_exception_business (cname, creditCode, inTime) value(?,?,?)";
        this.jdbcTemplate.update(sql, creditExceptionBusiness.getCname(), creditExceptionBusiness.getCreditCode(), creditExceptionBusiness.getInTime());
        LOG.info("经营异常插入数据成功：" + creditExceptionBusiness.toString());
    }

    @Override
    public void insertShCreditExceptionBusinessBatch(List<ShCreditExceptionBusiness> creditExceptionBusinesses) {

        List<ShCreditExceptionBusiness> list = creditExceptionBusinesses;
        String sql = "insert into credit_sh_exception_business (cname, creditCode, inTime) value(?,?,?)";
        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1,list.get(i).getCname());
                preparedStatement.setString(2,list.get(i).getCreditCode());
                preparedStatement.setString(3,list.get(i).getInTime());
            }
            @Override
            public int getBatchSize() {
                return list.size();
            }
        });

        for(ShCreditExceptionBusiness creditExceptionBusiness : list) {
            LOG.info("经营异常插入数据成功：" + creditExceptionBusiness.toString());
        }
    }

    @Override
    public void insertShCreditIllegalFoundsRaise(ShCreditIllegalFoundsRaise creditIllegalFoundsRaise) {
        String sql = "insert into credit_sh_illegal_founds_raise (cname, creditCode, province) value(?,?,?)";
        this.jdbcTemplate.update(sql, creditIllegalFoundsRaise.getCname(), creditIllegalFoundsRaise.getCreditCode(), creditIllegalFoundsRaise.getProvince());
        LOG.info("非法集资插入数据成功：" + creditIllegalFoundsRaise.toString());
    }

    @Override
    public void insertShCreditIllegalFoundsRaiseBatch(List<ShCreditIllegalFoundsRaise> creditIllegalFoundsRaises) {

        List<ShCreditIllegalFoundsRaise> list = creditIllegalFoundsRaises;
        String sql = "insert into credit_sh_illegal_founds_raise (cname, creditCode, province) value(?,?,?)";
        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1,list.get(i).getCname());
                preparedStatement.setString(2,list.get(i).getCreditCode());
                preparedStatement.setString(3,list.get(i).getProvince());
            }
            @Override
            public int getBatchSize() {
                return list.size();
            }
        });

        for(ShCreditIllegalFoundsRaise creditIllegalFoundsRaise : list) {
            LOG.info("经营异常插入数据成功：" + creditIllegalFoundsRaise.toString());
        }
    }

    @Override
    public void insertShCreditExecutedBrokenPromises(ShCreditExecutedBrokenPromises creditExecutedBrokenPromises) {
        String sql = "insert into credit_sh_executed_broken_promises (cname, creditCode, caseNO) value(?,?,?)";
        this.jdbcTemplate.update(sql, creditExecutedBrokenPromises.getCname(), creditExecutedBrokenPromises.getCreditCode(), creditExecutedBrokenPromises.getCaseNO());
        LOG.info("失信被执行人插入数据成功：" + creditExecutedBrokenPromises.toString());

    }

    @Override
    public void insertShCreditExecutedBrokenPromisesBatch(List<ShCreditExecutedBrokenPromises> executedBrokenPromiseses) {

        List<ShCreditExecutedBrokenPromises> list = executedBrokenPromiseses;
        String sql = "insert into credit_sh_executed_broken_promises (cname, creditCode, caseNO) value(?,?,?)";
        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1,list.get(i).getCname());
                preparedStatement.setString(2,list.get(i).getCreditCode());
                preparedStatement.setString(3,list.get(i).getCaseNO());
            }
            @Override
            public int getBatchSize() {
                return list.size();
            }
        });

        for(ShCreditExecutedBrokenPromises creditExecutedBrokenPromises : list) {
            LOG.info("失信被执行人插入数据成功：" + creditExecutedBrokenPromises.toString());
        }
    }

    @Override
    public void insertShCreditIllegalTax(ShCreditIllegalTax creditIllegalTax) {
        String sql = "insert into credit_sh_illegal_tax (cname, creditCode, caseStatus) value(?,?,?)";
        this.jdbcTemplate.update(sql, creditIllegalTax.getCname(), creditIllegalTax.getCreditCode(), creditIllegalTax.getCaseStatus());
        LOG.info("重大税收违法案件插入数据成功：" + creditIllegalTax.toString());

    }

    @Override
    public void insertShCreditIllegalTaxBatch(List<ShCreditIllegalTax> creditIllegalTaxes) {
        List<ShCreditIllegalTax> list = creditIllegalTaxes;
        String sql = "insert into credit_sh_illegal_tax (cname, creditCode, caseStatus) value(?,?,?)";
        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1,list.get(i).getCname());
                preparedStatement.setString(2,list.get(i).getCreditCode());
                preparedStatement.setString(3,list.get(i).getCaseStatus());
            }
            @Override
            public int getBatchSize() {
                return list.size();
            }
        });

        for(ShCreditIllegalTax creditIllegalTax : list) {
            LOG.info("失信被执行人插入数据成功：" + creditIllegalTax.toString());
        }

    }

    @Override
    public void insertShCreditGovIllegal(ShCreditGovIllegal creditGovIllegal) {
        String sql = "insert into credit_sh_gov_illegal (cname, creditCode, inReason) value(?,?,?)";
        this.jdbcTemplate.update(sql, creditGovIllegal.getCname(), creditGovIllegal.getCreditCode(), creditGovIllegal.getInReason());
        LOG.info("政府采购严重违法失信插入数据成功：" + creditGovIllegal.toString());

    }

    @Override
    public void insertShCreditGovIllegalBatch(List<ShCreditGovIllegal> creditGovIllegals) {
        List<ShCreditGovIllegal> list = creditGovIllegals;
        String sql = "insert into credit_sh_gov_illegal (cname, creditCode, inReason) value(?,?,?)";
        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1,list.get(i).getCname());
                preparedStatement.setString(2,list.get(i).getCreditCode());
                preparedStatement.setString(3,list.get(i).getInReason());
            }
            @Override
            public int getBatchSize() {
                return list.size();
            }
        });

        for(ShCreditGovIllegal creditGovIllegal : list) {
            LOG.info("失信被执行人插入数据成功：" + creditGovIllegal.toString());
        }

    }

}
