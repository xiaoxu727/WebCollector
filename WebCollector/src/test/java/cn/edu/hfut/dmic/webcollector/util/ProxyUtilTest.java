package cn.edu.hfut.dmic.webcollector.util;

import cn.edu.hfut.dmic.webcollector.net.Proxys;
import org.junit.Test;

import java.net.Proxy;

import static org.junit.Assert.*;

public class ProxyUtilTest {

    @Test
    public void proxyTest(){
        Proxy proxy = ProxyUtil.getProxyFromRedis("proxy:Set:ProxySet");
        Proxys proxies = new Proxys();
        proxies.add(proxy);
        Proxy proxy1 = proxies.nextRandom();
        proxies.remove(proxy1);
        System.out.print("");
    }

}