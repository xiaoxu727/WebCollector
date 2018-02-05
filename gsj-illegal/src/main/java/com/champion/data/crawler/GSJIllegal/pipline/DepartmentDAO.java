package com.champion.data.crawler.GSJIllegal.pipline;

import com.champion.data.crawler.GSJIllegal.model.DepartmentInfo;
import com.champion.data.crawler.GSJIllegal.model.DepartmentList;
import com.champion.data.crawler.GSJIllegal.model.DepartmentReportInfo;
import com.champion.data.crawler.GSJIllegal.model.DepartmentReportList;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by champion_xu on 2018/1/10.
 */
public interface DepartmentDAO {
    public void setDataSource(DataSource dataSource);

    public void insertDepartmentList(DepartmentList departmentList);

    public void insertDepartmentInfo(DepartmentInfo departmentInfo);

    public void insertDepartmentReportList(DepartmentReportList departmentReportList);

    public void insertDepartmentReportInfo(DepartmentReportInfo departmentReportInfo);

    public void insertDepartmentListBatch(List<DepartmentList> departmentListList);

    public void insertDepartmentReportListBatch(List<DepartmentReportList> departmentReportLists);

}



