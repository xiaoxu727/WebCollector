package cn.edu.hfut.dmic.webcollector.util;

/**
 * Created by champion_xu on 2018/1/24.
 */
import cn.edu.hfut.dmic.webcollector.plugin.redis.RedisDBUtils;
import com.google.gson.JsonElement;

import java.net.InetSocketAddress;
import java.net.Proxy;

public class ProxyUtil {

    public ProxyUtil(){

    }
    public static Proxy parse(String content, String hostFiled , String portField){
        Proxy proxy = null;
        try {
            JsonElement jsonElement = GsonUtils.parse(content);
            String host = jsonElement.getAsJsonObject().get(hostFiled).getAsString();
            int port = jsonElement.getAsJsonObject().get(portField).getAsInt();
             proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));

        }catch (Exception e){

        }
        return proxy;
    }

    public static Proxy parse(String content){

        return ProxyUtil.parse(content,"host","port");
    }

    public static Proxy getProxyFromRedis(String key){
         String content = RedisDBUtils.getValueFromSet(key);
        return ProxyUtil.parse(content);
    }

}
