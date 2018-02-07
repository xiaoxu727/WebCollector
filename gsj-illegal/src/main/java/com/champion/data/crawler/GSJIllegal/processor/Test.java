package com.champion.data.crawler.GSJIllegal.processor;

import com.champion.data.crawler.GSJIllegal.util.PropertiesUtil;

import java.io.*;
import java.net.URLEncoder;

/**
 * Created by champion_xu on 2018/1/25.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        Test test = new Test();
        test.readKeywordTest();

    }
    public  void endcode() throws UnsupportedEncodingException {
        System.out.println(URLEncoder.encode("申请人", "UTF-8"));

        System.out.println("申请人");

    }

    public void readKeywordTest() throws IOException {
        String keywordPath = PropertiesUtil.getPeroperty("jmnr_keyword");
        File keywordFile = new File(keywordPath);
        BufferedReader kewordReader = new BufferedReader(new FileReader(keywordFile));
        String keyword = null;
        while ( (keyword = kewordReader.readLine()) != null ) {
            System.out.println(keyword);
        }
    }

}
