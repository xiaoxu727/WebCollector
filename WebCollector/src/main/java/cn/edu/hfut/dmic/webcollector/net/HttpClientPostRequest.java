package cn.edu.hfut.dmic.webcollector.net;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Created by champion_xu on 2018/1/10.
 */
public class HttpClientPostRequest extends HttpClientRequest {
    public HttpClientPostRequest(String url) throws Exception {
        super(url);
    }

    public HttpClientPostRequest(String url, Proxy proxy) throws Exception {
        super(url, proxy);
    }

    public HttpClientPostRequest(CrawlDatum crawlDatum) throws Exception {
        super(crawlDatum);
    }

    public HttpClientPostRequest(CrawlDatum crawlDatum, Proxy proxy) throws Exception {
        super(crawlDatum, proxy);
    }
    public HttpResponse response() throws Exception {
        HttpResponse response = new HttpResponse();
        int code = -1;
        // Post请求的url，与get不同的是不需要带参数
        URL postUrl = new URL(crawlDatum.url());
        // 打开连接
        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();

        // 设置是否向connection输出，因为这个是post请求，参数要放在
        // http正文内，因此需要设为true
        connection.setDoOutput(true);
        // Read from the connection. Default is true.
        connection.setDoInput(true);
        // 默认是 GET方式
        connection.setRequestMethod("POST");

        // Post 请求不能使用缓存
        connection.setUseCaches(false);

        connection.setInstanceFollowRedirects(true);

        // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
        // 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
        // 进行编码
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
        // 要注意的是connection.getOutputStream会隐含的进行connect。
        connection.connect();
        DataOutputStream out = new DataOutputStream(connection
                .getOutputStream());
        // The URL-encoded cont<a href="https://www.baidu.com/s?wd=end&tn=44039180_cpr&fenlei=mv6quAkxTZn0IZRqIHckPjm4nH00T1Y3m10LnhuBuHTzP16kPvc40ZwV5Hcvrjm3rH6sPfKWUMw85HfYnjn4nH6sgvPsT6KdThsqpZwYTjCEQLGCpyw9Uz4Bmy-bIi4WUvYETgN-TLwGUv3EPHndnWcdPHns" target="_blank" class="baidu-highlight">end</a>
        // 正文，正文内容其实跟get的URL中 '? '后的参数字符串一致

        String content = crawlDatum.meta("param");
        if(content != null){
            // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写到流里面
            out.writeBytes(content);
            out.flush();
            out.close();
        }


        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        String result = "";

        while ((line = reader.readLine()) != null){
            result += line;
        }
        reader.close();
        connection.disconnect();
            response.content(result.getBytes());
            response.headers(response.getHeaders());
            return response;
    }


}
