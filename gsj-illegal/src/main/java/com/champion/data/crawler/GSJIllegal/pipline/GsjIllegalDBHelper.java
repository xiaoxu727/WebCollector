package com.champion.data.crawler.GSJIllegal.pipline;

import cn.edu.hfut.dmic.webcollector.util.MysqlHelper;
import com.champion.data.crawler.GSJIllegal.util.PropertiesUtil;

import java.beans.PropertyVetoException;

/**
 * Created by champion_xu on 2017/11/28.
 */
public class GsjIllegalDBHelper extends MysqlHelper {

    static String url = PropertiesUtil.getPeroperty("url");
    static String username = PropertiesUtil.getPeroperty("user");
    static String password = PropertiesUtil.getPeroperty("password");
    static int initialSize = 10;
    static int maxPoolSize = 30;

    public GsjIllegalDBHelper() throws PropertyVetoException {
        super(url,username, password, initialSize,maxPoolSize);
    }

//    public static void main(String[] args){
//        try {
//            GsjIllegalDBHelper gsjIllegal = new GsjIllegalDBHelper();
//            JdbcTemplate jdbcTemplate = gsjIllegal.getTemplate();
//            int updates = jdbcTemplate.update("insert into gsj_illegal(title,department,date,province) VALUE ('title','department','2017-05','上海')");
//            if (updates == 1){
//                System.out.println("success");
//            }
//        }catch (PropertyVetoException e){
//
//        }
//    }
}
