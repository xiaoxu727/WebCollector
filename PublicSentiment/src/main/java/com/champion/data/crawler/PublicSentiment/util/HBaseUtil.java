package com.champion.data.crawler.PublicSentiment.util;

/**
 * Created by champion_xu on 2017/12/12.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HBaseUtil {
    private static final Logger logger = LoggerFactory.getLogger(HBaseUtil.class);
    private static final int DEFAULT_MAX_VERSIONS = 3;
    private static Configuration conf;
    private static Connection conn;

    static {
        try {
            if (conf == null) {
                conf = HBaseConfiguration.create();
                conf.set("hbase.zookeeper.quorum", "slave1,slave2");
                conf.set("hbase.zookeeper.property.clientPort", "2181");

            }
        } catch (Exception e) {
            logger.error("HBase Configuration Initialization failure !", e);
            e.printStackTrace();
        }
    }

    /**
     * 初始化配置信息
     *
     * @param zkHost
     * @param zkClientPort
     */
    public static void init(String zkHost, String zkClientPort) {
        try {
            if (conf == null) {
                conf = HBaseConfiguration.create();
                conf.set("hbase.zookeeper.quorum", zkHost);
                conf.set("hbase.zookeeper.property.clientPort", zkClientPort);

            }
        } catch (Exception e) {
            logger.error("HBase Configuration Initialization failure !", e);
            e.printStackTrace();
        }
    }

    public static Configuration getConfiguration() {
        return conf;
    }

    /**
     * 获得链接
     *
     * @return
     */
    public static synchronized Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = ConnectionFactory.createConnection(conf);
            }
        } catch (IOException e) {
            logger.error("HBase 建立链接失败 ", e);
        }
        return conn;
    }

    /**
     * 关闭连接
     *
     * @throws IOException
     */
    public static void closeConnection(Connection conn) {
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
     * @param families
     * @author
     */
    public static void createTable(String tableName, String[] families) {
        createTable(tableName, DEFAULT_MAX_VERSIONS, null, families);
    }

    /**
     * 创建表操作
     *
     * @param tableName
     * @param splitKeys
     * @param families
     * @author
     */
    public static void createTable(String tableName, byte[][] splitKeys, String[] families) {
        createTable(tableName, DEFAULT_MAX_VERSIONS, splitKeys, families);
    }

    /**
     * 创建表操作
     *
     * @param tableName
     * @param maxVersions
     * @param families
     * @author
     */
    public static void createTable(String tableName, int maxVersions, String[] families) {
        createTable(tableName, maxVersions, null, families);
    }

    /**
     * 创建表操作
     *
     * @param tableName
     * @param splitKeys
     * @author
     */
    public static void createTable(String tableName, int maxVersions, byte[][] splitKeys, String[] families) {
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
            closeConnection(conn);
        }
    }

    /**
     * 判断表是否存在
     *
     * @param tableName
     * @return
     */
    public static boolean tableExists(String tableName) {
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
            closeConnection(conn);
        }

        return result;
    }

    /**
     * 获取 Table
     *
     * @param tableName
     *            表名
     * @return
     * @throws IOException
     */
    public static Table getTable(String tableName) throws IOException {
        return getConnection().getTable(TableName.valueOf(tableName));
        // try {
        // return getConnection().getTable(TableName.valueOf(tableName));
        // } catch (Exception e) {
        // logger.error("Obtain Table failure !", e);
        // e.printStackTrace();
        // }
        // return null;
    }

    /**
     * 关闭table
     *
     * @param table
     */
    public static void closeTable(Table table) {
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
     * 删除表
     *
     * @param tableName
     * @author
     */
    public static void dropTable(String tableName) {
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
            closeConnection(conn);
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
    public static String get(String tableName, String rowKey, String family, String qualifier) {
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
    public static Map<String, String> getFamily(String tableName, String rowKey, String family) {
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
     * 获取单条数据
     *
     * @param tableName
     * @param rowKey
     * @return
     * @throws IOException
     */
    public static Result getRow(String tableName, String rowKey) {
        if (StringUtils.isBlank(tableName) || StringUtils.isBlank(rowKey)) {
            return null;
        }

        Result rs = null;
        Table table = null;

        try {
            table = getTable(tableName);
            Get get = new Get(Bytes.toBytes(rowKey));
            rs = table.get(get);

        } catch (IOException e) {
            logger.error("get row error !", e);
            e.printStackTrace();
        } finally {
            closeTable(table);
        }
        return rs;
    }

    /**
     * 判断rowkey是否存在
     *
     * @param tableName
     * @param rowKey
     * @return
     * @throws IOException
     */
    public static boolean isRowExist(String tableName, String rowKey) {
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
    public static <T> Result[] getRows(String tableName, List<T> rowKeys) {
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
        return rs;
    }

    /**
     * 扫描整张表，注意使用完要释放。
     *
     * @param tableName
     * @return
     */
    public static ResultScanner getScanneer(String tableName, int caching) {
        ResultScanner results = null;

        if (StringUtils.isBlank(tableName)) {
            return null;
        }

        Table table = null;

        try {
            table = getTable(tableName);

            Scan scan = new Scan();
            scan.setCaching(caching);
            results = table.getScanner(scan);

        } catch (IOException e) {
            logger.error("getResultScanner failure !", e);
            e.printStackTrace();
        } finally {
            closeTable(table);
        }
        return results;
    }

    /**
     * 异步往指定表添加数据
     *
     * @param tableName
     *            表名
     * @param puts
     *            需要添加的数据
     * @return long 返回执行时间
     * @throws IOException
     */
    public static long put(String tableName, List<Put> puts) throws Exception {
        if (StringUtils.isBlank(tableName) || puts == null || puts.size() == 0) {
            return -1;
        }

        long currentTime = System.currentTimeMillis();
        Connection conn = getConnection();

        final BufferedMutator.ExceptionListener listener = new BufferedMutator.ExceptionListener() {
            @Override
            public void onException(RetriesExhaustedWithDetailsException e, BufferedMutator mutator) {
                for (int i = 0; i < e.getNumExceptions(); i++) {
                    System.out.println("Failed to sent put " + e.getRow(i) + ".");
                    logger.error("Failed to sent put " + e.getRow(i) + ".");
                }
            }
        };
        BufferedMutatorParams params = new BufferedMutatorParams(TableName.valueOf(tableName)).listener(listener);
        params.writeBufferSize(5 * 1024 * 1024);

        final BufferedMutator mutator = conn.getBufferedMutator(params);
        try {
            mutator.mutate(puts);
            mutator.flush();
        } catch (Exception e) {
            logger.error("put data error !", e);
            e.printStackTrace();
        } finally {
            mutator.close();
            closeConnection(conn);
        }

        return System.currentTimeMillis() - currentTime;
    }

    /**
     * 异步往指定表添加数据
     *
     * @param tablename
     *            表名
     * @param put
     *            需要添加的数据
     * @return long 返回执行时间
     * @throws Exception
     */
    public static long put(String tablename, Put put) throws Exception {
        return put(tablename, Arrays.asList(put));
    }

    /**
     * 异步往指定表添加数据
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
    public static long put(String tableName, String rowKey, String family, String qualifier, String value)
            throws Exception {
        if (StringUtils.isBlank(tableName) || StringUtils.isBlank(rowKey) || StringUtils.isBlank(family)
                || StringUtils.isBlank(qualifier)) {
            return -1;
        }

        Put p = new Put(Bytes.toBytes(rowKey));
        p.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(value));
        return put(tableName, Arrays.asList(p));
    }

    /**
     * 往指定表添加数据
     *
     * @param tableName
     *            表名
     * @param puts
     *            需要添加的数据
     * @return long 返回执行时间
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static long putByHTable(String tableName, List<?> puts) throws Exception {
        if (StringUtils.isBlank(tableName) || puts == null || puts.size() == 0) {
            return -1;
        }

        long currentTime = System.currentTimeMillis();
        Connection conn = getConnection();

        HTable table = (HTable) conn.getTable(TableName.valueOf(tableName));
        table.setAutoFlushTo(false);
        table.setWriteBufferSize(5 * 1024 * 1024);

        try {
            table.put((List<Put>) puts);
            table.flushCommits();

        } finally {
            closeTable(table);
            closeConnection(conn);
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
    public static long putByHTable(String tableName, String rowKey, String family, String qualifier, String value)
            throws Exception {
        if (StringUtils.isBlank(tableName) || StringUtils.isBlank(rowKey) || StringUtils.isBlank(family)
                || StringUtils.isBlank(qualifier)) {
            return -1;
        }

        Put p = new Put(Bytes.toBytes(rowKey));
        p.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(value));
        return putByHTable(tableName, Arrays.asList(p));
    }

    /**
     * 删除单条数据
     *
     * @param tableName
     * @param rowKey
     * @throws IOException
     */
    public static void delete(String tableName, String rowKey) throws IOException {
        if (StringUtils.isBlank(tableName) || StringUtils.isBlank(rowKey)) {
            return;
        }

        Table table = null;

        try {
            table = getTable(tableName);
            Delete d = new Delete(Bytes.toBytes(rowKey));
            table.delete(d);

        } catch (IOException e) {
            logger.error("delete row error !", e);
            e.printStackTrace();
        } finally {
            closeTable(table);
        }
    }

    /**
     * 删除多行数据
     *
     * @param <T>
     * @param tableName
     * @param rowKeys
     * @throws IOException
     */
    public static <T> void delete(String tableName, List<T> rowKeys) throws IOException {
        if (StringUtils.isBlank(tableName) || rowKeys == null || rowKeys.size() == 0) {
            return;
        }

        Table table = null;

        try {
            table = getTable(tableName);
            List<Delete> dList = new ArrayList<Delete>();
            for (T rowKey : rowKeys) {
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

    /**
     * 检索指定表的第一行记录。
     * （如果在创建表时为此表指定了非默认的命名空间，则需拼写上命名空间名称，格式为【namespace:tablename】）。
     *
     * @param tableName
     *            表名称(*)。
     * @param filterList
     *            过滤器集合，可以为null。
     * @return
     */
    public static Result selectFirstResultRow(String tableName, FilterList filterList) {
        if (StringUtils.isBlank(tableName)) {
            return null;
        }

        Table table = null;

        try {

            table = getTable(tableName);
            Scan scan = new Scan();
            if (filterList != null) {
                scan.setFilter(filterList);
            }
            ResultScanner scanner = table.getScanner(scan);
            Iterator<Result> iterator = scanner.iterator();
            int index = 0;
            while (iterator.hasNext()) {
                Result rs = iterator.next();
                if (index == 0) {
                    scanner.close();
                    return rs;
                }
            }

        } catch (IOException e) {
            logger.error("delete row error !", e);
            e.printStackTrace();
        } finally {
            closeTable(table);
        }

        return null;
    }

    /**
     * 分页检索表数据。 （如果在创建表时为此表指定了非默认的命名空间，则需拼写上命名空间名称，格式为【namespace:tablename】）。
     *
     * @param tableName
     *            表名称(*)。
     * @param startRowKey
     *            起始行键(可以为空，如果为空，则从表中第一行开始检索)。
     * @param endRowKey
     *            结束行键(可以为空)。
     * @param filterList
     *            检索条件过滤器集合(不包含分页过滤器；可以为空)。
     * @param maxVersions
     *            指定最大版本数【如果为最大整数值，则检索所有版本；如果为最小整数值，则检索最新版本；否则只检索指定的版本数】。
     * @param pageModel
     *            分页模型(*)。
     * @return 返回HBasePageModel分页对象。
     */
    public static HBasePageModel scanResultByPageFilter(String tableName, byte[] startRowKey, byte[] endRowKey,
                                                        FilterList filterList, int maxVersions, HBasePageModel pageModel) {
        if (StringUtils.isBlank(tableName)) {
            return pageModel;
        }

        if (pageModel == null) {
            pageModel = new HBasePageModel(10);
        }
        if (maxVersions <= 0) {
            // 默认只检索数据的最新版本
            maxVersions = Integer.MIN_VALUE;
        }
        pageModel.initStartTime();
        pageModel.initEndTime();

        Table table = null;

        try {
            table = getTable(tableName);
            int tempPageSize = pageModel.getPageSize();
            boolean isEmptyStartRowKey = false;
            if (startRowKey == null) {
                // 则读取表的第一行记录，这里用到了笔者本人自己构建的一个表数据操作类。
                Result firstResult = selectFirstResultRow(tableName, filterList);
                if (firstResult.isEmpty()) {
                    return pageModel;
                }
                startRowKey = firstResult.getRow();
            }

            if (pageModel.getPageStartRowKey() == null) {
                isEmptyStartRowKey = true;
                pageModel.setPageStartRowKey(startRowKey);
            } else {
                if (pageModel.getPageEndRowKey() != null) {
                    pageModel.setPageStartRowKey(pageModel.getPageEndRowKey());
                }
                // 从第二页开始，每次都多取一条记录，因为第一条记录是要删除的。
                tempPageSize += 1;
            }

            Scan scan = new Scan();
            scan.setStartRow(pageModel.getPageStartRowKey());
            if (endRowKey != null) {
                scan.setStopRow(endRowKey);
            }
            PageFilter pageFilter = new PageFilter(pageModel.getPageSize() + 1);
            if (filterList != null) {
                filterList.addFilter(pageFilter);
                scan.setFilter(filterList);
            } else {
                scan.setFilter(pageFilter);
            }
            if (maxVersions == Integer.MAX_VALUE) {
                scan.setMaxVersions();
            } else if (maxVersions == Integer.MIN_VALUE) {

            } else {
                scan.setMaxVersions(maxVersions);
            }
            ResultScanner scanner = table.getScanner(scan);
            List<Result> resultList = new ArrayList<Result>();
            int index = 0;
            for (Result rs : scanner.next(tempPageSize)) {
                if (isEmptyStartRowKey == false && index == 0) {
                    index += 1;
                    continue;
                }
                if (!rs.isEmpty()) {
                    resultList.add(rs);
                    logger.info("docID: " + Bytes.toString(rs.getRow()));
                }
                index += 1;
            }
            scanner.close();
            pageModel.setResultList(resultList);

            int pageIndex = pageModel.getPageIndex() + 1;
            pageModel.setPageIndex(pageIndex);
            if (pageModel.getResultList().size() > 0) {
                // 获取本次分页数据首行和末行的行键信息
                byte[] pageStartRowKey = pageModel.getResultList().get(0).getRow();
                byte[] pageEndRowKey = pageModel.getResultList().get(pageModel.getResultList().size() - 1).getRow();
                pageModel.setPageStartRowKey(pageStartRowKey);
                pageModel.setPageEndRowKey(pageEndRowKey);
            }
            int queryTotalCount = pageModel.getQueryTotalCount() + pageModel.getResultList().size();
            logger.info("查询记录数：" + queryTotalCount);
            pageModel.setQueryTotalCount(queryTotalCount);
            pageModel.initEndTime();
            pageModel.printTimeInfo();

        } catch (Exception e) {
            logger.error("scan page error !", e);
            e.printStackTrace();
        } finally {
            closeTable(table);
        }

        return pageModel;
    }

}

