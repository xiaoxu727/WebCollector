import cn.edu.hfut.dmic.webcollector.util.GsonUtils;
import com.google.gson.JsonObject;
//import org.dom4j.*;
//import org.dom4j.Document;
//import org.dom4j.Element;


import javax.print.Doc;
import javax.xml.parsers.SAXParser;
import javax.xml.transform.sax.SAXResult;
import java.io.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLSyntaxErrorException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URLEncodedUtils;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.jsoup.*;

import java.util.regex.Pattern;

//import org.dom4j.Attribute;
//import org.dom4j.io.SAXReader;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.xml.sax.*;
import javax.xml.*;

/**
 * Created by champion_xu on 2017/11/29.
 */
public class test {
    public static void main(String[] args) throws IOException, ParseException {
        test t = new test();
        t.endcode();
    }

    static String CREDIT_CODE_URL = "http://www.shcredit.gov.cn/credit/f/credit/query/?model=tyshxydm&page=%d&keywords=%s";
    static String KEEP_WORD_URL = "http://www.shcredit.gov.cn/credit/f/credit/query/?model=sxmdcx&page=%d&keywords=%s";
    static String PATENT_DATA_LIST_KEYWORD_URL ="http://so.iptrm.com/txnPatentData01.ajax?secondKeyWord=&secondkeyWordVal=%s&secondSearchType=NOT&express2=&express=%s&isFamily=&categoryIndex=%s&selectedCategory=&patentLib=&patentType=&order=&pdbt=&attribute-node:patent_cache-flag=false&attribute-node:patent_start-row=1&attribute-node:patent_page-row=10&attribute-node:patent_sort-column=ano&attribute-node:patent_page=1";

    static HashMap map = new HashMap<String,String>();
    static {
        map.put("creditCode",CREDIT_CODE_URL);
        map.put("keepWord",KEEP_WORD_URL);
    }
    public test(){
    }

    @Test
    public void xmlTest() throws IOException, DocumentException {
        File file = new File("config/test.xml");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String content = "";
        String line = "";
        while((line = reader.readLine())!= null){
            content += line;
        }
        if(content.startsWith("null")){
            content = content.substring("null".length());
        }
        org.dom4j.Document doc = DocumentHelper.parseText(content);
        org.dom4j.Element rootEle = doc.getRootElement();
        List<org.dom4j.Element> patentNodes = rootEle.elements("patent");
        for(org.dom4j.Element node : patentNodes){
            System.out.println(node.getText());
        }

    }

    public static void dateCal() throws ParseException {
        Date d=new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
        Date endDate = df.parse("19700101");
        while (d.after(endDate) ){
            d = new Date(d.getTime() - 1 * 24 * 60 * 60 * 1000);
            System.out.println("日期："+df.format(d));
        }
    }
    @Test
    public  void endcode(){
//        System.out.print(com.sun.deploy.net.URLEncoder);
        System.out.println(URLEncoder.encode("申请人"));
    }

}
