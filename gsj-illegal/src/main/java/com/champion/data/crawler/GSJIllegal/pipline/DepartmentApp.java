package com.champion.data.crawler.GSJIllegal.pipline;

import com.champion.data.crawler.GSJIllegal.model.DepartmentInfo;
import com.champion.data.crawler.GSJIllegal.model.DepartmentList;
import com.champion.data.crawler.GSJIllegal.model.DepartmentReportInfo;
import com.champion.data.crawler.GSJIllegal.model.DepartmentReportList;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by champion_xu on 2018/1/10.
 */
public class DepartmentApp {
    static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    static DepatmentJDBCTemplate tempalte = (DepatmentJDBCTemplate) applicationContext.getBean("departmentJDBCTemplate");
   
    public static void insertDepartmentList(DepartmentList departmentList) {
        if(departmentList == null){
            return;
        }
        tempalte.insertDepartmentList(departmentList);
    }

    public static void insertDepartmentInfo(DepartmentInfo departmentInfo) {
        if(departmentInfo == null){
            return;
        }
        tempalte.insertDepartmentInfo(departmentInfo);
    }

    public  static void insertDepartmentListBatch(List<DepartmentList> departmentListList) {
        if(departmentListList == null || departmentListList.size() == 0){
            return;
        }
        tempalte.insertDepartmentListBatch(departmentListList);
    }

    public static void insertDepartmentReportListBatch(List<DepartmentReportList> departmentReportLists){
        if(departmentReportLists == null || departmentReportLists.size() == 0){
            return;
        }
        tempalte.insertDepartmentReportListBatch(departmentReportLists);
    }
    public static void insertDepartmentReportList(DepartmentReportList departmentReportList) {
        if(departmentReportList == null){
            return;
        }
        tempalte.insertDepartmentReportList(departmentReportList);
    }

    public static void insertDepartmentReportInfo(DepartmentReportInfo departmentReportInfo) {
        if(departmentReportInfo == null){
            return;
        }
        tempalte.insertDepartmentReportInfo(departmentReportInfo);
    }
    
}
