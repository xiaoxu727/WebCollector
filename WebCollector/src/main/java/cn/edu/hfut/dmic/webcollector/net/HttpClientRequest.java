package cn.edu.hfut.dmic.webcollector.net;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.*;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Created by champion_xu on 2017/11/30.
 */
public class HttpClientRequest extends HttpRequest {

    public HttpClientRequest(String url) throws Exception {
        super(url);
    }

    public HttpClientRequest(String url, Proxy proxy) throws Exception {
        super(url, proxy);
    }

    public HttpClientRequest(CrawlDatum crawlDatum) throws Exception {
        super(crawlDatum);
    }

    public HttpClientRequest(CrawlDatum crawlDatum, Proxy proxy) throws Exception {
        super(crawlDatum, proxy);
    }


    public HttpResponse response() throws Exception {

        HttpResponse response = new HttpResponse();

        int code = -1;

        int maxRedirect = Math.max(0, MAX_REDIRECT);

        HttpHost httpHost = null ;

        InputStream is = null;

        CloseableHttpResponse closeableHttpResponse = null;

        if(proxy != null){
            if(proxy.address() instanceof InetSocketAddress){
                InetSocketAddress address = (InetSocketAddress)proxy.address();
                httpHost = new HttpHost(address.getHostName() ,address.getPort());
            }
        }

        // 配置请求时间和代理
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(this.timeoutForConnect)
                .setConnectionRequestTimeout(this.timeoutForConnect)
                .setSocketTimeout(this.timeoutForRead)
                .setProxy(httpHost)
                .setCookieSpec(CookieSpecs.BEST_MATCH)
                .build();

        CloseableHttpClient client = HttpClients.custom()
                .setDefaultRequestConfig(config)
//                .setRedirectStrategy(new LaxRedirectStrategy())
                .build();


        HttpRequestBase method = null ;

        if(crawlDatum.meta().has("method")&&crawlDatum.meta("method").equals("post")){
            method = new HttpPost(crawlDatum.url());
            HttpPost post = new HttpPost();
            post.setEntity(new StringEntity(crawlDatum.meta("param")));
        }else {
            method = new HttpGet(crawlDatum.url());
        }


        // 设置request header，其中包含cookie
        if(method != null){
            Map<String, List<String>> headers =  this.getHeaders();
            if(headers != null){
                for(Map.Entry<String, List<String>> entry :headers.entrySet()){
                    String key = entry.getKey();
                    List<String > values = entry.getValue();
                    for(String value : values){
                        method.setHeader(key, value);
                    }
                }
            }
        }

        try {
            closeableHttpResponse = client.execute(method);

            for (int redirect = 0; redirect <= maxRedirect; redirect++) {

                code = closeableHttpResponse.getStatusLine().getStatusCode();
                /*只记录第一次返回的code*/
                if (redirect == 0) {
                    response.code(code);
                }

                if(code== HttpStatus.SC_NOT_FOUND){
                    response.setNotFound(true);
                    return response;
                }

                boolean needBreak = false;
                switch (code) {

                    case HttpStatus.SC_MOVED_PERMANENTLY:
                    case HttpURLConnection.HTTP_MOVED_TEMP:
                        response.setRedirect(true);
                        if (redirect == MAX_REDIRECT) {
                            throw new Exception("redirect to much time");
                        }

                        String location = closeableHttpResponse.getFirstHeader("Location").getValue();
                        if (location == null) {
                            throw new Exception("redirect with no location");
                        }
                        method.setURI(URI.create(location));
                        closeableHttpResponse = client.execute(method);

                        continue;
                    default:
                        needBreak = true;
                        break;
                }
                if (needBreak) {
                    break;
                }

            }


            HttpEntity entity = closeableHttpResponse.getEntity();
//            String str = EntityUtils.toString(entity);
            String contentEncoding = null;
//          = entity.getContentEncoding().getValue();
            is = entity.getContent();
            if (contentEncoding != null && contentEncoding.equals("gzip")) {
                is = new GZIPInputStream(is);
            }

            byte[] buf = new byte[2048];
            int read;
            int sum = 0;
            int maxsize = MAX_RECEIVE_SIZE;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((read = is.read(buf)) != -1) {
                if (maxsize > 0) {
                    sum = sum + read;

                    if (maxsize > 0 && sum > maxsize) {
                        read = maxsize - (sum - read);
                        bos.write(buf, 0, read);
                        break;
                    }
                }
                bos.write(buf, 0, read);
            }
            response.content(bos.toByteArray());
            response.headers(response.getHeaders());
            bos.close();
            return response;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (is != null) {
                is.close();
                closeableHttpResponse.close();
            }else {
                return null;
            }
        }
    }
}
