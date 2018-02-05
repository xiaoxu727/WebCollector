package cn.edu.hfut.dmic.webcollector.pipline;

import java.util.List;
import java.util.Map;

/**
 * Created by champion_xu on 2017/12/14.
 */
public interface Pipline {

    public void create(String tableName, String[] columns);

    public void create(String tableName, Map<String, String> columns);

    public void dropTable(String tableName);

    public <T> long insert(String tableName, T t);

    public <T> long insert(String tableName, List<T> records);

    public <T> void update(String tableName, T t);

    public <T> T getRecord(String tableName, Object key);

    public <T>  List<?> getRecords(String tableName, List<T> keys);

    public <T> void delRecord(String tableName, T key);

    public <T> void delRecords(String tableName, List<T> keys);


}
