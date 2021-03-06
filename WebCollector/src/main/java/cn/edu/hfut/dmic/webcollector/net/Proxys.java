/*
 * Copyright (C) 2014 hu
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package cn.edu.hfut.dmic.webcollector.net;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Random;

import cn.edu.hfut.dmic.webcollector.plugin.redis.RedisDBUtils;
import cn.edu.hfut.dmic.webcollector.util.ProxyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author xuxingyuan
 */
public class Proxys extends ArrayList<Proxy> {
    public static final Logger LOG=LoggerFactory.getLogger(Proxys.class);

    public static Random random = new Random();


    int maxSize = 10;

    String redisKey = null;

    public Proxys(String redisKey, int maxSize){
        this.maxSize = maxSize;
        this.redisKey = redisKey;
    }
    public Proxys(String redisKey){
        this.redisKey = redisKey;
    }

    public Proxys(){

    }

    public Proxy nextRandom(){
        if(this.size() >0){
            int r=random.nextInt(this.size());
            return this.get(r);
        }
        return null;

    }
    
    public void addEmpty(){
        Proxy nullProxy=null;
        this.add(nullProxy);
    }

    public void add(String ip, int port) {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
        this.add(proxy);
    }

    public void remove(Proxy proxy){
        for(int i =0; i <this.size(); i++){
            if(proxy.equals(this.get(i))){
                this.remove(i);
            }
        }
    }

    public Proxy pop(){
        if(this.size() == 0 && redisKey != null){
            addFromRedis(redisKey,maxSize);
        }
        if(this.size() > 0){
            int r=random.nextInt(this.size());
            Proxy proxy = this.get(r);
            this.remove(r);
            return proxy;
        }
        return null;
    }

    public void add(String proxyStr) throws Exception {
        try {
            String[] infos = proxyStr.split(":");
            String ip = infos[0];
            int port = Integer.valueOf(infos[1]);

            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
            this.add(proxy);
        } catch (Exception ex) {
            LOG.info("Exception", ex);
        }

    }

    public void addAllFromFile(File file) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line = null;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.startsWith("#")||line.isEmpty()) {
                continue;
            } else {
                this.add(line);
            }
        }
    }
    public void addFromRedis(String key){
        Proxy proxy = ProxyUtil.getProxyFromRedis(key);
        if(proxy != null){
            this.add(proxy);
        }
    }
    public void addFromRedis(String key, int count){
        for(int i = 0 ;i < count ; i ++){
            Proxy proxy = ProxyUtil.getProxyFromRedis(key);
            if(proxy != null){
                this.add(proxy);
            }
        }
    }

    public String getRedisKey() {
        return redisKey;
    }

    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }


}
