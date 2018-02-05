package com.champion.data.crawler.GSJIllegal.pipline;

import com.champion.data.crawler.GSJIllegal.model.JMRHPatent;
import com.mongodb.DBCollection;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.mongodb.client.MongoCollection;

import java.util.List;

/**
 * Created by champion_xu on 2018/1/29.
 */
public class MongoApp {
    static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("mongoDB.xml");
    static MongoTemplate jmrhjdbcTemplate = (MongoTemplate) applicationContext.getBean("mongoTemplate");

    public static void insertPatentList(JMRHPatent patent){
        jmrhjdbcTemplate.insert(patent,"patent_list_info");
    }
    public static void insert(Object object){
        jmrhjdbcTemplate.insert(object);
    }

    public static void insertBatch(List<Object> objects){
        jmrhjdbcTemplate.insertAll(objects);
    }
}
