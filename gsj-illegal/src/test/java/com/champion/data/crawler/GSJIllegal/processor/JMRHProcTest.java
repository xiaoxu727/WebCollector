package com.champion.data.crawler.GSJIllegal.processor;

import com.champion.data.crawler.GSJIllegal.util.PropertiesUtil;
import java.io.*;

public class JMRHProcTest {

    @org.junit.Test
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