package com.champion.data.crawler.GSJIllegal.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by champion_xu on 2018/2/6.
 */
public class Tools {

    public static String UrlEncode(String str)   {
        String strEcode = null;
        try{
            if(str != null){
                strEcode = URLEncoder.encode(str, "UTF-8");
            }

        } catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }finally {
            return  strEcode;
        }
    }
}
