import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.util.CrawlDatumFormater;
import com.champion.data.crawler.PublicSentiment.pipline.HBasePiplineDBManager;
import com.champion.data.crawler.PublicSentiment.util.HBasePageModel;
import com.champion.data.crawler.PublicSentiment.util.HBaseUtil;
import com.google.gson.Gson;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by champion_xu on 2017/12/12.
 */
public class test {
    HBaseUtil util = new HBaseUtil();
    public static void main(String [] args) throws Exception {
        String[] famlies = {"famly1"};
        HBasePiplineDBManager dbManager = new HBasePiplineDBManager();
//        dbManager.connect();
////        dbManager.create("test", famlies);
//        Put put = new Put(Bytes.toBytes("row2"));
//        put.add(Bytes.toBytes("famly1"), Bytes.toBytes("qual2"),Bytes.toBytes("valuetest4"));
//        dbManager.insert("test",put);
//        dbManager.insert("test","row3","famly1","qual3","valuetest5");
//
        System.out.println(dbManager.getRecord("test", "row3"));


//        dbManager.getFamily()
//  List<Number> numberList = new
// Ar

// rayList<Number>();
//        Integer integer=1;
//        numberList.add(integer);
//        System.out.println(numberList.get(0));
//        File file = new File("config");
//        System.out.println(file.getAbsoluteFile());
//        CrawlDatum crawlDatum = new CrawlDatum("http:baidu.com","list");
//        String str = CrawlDatumFormater.datumToJsonStr(crawlDatum);
//        System.out.println(str);
//        CrawlDatum test = CrawlDatumFormater.jsonStrToDatum(str);
//        System.out.println(test.url());
//        System.out.println(test.type());
//
//        System.out.println(new Gson().toJson(crawlDatum));
//        JSONObject json = JSONObject.fromObject(obj);

    }
}
