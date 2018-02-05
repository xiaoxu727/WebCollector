package com.champion.data.crawler.PublicSentiment.pipline;

import cn.edu.hfut.dmic.webcollector.pipline.PiplineDBManager;
import cn.edu.hfut.dmic.webcollector.util.PropertiesUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by champion_xu on 2017/12/13.
 */
public class HBasePiplineDBManager extends PiplineDBManager {

    private static final Logger logger = LoggerFactory.getLogger(HBasePiplineDBManager.class);
    private static final int DEFAULT_MAX_VERSIONS = 3;
    private static Configuration conf;
    private static Connection conn;
    static String PATH ="config/config.properties";
    static {
        try {
            File file = new File("config");
            System.out.println(file.getAbsoluteFile());
            if (conf == null) {
                conf = HBaseConfiguration.create();
                conf.set("hbase.zookeeper.quorum", PropertiesUtils.getAsString(PATH,"hbase.zookeeper.quorum"));
                conf.set("hbase.zookeeper.property.clientPort", PropertiesUtils.getAsString(PATH,"hbase.zookeeper.property.clientPort"));
            }
        } catch (Exception e) {
            logger.error("HBase Configuration Initialization failure !", e);
            e.printStackTrace();
        }
    }


    @Override
    public  synchronized void connect() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = ConnectionFactory.createConnection(conf);
            }
        } catch (IOException e) {
            logger.error("HBase 建立链接失败 ", e);
        }
        return;
    }

    @Override
    public boolean isExistDB() {
        return false;
    }

    @Override
    public boolean isExistable(String tableName) {
        boolean result = false;

        // 参数判空
        if (StringUtils.isBlank(tableName)) {
            return false;
        }

        Connection conn = null;
        HBaseAdmin admin = null;

        // 表不存在则创建
        try {
            conn = ConnectionFactory.createConnection(conf);
            admin = (HBaseAdmin) conn.getAdmin();
            result = admin.tableExists(tableName);

        } catch (IOException e) {
            logger.error("check HBase table error.", e);
            e.printStackTrace();

        } finally {
            closeHBaseAdmin(admin);
            close();
        }

        return result;
    }

    @Override
    public void open() {

    }

    @Override
    public void create(String tableName, String[] columns) {
        createTable(tableName, DEFAULT_MAX_VERSIONS, null, columns);

    }

    @Override
    public void create(String tableName, Map<String, String> columns) {

    }

    @Override
    public <Put> long insert(String tableName, Put put) {
        return insert(tableName, Arrays.asList(put));
    }

    @Override
    public <Put> long insert(String tableName, List<Put> puts){
        if (StringUtils.isBlank(tableName) || puts == null || puts.size() == 0) {
            return -1;
        }
        long currentTime = System.currentTimeMillis();
        try{

            if(conn == null){
                connect();
            }
            if(conn.isClosed()){
                connect();
            }
            HTable table = (HTable) conn.getTable(TableName.valueOf(tableName));
            table.setAutoFlushTo(false);
            table.setWriteBufferSize(5 * 1024 * 1024);

            try {
                table.put((List<org.apache.hadoop.hbase.client.Put>) puts);
                table.flushCommits();

            } finally {
                closeTable(table);
                close();
            }

        }catch (Exception e){
            logger.info("插入数据库错误");
        }
        return System.currentTimeMillis() - currentTime;
    }


    /**
     * 往指定表添加数据
     *
     * @param tableName
     *            表名
     * @param rowKey
     * @param family
     *            列簇名
     * @param qualifier
     *            列名
     * @param value
     *            列值
     * @return long 返回执行时间
     * @throws Exception
     */
    public long insert(String tableName, String rowKey, String family, String qualifier, String value)
            throws Exception {
        if (StringUtils.isBlank(tableName) || StringUtils.isBlank(rowKey) || StringUtils.isBlank(family)
                || StringUtils.isBlank(qualifier)) {
            return -1;
        }

        Put p = new Put(Bytes.toBytes(rowKey));
        p.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(value));
        return insert(tableName, Arrays.asList(p));
    }




    @Override
    public <Put> void update(String tableName, Put put) {

    }

    public Result getRecord(String tableName, Object key) {
        String keyStr = null;
        if(key instanceof String){
            keyStr = (String) key;
        }
        if (StringUtils.isBlank(tableName) || StringUtils.isBlank(keyStr)) {
            return null;
        }

        Result rs = null;
        Table table = null;

        try {
            table = getTable(tableName);
            Get get = new Get(Bytes.toBytes(keyStr));
            rs = table.get(get);

        } catch (IOException e) {
            logger.error("get row error !", e);
            e.printStackTrace();
        } finally {
            closeTable(table);
        }
        return rs;
    }



    @Override
    public void close() {
        if (null != conn) {
            try {
                conn.close();
            } catch (Exception e) {
                logger.error("close connection failure !", e);
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除表
     *
     * @param tableName
     * @author
     */
    @Override
    public void dropTable(String tableName) {
        if (StringUtils.isBlank(tableName)) {
            return;
        }

        Connection conn = null;
        HBaseAdmin admin = null;

        try {
            conn = ConnectionFactory.createConnection(conf);
            admin = (HBaseAdmin) conn.getAdmin();

            if (admin.tableExists(TableName.valueOf(tableName))) {
                admin.disableTable(TableName.valueOf(tableName));
                admin.deleteTable(TableName.valueOf(tableName));

                logger.info("Table: {} delete success!", tableName);
            } else {
                logger.warn("Table: {} is not exists!", tableName);
            }

        } catch (IOException e) {
            logger.error("drop table error.", e);
            e.printStackTrace();
        } finally {
            closeHBaseAdmin(admin);
            close();
        }
    }




    /**
     * 关闭管理员
     *
     * @throws IOException
     */
    public static void closeHBaseAdmin(HBaseAdmin admin) {
        if (null != admin) {
            try {
                admin.close();
            } catch (Exception e) {
                logger.error("close HBase admin failure !", e);
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建表操作
     *
     * @param tableName
     * @param splitKeys
     * @author
     */
    public void createTable(String tableName, int maxVersions, byte[][] splitKeys, String[] families) {
        // 参数判空
        if (StringUtils.isBlank(tableName) || families == null || families.length <= 0) {
            return;
        }

        Connection conn = null;
        HBaseAdmin admin = null;

        // 表不存在则创建
        try {
            conn = ConnectionFactory.createConnection(conf);
            admin = (HBaseAdmin) conn.getAdmin();

            if (!admin.tableExists(tableName)) {
                HTableDescriptor desc = new HTableDescriptor(TableName.valueOf(tableName));
                for (String family : families) {
                    HColumnDescriptor columnDescriptor = new HColumnDescriptor(family);
                    // columnDescriptor.setCompressionType(Algorithm.SNAPPY);
                    columnDescriptor.setMaxVersions(maxVersions);
                    desc.addFamily(columnDescriptor);
                }
                if (splitKeys != null) {
                    admin.createTable(desc, splitKeys);
                } else {
                    admin.createTable(desc);
                }
                logger.info("Table: {} create success!", tableName);
            } else {
                logger.warn("Table: {} already exists.", tableName);
            }
        } catch (IOException e) {
            logger.error("create HBase table error.", e);
            e.printStackTrace();

        } finally {
            closeHBaseAdmin(admin);
            close();
        }
    }

    /**
     * 获取 Table
     *
     * @param tableName
     *            表名
     * @return
     * @throws IOException
     */
    public Table getTable(String tableName) throws IOException {
        if(conn == null|| conn.isClosed()){
            connect();
        }
        return conn.getTable(TableName.valueOf(tableName));
    }
    /**
     * 关闭table
     *
     * @param table
     */
    public void closeTable(Table table) {
        if (null != table) {
            try {
                table.close();
            } catch (Exception e) {
                logger.error("close table failure !", e);
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取单个列值
     *
     * @param tableName
     * @param rowKey
     * @return
     * @author
     */
    public String getCellValue(String tableName, String rowKey, String family, String qualifier) {
        if (StringUtils.isBlank(tableName) || StringUtils.isBlank(rowKey) || StringUtils.isBlank(family)
                || StringUtils.isBlank(qualifier)) {
            return null;
        }

        Table table = null;

        try {
            table = getTable(tableName);
            Get get = new Get(Bytes.toBytes(rowKey));
            get.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
            Result result = table.get(get);
            return Bytes.toString(result.getValue(Bytes.toBytes(family), Bytes.toBytes(qualifier)));

        } catch (IOException e) {
            logger.error("get column error !", e);
            e.printStackTrace();

        } finally {
            closeTable(table);
        }
        return null;
    }

    /**
     * 获取一行中某列族的值
     *
     * @param tableName
     * @param rowKey
     * @return
     * @author
     */
    public  Map<String, String> getFamily(String tableName, String rowKey, String family) {
        if (StringUtils.isBlank(tableName) || StringUtils.isBlank(rowKey) || StringUtils.isBlank(family)) {
            return null;
        }

        Map<String, String> map = new HashMap<String, String>();
        Table table = null;

        try {
            table = getTable(tableName);
            Get get = new Get(Bytes.toBytes(rowKey));
            get.addFamily(Bytes.toBytes(family));
            Result result = table.get(get);
            for (Cell cell : result.rawCells()) {
                byte[] q = CellUtil.cloneQualifier(cell);
                byte[] v = CellUtil.cloneValue(cell);
                map.put(Bytes.toString(q), Bytes.toString(v));
            }
        } catch (IOException e) {
            logger.error("get family error !", e);
            e.printStackTrace();
        } finally {
            closeTable(table);
        }
        return map;
    }

    /**
     * 判断rowkey是否存在
     *
     * @param tableName
     * @param rowKey
     * @return
     * @throws IOException
     */
    public boolean isRowExist(String tableName, String rowKey) {
        if (StringUtils.isBlank(tableName) || StringUtils.isBlank(rowKey)) {
            return false;
        }

        Result rs = null;
        Table table = null;

        try {
            table = getTable(tableName);
            Get get = new Get(Bytes.toBytes(rowKey));
            get.setCheckExistenceOnly(true);
            rs = table.get(get);
            return rs.getExists();
        } catch (IOException e) {
            logger.error("get row error !", e);
            e.printStackTrace();
            return false;
        } finally {
            closeTable(table);
        }
    }

    /**
     * 获取多行数据
     *
     * @param rowKeys
     * @param rowKeys
     * @return
     * @throws Exception
     */
    @Override
    public <T> List<Result> getRecords(String tableName, List<T> rowKeys) {
        if (StringUtils.isBlank(tableName) || rowKeys == null || rowKeys.size() == 0) {
            return null;
        }

        Result[] rs = null;
        Table table = null;

        try {
            table = getTable(tableName);
            List<Get> gets = new ArrayList<Get>();
            for (T rowKey : rowKeys) {
                if (rowKey != null) {
                    gets.add(new Get(Bytes.toBytes(String.valueOf(rowKey))));
                }
            }
            if (gets.size() > 0) {
                rs = table.get(gets);
            }

        } catch (IOException e) {
            logger.error("get rows error !", e);
            e.printStackTrace();
        } finally {
            closeTable(table);
        }
        return Arrays.asList(rs);
    }

    public <T> void delRecord(String tableName, T key) {
        delRecord(tableName, key);
    }

    public  void delRecord(String tableName, String key) {
        if (StringUtils.isBlank(tableName) || StringUtils.isBlank(key)) {
            return;
        }

        Table table = null;

        try {
            table = getTable(tableName);
            Delete d = new Delete(Bytes.toBytes(key));
            table.delete(d);

        } catch (IOException e) {
            logger.error("delete row error !", e);
            e.printStackTrace();
        } finally {
            closeTable(table);
        }

    }

    @Override
    public <T> void delRecords(String tableName, List<T> keys) {
        if (StringUtils.isBlank(tableName) || keys == null || keys.size() == 0) {
            return;
        }

        Table table = null;

        try {
            table = getTable(tableName);
            List<Delete> dList = new ArrayList<Delete>();
            for (T rowKey : keys) {
                Delete d = new Delete(Bytes.toBytes(String.valueOf(rowKey)));
                dList.add(d);
            }
            if (dList.size() > 0) {
                table.delete(dList);
            }

        } catch (IOException e) {
            logger.error("delete row error !", e);
            e.printStackTrace();
        } finally {
            closeTable(table);
        }

    }

}
