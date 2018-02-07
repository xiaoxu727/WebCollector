package com.champion.data.crawler.GSJIllegal.util;

import cn.edu.hfut.dmic.webcollector.util.PropertiesUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;

/**
 * Created by champion_xu on 2017/11/27.
 */
public class PropertiesUtil {
    static String PATH="config/config.properties";
    static Properties properties = new Properties();
    public static int ERROR_CODE = PropertiesUtils.ERROR_CODE;
    public static String PROXY_KEY="proxy:Set:ProxySet";
    static {
        File file = new File(PATH);
        System.out.println(file.getAbsoluteFile());
        PropertiesUtils pro = new PropertiesUtils();
    }

    public PropertiesUtil(){
      }
    public static String  getPeroperty(String key){
        return (String)PropertiesUtils.getAsString(PATH,key);
    }

    public static int  getPeropertyAsInt(String key){
        return PropertiesUtils.getAsInt(PATH,key);
    }


    public static void main(String[] args){
        String value = PropertiesUtil.getPeroperty("sh_credit_keyword");
        System.out.print(value);
    }

}
