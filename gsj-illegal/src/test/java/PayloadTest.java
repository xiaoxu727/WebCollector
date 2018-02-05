import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.httpclient.methods.PostMethod;
//import org.apache.http.NameValuePair;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.HttpClient;

import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Created by champion_xu on 2017/11/29.
 */

public class PayloadTest {
    public static void main(String[] args) throws IOException, InterruptedException {

//        String loginUrl = "http://ln.gsxt.gov.cn/saicpub/entPublicitySC/entPublicityDC/moreSfzxxgg.action?jyzxdjggNum=19&&entname=&gsaccdept=&tztype=undefined";
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        HttpGet get = new HttpGet(loginUrl);
//
//        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//
//        nvps.add(new BasicNameValuePair("noticesubtype","21"));
//        nvps.add(new BasicNameValuePair("title",""));
//        nvps.add(new BasicNameValuePair("xzqh","ALL"));
//        HttpPost post = new HttpPost(loginUrl);
//        post.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
//        post.setHeader("Content-Type","application/x-www-form-urlencoded");
//
//        CloseableHttpResponse response = httpClient.execute(get);
//        HttpEntity entity = response.getEntity();
//        System.out.print(EntityUtils.toString(entity));
//        response.close();
//        PayloadTest.seleium();
        PostMethod post = new PostMethod( "http://sd.gsxt.gov.cn/pub/notice/page?page=1" );
        NameValuePair[] data = {
                new NameValuePair( "noticesubtype", "21" ),
                new NameValuePair( "title", "" ),
                new NameValuePair( "xzqh", "ALL" ),
        };

        post.setRequestBody(data);
        HttpClient client = new HttpClient();
        int code = client.executeMethod(post);
        System.out.println(code);

    }

    public static void httpunit(String url) throws IOException {
        WebClient webClient = new WebClient();
        webClient.getOptions().setJavaScriptEnabled(true);
        HtmlPage page = webClient.getPage("http://sd.gsxt.gov.cn/pub/notice/");
        System.out.println(page.asText());
        webClient.close();

    }

    public static void seleium() throws InterruptedException {
//        System.setProperty("webdriver.chrome.driver", "crawl/chromedriver.exe");
//        System.setProperty("webdriver.gecko.driver", "crawl/geckodriver.exe");
//        WebDriver driver = new ChromeDriver();
        System.setProperty("phantomjs.binary.path", "tool/phantomjs.exe");
//        WebDriver driver = new HtmlUnitDriver();

        PhantomJSDriver driver = new PhantomJSDriver();
//        WebDriver driver = new FirefoxDriver();
        driver.get("http://sd.gsxt.gov.cn/pub/notice/");
        driver.executeScript("document.getElementById(\"linotice21\").click()");
        Thread.sleep(10000);
//        System.out.print(driver.getPageSource());
        System.out.println(driver.getPageSource());
        driver.get("https://www.hao123.com/");
        System.out.println(driver.getTitle());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        driver.quit();
    }
}
