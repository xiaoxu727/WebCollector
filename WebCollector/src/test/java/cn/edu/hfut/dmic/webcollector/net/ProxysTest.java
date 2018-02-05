package cn.edu.hfut.dmic.webcollector.net;

import org.junit.Test;

import java.net.Proxy;

import static org.junit.Assert.*;

public class ProxysTest {

    @Test
    public void test(){
        Proxys proxies = new Proxys();
        Proxy proxy = proxies.pop();
        System.out.print(proxy.toString());

    }

}