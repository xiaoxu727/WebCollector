package cn.edu.hfut.dmic.webcollector.net;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.Proxy;

/**
 * Created by champion_xu on 2017/12/6.
 */
public class SeleniumRequest extends HttpRequest {

    public SeleniumRequest(String url) throws Exception {
        super(url);
    }

    public SeleniumRequest(String url, Proxy proxy) throws Exception {
        super(url, proxy);
    }

    public SeleniumRequest(CrawlDatum crawlDatum) throws Exception {
        super(crawlDatum);
    }

    public SeleniumRequest(CrawlDatum crawlDatum, Proxy proxy) throws Exception {
        super(crawlDatum, proxy);
    }

    public HttpResponse response() throws Exception{

        HttpResponse response = new HttpResponse();
        WebDriver driver = null;
        String driverType = null;
        try {
            if (crawlDatum.meta().has("driverType")) {
                driverType = crawlDatum.meta("driverType");
            }
            if (driverType != null && driverType.equals("chrome")) {
                System.setProperty("webdriver.chrome.driver", "tool/chromedriver.exe");
                driver = new ChromeDriver();
            } else if (driverType != null && driverType.equals("phantomjs")) {
                System.setProperty("phantomjs.binary.path", "tool/phantomjs.exe");
                driver = new PhantomJSDriver();
            } else {
                driver = new HtmlUnitDriver();
            }
            driver.get(crawlDatum.url());
            if (driver instanceof RemoteWebDriver && crawlDatum.meta("javascripts") != null) {
                RemoteWebDriver driverJs = (RemoteWebDriver) driver;
                String javascripts = crawlDatum.meta("javascripts");
                for(String js :javascripts.split(";"))
                {
                    driverJs.executeScript(js);
                    Thread.sleep(1000);
                }
            }
            if (crawlDatum.meta("sleepTime") != null) {
                Thread.sleep(crawlDatum.metaAsInt("sleepTime"));
            } else {
                Thread.sleep(10000);
            }
            response.setHtml(driver.getPageSource());
            response.content(driver.getPageSource().getBytes());
        }catch (Exception ex){
            LOG.info("selenium get failed :" + crawlDatum.url() );
        }finally {
            driver.close();
        }
        return  response;
    }

}
