package cn.edu.hfut.dmic.webcollector.net;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.Page;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.Proxy;

import static org.junit.Assert.*;

public class HttpClientRequestTest {
    @Test
    public void responseTest() throws Exception {
        String url = "http://so.iptrm.com/txnPatentData01.ajax?secondKeyWord=%E7%94%B3%E8%AF%B7%E4%BA%BA&secondkeyWordVal=%E6%96%B0%E6%80%9D%E7%A7%91%E6%8A%80%EF%BC%88%E4%B8%8A%E6%B5%B7%EF%BC%89%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8&secondSearchType=NOT&express2=&express=%28%E7%94%B3%E8%AF%B7%E4%BA%BA+%3D++%28+%E6%96%B0%E6%80%9D%E7%A7%91%E6%8A%80%EF%BC%88%E4%B8%8A%E6%B5%B7%EF%BC%89%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8+%29+%29&isFamily=&categoryIndex=&selectedCategory=&patentLib=&patentType=&order=&pdbt=&attribute-node:patent_cache-flag=false&attribute-node:patent_start-row=1&attribute-node:patent_page-row=1000&attribute-node:patent_sort-column=ano&attribute-node:patent_page=1";
        CrawlDatum crawlDatum = new CrawlDatum(url);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("113.223.215.45", 28803));
        HttpClientRequest request = new HttpClientRequest(crawlDatum,proxy);
        Page page = request.responsePage();
        System.out.print(page.html());
    }

}