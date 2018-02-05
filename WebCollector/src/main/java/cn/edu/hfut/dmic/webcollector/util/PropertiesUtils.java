package cn.edu.hfut.dmic.webcollector.util;


import com.sun.deploy.util.SessionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * Created by champion_xu on 2017/11/27.
 */
public class PropertiesUtils {
    public static final Logger LOG = LoggerFactory.getLogger(PropertiesUtils.class);
    public static int ERROR_CODE = -999;
//    public Properties properties = null;


    public static String getAsString( String path, String key){
        Properties properties = new Properties();
        String result = null;
        try {
            InputStreamReader in = new InputStreamReader(new FileInputStream(path),"UTF-8");
//            InputStream in = new BufferedInputStream(new FileInputStream(path));
            properties.load(in);
            result =  (String)properties.get(key);
        }catch (Exception e )
        {
            LOG.info("load properties file failed!");
        }finally {
            return result;
        }

    }

    public static int getAsInt(String path, String key){
        Properties properties = new Properties();
        int result = ERROR_CODE;
        try {
            InputStreamReader in = new InputStreamReader(new FileInputStream(path),"UTF-8");
            properties.load(in);
            result = Integer.valueOf((String)properties.get(key));
        }catch (Exception e )
        {
            LOG.info("load properties file failed!");
        }finally {
            return  result;
        }
    }

}
