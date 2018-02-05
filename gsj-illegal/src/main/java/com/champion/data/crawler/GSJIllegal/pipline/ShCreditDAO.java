package com.champion.data.crawler.GSJIllegal.pipline;

import com.champion.data.crawler.GSJIllegal.model.*;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by champion_xu on 2017/12/8.
 */
public interface ShCreditDAO {

    public void setDataSource(DataSource dataSource);

    public void insertShCreditPermissionList(ShCreditPermissionList permissionList);

    public void insertShCreditPermissionListBatch(List<ShCreditPermissionList> permissionLists);

    public void insertShCreditPermissionInfo(ShCreditPermissionInfo creditSHPermission);

    public void insertShCreditPublishList(ShCreditPublishList publishList);

    public void insertShCreditPublishListBatch(List<ShCreditPublishList> publishLists);

    public void insertCreditSHPublishInfo(ShCreditPublishInfo publishInfo);

    public void insertShCreditCode(ShCreditCode creditCode);

    public void insertShCreditCodeBatch(List<ShCreditCode> creditCodes);

    public void insertShCreditKeepWord(ShCreditKeepWord creditKeepWord);

    public void insertShCreditKeepWordBatch(List<ShCreditKeepWord> creditKeepWords);

    public void insertShCreditFocus(ShCreditFocus creditFocus);

    public void insertShCreditFocusBatch(List<ShCreditFocus> creditFocuses);

    public void insertShCreditBrokenPromises(ShCreditBrokenPromises creditBrokenPromises);

    public void insertShCreditBrokenPromisesBatch(List<ShCreditBrokenPromises> creditBrokenPromiseses);

    public void insertShCreditCustomsCertificate(ShCreditCustomsCertificate creditCustomsCertificate);

    public void insertShCreditCustomsCertificateBatch(List<ShCreditCustomsCertificate> creditCustomsCertificates);

    public void insertShCreditTaxA(ShCreditTaxA creditTaxA);

    public void insertShCreditTaxABatch(List<ShCreditTaxA> creditTaxAs);

    public void insertShCreditExceptionBusiness(ShCreditExceptionBusiness creditExceptionBusiness);

    public void insertShCreditExceptionBusinessBatch(List<ShCreditExceptionBusiness> creditExceptionBusinesses);

    public void insertShCreditIllegalFoundsRaise(ShCreditIllegalFoundsRaise creditIllegalFoundsRaise);

    public void insertShCreditIllegalFoundsRaiseBatch(List<ShCreditIllegalFoundsRaise> creditIllegalFoundsRaises);

    public void insertShCreditExecutedBrokenPromises(ShCreditExecutedBrokenPromises creditExecutedBrokenPromises);

    public void insertShCreditExecutedBrokenPromisesBatch(List<ShCreditExecutedBrokenPromises> creditExecutedBrokenPromiseses);

    public void insertShCreditIllegalTax(ShCreditIllegalTax creditIllegalTax);

    public void insertShCreditIllegalTaxBatch(List<ShCreditIllegalTax> creditIllegalTaxes);

    public void insertShCreditGovIllegal(ShCreditGovIllegal creditGovIllegal);

    public void insertShCreditGovIllegalBatch(List<ShCreditGovIllegal> creditGovIllegals);



}
