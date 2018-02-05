/**
 * Created by champion_xu on 2018/1/10.
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class PostTest {
    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        readContentFromPost();
    }
    public static void readContentFromPost() throws IOException {
        // Post请求的url，与get不同的是不需要带参数
        URL postUrl = new URL("http://so.iptrm.com/txnPatentData01.ajax?secondKeyWord=%E7%94%B3%E8%AF%B7%E4%BA%BA&secondkeyWordVal=%E4%B8%8A%E6%B5%B7%E5%8C%BB%E5%AD%A6&secondSearchType=NOT&express2=&express=+(%E7%94%B3%E8%AF%B7%E4%BA%BA+%3D++(+%E4%B8%8A%E6%B5%B7%E5%8C%BB%E5%AD%A6+)+)+&isFamily=&categoryIndex=&selectedCategory=&patentLib=&patentType=patent2&order=&pdbt=&attribute-node:patent_cache-flag=false&attribute-node:patent_start-row=1&attribute-node:patent_page-row=10&attribute-node:patent_sort-column=ano&attribute-node:patent_page=1");
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

//        String content = "keyword="+ URLEncoder.encode("上", "UTF-8");
//        content += "&pageIndex=10&type=12&geo_code=";
        String content = "";
        // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写到流里面
        out.writeBytes(content);
        out.flush();
        out.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        String result ="";

        while ((line = reader.readLine()) != null){
            System.out.println(line);
            result +=line;
        }

        reader.close();
        connection.disconnect();
//        return
    }
}