package com.champion.data.crawler.GSJIllegal.pipline;

import com.champion.data.crawler.GSJIllegal.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by champion_xu on 2017/12/8.
 */
public class ShCreditApp {
    static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    static ShCreditJDBCTemplate creditSHJDBCTemplate = (ShCreditJDBCTemplate) applicationContext.getBean("shCreditJDBCTemplate");

    public static void insertShCreditPermissionList(ShCreditPermissionList permissionList) {
        if(permissionList== null){
            return;
        }
        creditSHJDBCTemplate.insertShCreditPermissionList(permissionList);
    }

    public static void insertShCreditPermissionListBatch(List<ShCreditPermissionList> creditSHPermissionLists) {
        if(creditSHPermissionLists.size() == 0){
            return;
        }
        creditSHJDBCTemplate.insertShCreditPermissionListBatch(creditSHPermissionLists);
    }

    public static void insertShCreditPermissionInfo(ShCreditPermissionInfo creditSHPermission) {
        if(creditSHPermission == null){
            return;
        }
        creditSHJDBCTemplate.insertShCreditPermissionInfo(creditSHPermission);

    }

    public static void insertShCreditPublishList(ShCreditPublishList publishList) {
        if(publishList == null){
            return;
        }
        creditSHJDBCTemplate.insertShCreditPublishList(publishList);
    }
    
    public static void insertShCreditPublishListBatch(List<ShCreditPublishList> publishLists) {
        if(publishLists.size() == 0){
            return;
        }
        creditSHJDBCTemplate.insertShCreditPublishListBatch(publishLists);
    }

    public static void insertShCreditPublishInfo(ShCreditPublishInfo publishInfo) {
        if(publishInfo == null){
            return;
        }
        creditSHJDBCTemplate.insertCreditSHPublishInfo(publishInfo);
    }


    public static void insertShCreditCode(ShCreditCode creditCode){
        if(creditCode == null){
            return;
        }
        creditSHJDBCTemplate.insertShCreditCode(creditCode);
    }

    public static  void insertShCreditCodeBatch(List<ShCreditCode> creditCodes){
        if(creditCodes.size() == 0){
            return;
        }
        creditSHJDBCTemplate.insertShCreditCodeBatch(creditCodes);
    }

    public static void insertShCreditKeepWord(ShCreditKeepWord creditKeepWord){
        if(creditKeepWord == null){
            return;
        }
        creditSHJDBCTemplate.insertShCreditKeepWord(creditKeepWord);
    }

    public static void insertShCreditKeepWordBatch(List<ShCreditKeepWord> creditKeepWords){
        if(creditKeepWords.size() == 0){
            return;
        }
        creditSHJDBCTemplate.insertShCreditKeepWordBatch(creditKeepWords);
    }

    public static void insertShCreditFocus(ShCreditFocus creditFocus){
        if(creditFocus == null) {
            return;
        }
        creditSHJDBCTemplate.insertShCreditFocus(creditFocus);
    }

    public static void insertShCreditFocusBatch(List<ShCreditFocus> creditFocuses){
        if(creditFocuses.size() == 0){
            return;
        }
        creditSHJDBCTemplate.insertShCreditFocusBatch(creditFocuses);
    }

    public static void insertShCreditBrokenPromises(ShCreditBrokenPromises creditBrokenPromises){
        if(creditBrokenPromises == null){
            return;
        }
        creditSHJDBCTemplate.insertShCreditBrokenPromises(creditBrokenPromises);

    }

    public static void insertShCreditBrokenPromisesBatch(List<ShCreditBrokenPromises> creditBrokenPromiseses){
        if(creditBrokenPromiseses.size() == 0){
            return;
        }
        creditSHJDBCTemplate.insertShCreditBrokenPromisesBatch(creditBrokenPromiseses);
    }

    public static void insertShCreditCustomsCertificate(ShCreditCustomsCertificate creditCustomsCertificate){
        if(creditCustomsCertificate == null){
            return;
        }
        creditSHJDBCTemplate.insertShCreditCustomsCertificate(creditCustomsCertificate);
    }

    public static void insertShCreditCustomsCertificateBatch(List<ShCreditCustomsCertificate> creditCustomsCertificates){
        if(creditCustomsCertificates.size() == 0){
            return;
        }
        creditSHJDBCTemplate.insertShCreditCustomsCertificateBatch(creditCustomsCertificates);
    }

    public static void ShCreditTaxA(ShCreditTaxA creditTaxA){
        if(creditTaxA == null){
            return;
        }
        creditSHJDBCTemplate.insertShCreditTaxA(creditTaxA);
    }

    public static void insertShCreditTaxABatch(List<ShCreditTaxA> creditTaxAs){
        if(creditTaxAs.size()==0){
            return;
        }
        creditSHJDBCTemplate.insertShCreditTaxABatch(creditTaxAs);
    }

    public static void insertShCreditExceptionBusiness(ShCreditExceptionBusiness creditExceptionBusiness){
        if(creditExceptionBusiness == null){
            return;
        }
        creditSHJDBCTemplate.insertShCreditExceptionBusiness(creditExceptionBusiness);
    }

    public static void insertShCreditExceptionBusinessBatch(List<ShCreditExceptionBusiness> creditExceptionBusinesses){
        if(creditExceptionBusinesses.size() == 0){
            return;
        }
        creditSHJDBCTemplate.insertShCreditExceptionBusinessBatch(creditExceptionBusinesses);
    }

    public static void insertShCreditIllegalFoundsRaise(ShCreditIllegalFoundsRaise creditIllegalFoundsRaise){
        if(creditIllegalFoundsRaise == null){
            return;
        }
        creditSHJDBCTemplate.insertShCreditIllegalFoundsRaise(creditIllegalFoundsRaise);
    }

    public static void insertShCreditIllegalFoundsRaiseBatch(List<ShCreditIllegalFoundsRaise> creditIllegalFoundsRaises){
        if(creditIllegalFoundsRaises.size() == 0){
            return;
        }
        creditSHJDBCTemplate.insertShCreditIllegalFoundsRaiseBatch(creditIllegalFoundsRaises);
    }

    public static void insertShCreditExecutedBrokenPromises(ShCreditExecutedBrokenPromises creditExecutedBrokenPromises){
        if(creditExecutedBrokenPromises == null){
            return;
        }
        creditSHJDBCTemplate.insertShCreditExecutedBrokenPromises(creditExecutedBrokenPromises);
    }

    public static void insertShCreditExecutedBrokenPromisesBatch(List<ShCreditExecutedBrokenPromises> creditExecutedBrokenPromiseses){
        if(creditExecutedBrokenPromiseses.size() == 0){
            return;
        }
        creditSHJDBCTemplate.insertShCreditExecutedBrokenPromisesBatch(creditExecutedBrokenPromiseses);
    }

    public static void insertShCreditIllegalTax(ShCreditIllegalTax creditIllegalTax){
        if(creditIllegalTax ==  null){
            return;
        }
        creditSHJDBCTemplate.insertShCreditIllegalTax(creditIllegalTax);
    }

    public static void insertShCreditIllegalTaxBatch(List<ShCreditIllegalTax> creditIllegalTaxes){
        if(creditIllegalTaxes.size() == 0){
            return;
        }
        creditSHJDBCTemplate.insertShCreditIllegalTaxBatch(creditIllegalTaxes);
    }

    public static void insertShCreditGovIllegal(ShCreditGovIllegal creditGovIllegal){
        if(creditGovIllegal ==  null){
            return;
        }
        creditSHJDBCTemplate.insertShCreditGovIllegal(creditGovIllegal);
    }

    public static void insertShCreditGovIllegalBatch(List<ShCreditGovIllegal> creditGovIllegals){
        if(creditGovIllegals.size() == 0){
            return;
        }
        creditSHJDBCTemplate.insertShCreditGovIllegalBatch(creditGovIllegals);
    }



}
