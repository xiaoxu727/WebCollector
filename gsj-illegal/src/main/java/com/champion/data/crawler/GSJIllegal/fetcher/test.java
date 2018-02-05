package com.champion.data.crawler.GSJIllegal.fetcher;

import java.io.File;

/**
 * Created by champion_xu on 2017/11/29.
 */
public class test {
    public static void main(String[] args){
//        String text = "openDetail('2','2','1020721','6101310000009399')";
//        text =text.replace("openDetail(","").replace("'","").replace(")","");
//        String[] texts = text.split(",");
//        for(String str : texts){
//            System.out.println(str);
//        }
//
//        String[] lists = {"上海","北京"};
//        System.out.print(Arrays.asList(lists).contains("上海"));
//        List<String > provices = new ArrayList<>(Arrays.asList(lists));
////        System.out.print(provices.contains("上海"));
//        System.setProperty("phantomjs.binary.path", "tool/phantomjs.exe");
//        PhantomJSDriver driverc = new PhantomJSDriver();
//        System.out.print(driverc instanceof RemoteWebDriver);
        File file = new File("tool");
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getPath());


    }
}
