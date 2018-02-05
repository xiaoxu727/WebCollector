package cn.edu.hfut.dmic.webcollector.net;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * Created by champion_xu on 2017/12/4.
 */
public class HtmlUnitRequest extends HttpRequest{


    public HtmlUnitRequest(String url) throws Exception {
        super(url);
    }

    public HtmlUnitRequest(String url, Proxy proxy) throws Exception {
        super(url, proxy);
    }

    public HtmlUnitRequest(CrawlDatum crawlDatum) throws Exception {
        super(crawlDatum);
    }

    public HtmlUnitRequest(CrawlDatum crawlDatum, Proxy proxy) throws Exception {
        super(crawlDatum, proxy);
    }

    public HttpResponse response() throws Exception{

        HttpResponse response = new HttpResponse();
        WebClient webClient = null;
        BrowserVersion browserVersion = null;
        if(crawlDatum.meta().has("browser")){
            String browser = crawlDatum.meta("browser");
            if(browser.equals("chrome")){
                browserVersion = BrowserVersion.CHROME;
            }else if(browser.equals("IE")){
                browserVersion = BrowserVersion.INTERNET_EXPLORER_11;
            }else if(browser.equals("firefox")){
                browserVersion = BrowserVersion.FIREFOX_24;
            }
        }
        if(browserVersion != null){

            if(proxy != null){
                if(proxy.address() instanceof InetSocketAddress){
                    InetSocketAddress address = (InetSocketAddress)proxy.address();
                    webClient = new WebClient(browserVersion,address.getHostName() ,address.getPort());
                }
            }else {
                webClient = new WebClient(browserVersion);
            }
        }else {
            webClient = new WebClient();
        }
        if(crawlDatum.meta().has("JavaScriptEnabled")){
            if(crawlDatum.meta("JavaScriptEnabled").equals("true")){
                webClient.getOptions().setJavaScriptEnabled(true);
            }else {
                webClient.getOptions().setJavaScriptEnabled(false);
            }
        }

        if(crawlDatum.meta().has("CssEnabled")){
            if(crawlDatum.meta("CssEnabled").equals("true")){
                webClient.getOptions().setCssEnabled(true);
            }else {
                webClient.getOptions().setCssEnabled(false);
            }
        }
        if(crawlDatum.meta().has("redirect")){
            if(crawlDatum.meta("redirect").equals("true")){
                webClient.getOptions().setRedirectEnabled(true);
            }else {
                webClient.getOptions().setRedirectEnabled(false);
            }
        }

        if(crawlDatum.meta().has("cookieManager")){
            if(crawlDatum.meta("cookieManager").equals("true")){
                webClient.setCookieManager(new CookieManager());
            }
        }

        if(crawlDatum.meta().has("waitForBackgroundJavaScript")){
            if(crawlDatum.meta("cookieManager").equals("true")){
                int watiTime = crawlDatum.metaAsInt("waitForBackgroundJavaScript");
                webClient.waitForBackgroundJavaScript(watiTime);
            }
        }
        String url = crawlDatum.url();
        Page page = webClient.getPage(url);
        response.setHtml(page.toString());
//        page.getHead();
        return  response;

    }
}
