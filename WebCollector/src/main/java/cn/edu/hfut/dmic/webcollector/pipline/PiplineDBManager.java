package cn.edu.hfut.dmic.webcollector.pipline;

/**
 * Created by champion_xu on 2017/12/13.
 */
public abstract class PiplineDBManager implements Pipline {

    public synchronized void connect(){};

    public  boolean isExistDB(){ return false; }

    public boolean isExistable(String tableName){return false; }

    public abstract void open();

    public abstract void close();

}
