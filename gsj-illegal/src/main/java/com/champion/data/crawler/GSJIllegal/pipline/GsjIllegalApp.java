package com.champion.data.crawler.GSJIllegal.pipline;
import com.champion.data.crawler.GSJIllegal.model.GsjIllegal;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by champion_xu on 2017/11/28.
 */
public class GsjIllegalApp {
    static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    static GsjIllegalJDBCTempalte gsjIllegalJDBCTempalte = (GsjIllegalJDBCTempalte) applicationContext.getBean("gsjIllegalJDBCTempalte");

    public static void create(GsjIllegal obj){
        gsjIllegalJDBCTempalte.create(obj);
    }

    public static void update(String contentUrl, String content){
        gsjIllegalJDBCTempalte.update(contentUrl, content);
    }

    public static void createBatch(List<GsjIllegal> gsjIllegalList){
        if(gsjIllegalList.size() > 0) {
            gsjIllegalJDBCTempalte.createBatch(gsjIllegalList);
        }
    }

    public static void main(String[] args){
        GsjIllegal gsjIllegal = new GsjIllegal();
        gsjIllegal.setTitle("test");
        gsjIllegal.setDate("test");
        gsjIllegal.setDepartment("test");
        GsjIllegal gsjIllegal2 = new GsjIllegal();
        gsjIllegal2.setTitle("test2");
        gsjIllegal2.setDate("test2");
        gsjIllegal2.setDepartment("test2");
        List<GsjIllegal> list = new ArrayList<GsjIllegal>();
        list.add(gsjIllegal);
        list.add(gsjIllegal2);
        GsjIllegalApp.createBatch(list);
    }
}
